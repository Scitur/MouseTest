package mouse.old.api;

import mouse.InterfaceMouse;
import mouse.Provider;
import mouse.old.VirtualMouse;
import mouse.old.support.*;
import mouse.old.support.mousemotion.MouseMovement;
import mouse.old.support.mousemotion.MovementFactory;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Random;

/**
 * Contains instructions to move cursor smoothly to the destination coordinates from where ever the cursor
 * currently is. The class is reusable, meaning user can keep calling it and the cursor returns in a random,
 * but reliable way, described in this class, to the destination.
 */
public class MouseMotion {
    // TODO: private static final Logger log = LoggerFactory.getLogger(MouseMotion.class);
    private static final int SLEEP_AFTER_ADJUSTMENT_MS = 2;

    private final InterfaceMouse mouse;

    private final int minSteps;
    private final int effectFadeSteps;
    private final int reactionTimeBaseMs;
    private final int reactionTimeVariationMs;
    private final double timeToStepsDivider;

    private final Dimension screenSize;
    private final DeviationProvider deviationProvider;
    private final NoiseProvider noiseProvider;
    private final InterfaceSpeedManager speedManager;
    private final DefaultOvershootManager overshootManager;

    private final int xDest;
    private final int yDest;

    private final Random random;

    /**
     * @param nature the nature that defines how mouse is moved
     * @param xDest  the x-coordinate of destination
     * @param yDest  the y-coordinate of destination
     * @param random the random used for unpredictability
     */
    public MouseMotion(InterfaceMouse mouse, MouseMotionNature nature, Random random, int xDest, int yDest) {
        this.mouse = mouse;
        this.deviationProvider = nature.getDeviationProvider();
        this.noiseProvider = nature.getNoiseProvider();
        this.screenSize = Provider.getScreenSize();
        this.xDest = limitByScreenWidth(xDest);
        this.yDest = limitByScreenHeight(yDest);
        this.random = random;
        this.speedManager = nature.getSpeedManager();
        this.timeToStepsDivider = nature.getTimeToStepsDivider();
        this.minSteps = nature.getMinSteps();
        this.effectFadeSteps = nature.getEffectFadeSteps();
        this.reactionTimeBaseMs = nature.getReactionTimeBaseMs();
        this.reactionTimeVariationMs = nature.getReactionTimeVariationMs();
        this.overshootManager = nature.getOvershootManager();
    }

    /**
     * Blocking call, starts to move the cursor to the specified location from where it currently is.
     *
     * @throws InterruptedException when interrupted
     */
    public void move() throws InterruptedException {
        MovementFactory movementFactory = new MovementFactory(xDest, yDest, speedManager, overshootManager, screenSize);
        ArrayDeque<MouseMovement> movements = movementFactory.createMovements(mouse.getPosition());

        while (mouse.getPosition().x != xDest || mouse.getPosition().y != yDest) {
            if (movements.isEmpty()) {
                // This shouldn't usually happen, but it's possible that somehow we won't end up on the target,
                // Then just re-attempt from mouse new position. (There are known JDK bugs, that can cause sending the cursor
                // to wrong pixel)
                // movements = movementFactory.createMovements(mousePosition);
                throw new RuntimeException();
            }

            MouseMovement movement = movements.removeFirst();
            double distance = movement.distance;
            long mouseMovementMs = movement.time;
            Flow flow = movement.flow;
            double xDistance = movement.xDistance;
            double yDistance = movement.yDistance;

            // Number of steps is calculated from the movement time and limited by minimal amount of steps
            // (should have at least MIN_STEPS) and distance (shouldn't have more steps than pixels travelled)
            int steps = (int) Math.ceil(Math.min(distance, Math.max(mouseMovementMs / timeToStepsDivider, minSteps)));

            long startTime = System.currentTimeMillis();
            long stepTime = (long) (mouseMovementMs / (double) steps);

            double exactX = mouse.getPosition().x;
            double exactY = mouse.getPosition().y;

            double deviationMultiplierX = (random.nextDouble() - 0.5) * 2;
            double deviationMultiplierY = (random.nextDouble() - 0.5) * 2;

            double completedXDistance = 0;
            double completedYDistance = 0;

            for (int i = 0; i < steps; i++) {
                // All steps take equal amount of time. This is a value from 0...1 describing how far along the process is.
                final double percentageOfTotalTime = i == steps-1 ? 1 : i / (double) (steps-1);

                // value from 0 to 1, when effectFadeSteps remaining steps, starts to decrease to 0 linearly
                // This is here so noise and deviation wouldn't add offset to mouse final position, when we need accuracy.
                final int stepsLeft = steps - i - 1;
                final double effectMulti = stepsLeft >= effectFadeSteps ? 1 : stepsLeft / (double) effectFadeSteps;

                final double xStepSize = flow.getStepSize(xDistance, steps, percentageOfTotalTime);
                final double yStepSize = flow.getStepSize(yDistance, steps, percentageOfTotalTime);

                completedXDistance += xStepSize;
                completedYDistance += yStepSize;

                final double completedDistance = Math.hypot(completedXDistance, completedYDistance);
                double percentageOfTotalDistance = Math.min(1, completedDistance / distance);

                noiseProvider.apply(xStepSize, yStepSize);
                DoublePoint deviation = deviationProvider.getDeviation(distance, percentageOfTotalDistance);

                exactX += xStepSize;
                exactY += yStepSize;

                long endTime = startTime + stepTime * (i + 1);
                int mousePosX = roundTowards(exactX + (deviation.getX() * deviationMultiplierX + noiseProvider.getX()) * effectMulti, movement.destX);

                int mousePosY = roundTowards(exactY + (deviation.getY() * deviationMultiplierY + noiseProvider.getY()) * effectMulti, movement.destY);

                mousePosX = limitByScreenWidth(mousePosX);
                mousePosY = limitByScreenHeight(mousePosY);

                mouse.move(mousePosX, mousePosY);

                // Allow other action to take place or just observe, we'll later compensate by sleeping less.
                // observer.observe(mousePosX, mousePosY);

                long timeLeft = endTime - System.currentTimeMillis();
                sleepAround(Math.max(timeLeft, 0), 0);
            }

            if (mouse.getPosition().x != movement.destX || mouse.getPosition().y != movement.destY) {
                // It's possible that mouse is manually moved or for some other reason.
                // Let's start next step from pre-calculated location to prevent errors from accumulating.
                // But print warning as this is not expected behavior.
                mouse.move(movement.destX, movement.destY);
                // Let's wait a bit before getting mouse info.
                sleepAround(SLEEP_AFTER_ADJUSTMENT_MS, 0);
            }

            if (mouse.getPosition().x != xDest || mouse.getPosition().y != yDest) {
                // We are dealing with overshoot, let's sleep a bit to simulate human reaction time.
                sleepAround(reactionTimeBaseMs, reactionTimeVariationMs);
            }
        }
    }

    private int limitByScreenWidth(int value) {
        return Math.max(0, Math.min(screenSize.width - 1, value));
    }

    private int limitByScreenHeight(int value) {
        return Math.max(0, Math.min(screenSize.height - 1, value));
    }

    private void sleepAround(long sleepMin, long randomPart) {
        long sleepTime = (long) (sleepMin + random.nextDouble() * randomPart);
        // TODO: throw new RuntimeException("MouseMotion sleepAround is not currently implemented");
        //if (log.isTraceEnabled() && sleepTime > 0) {
        //    updateMouseInfo();
        //}
        Provider.sleep(sleepTime);
    }

    private static int roundTowards(double value, int target) {
        return (int) (target > value ? Math.ceil(value) : Math.floor(value));
    }
}