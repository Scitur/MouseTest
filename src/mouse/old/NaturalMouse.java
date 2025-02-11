package mouse.old;

import lombok.Getter;
import lombok.Setter;
import mouse.InterfaceMouse;
import mouse.Provider;
import mouse.old.api.MouseMotion;
import mouse.old.api.MouseMotionFactory;
import mouse.old.support.DefaultMouseMotionNature;
import mouse.old.support.DefaultSpeedManager;
import mouse.old.support.Flow;
import mouse.old.support.MouseMotionNature;
import mouse.old.templates.FlowTemplates;
import mouse.old.util.Pair;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class NaturalMouse implements InterfaceMouse {
    public final MouseMotionNature nature;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final InterfaceMouse robotMouse;

    @Getter
    @Setter
    private List<Flow> flows = List.of(
            FlowTemplates.VARIATING.getFlow(),
            FlowTemplates.SLOW_STARTUP.getFlow(),
            FlowTemplates.SLOW_STARTUP_2.getFlow(),
            FlowTemplates.JAGGED.getFlow(),
            FlowTemplates.INTERRUPTED.getFlow(),
            FlowTemplates.INTERRUPTED_2.getFlow(),
            FlowTemplates.STOPPING.getFlow(),
            FlowTemplates.ADJUSTING.getFlow(),
            new Flow(FlowTemplates.random())
    );

    public NaturalMouse() {
        robotMouse = Provider.ROBOT_MOUSE;
        nature = new DefaultMouseMotionNature();
    }

    private synchronized boolean moveInternal(int dx, int dy) {
        MouseMotion motion = getFactory().build(dx, dy);
        try {
            motion.move();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Point getPosition() {
        return robotMouse.getPosition();
    }

    @Override
    public boolean move(Point point) {
        return false;
    }

    public synchronized boolean move(int dx, int dy) {
        if (getPosition().x == dx && getPosition().y == dy) return true;
        if (!Provider.getClient().isClientThread()) return moveInternal(dx, dy);

        executorService.submit(() -> moveInternal(dx, dy));
        return true;
    }

    @Override
    public boolean click(boolean rightClick) {
        return robotMouse.click(rightClick);
    }

    @Override
    public boolean moveFast(Point point) {
        throw new RuntimeException();
    }

    @Override
    public boolean clickFast(boolean rightClick) {
        return robotMouse.clickFast(rightClick);
    }

    @Override
    public boolean scrollDown(Point point) {
        return robotMouse.scrollDown(point);
    }

    @Override
    public boolean scrollUp(Point point) {
        return robotMouse.scrollUp(point);
    }

    public MouseMotionFactory getFactory() {
        return null;
        /*if (Rs2Antiban.getActivityIntensity() == ActivityIntensity.VERY_LOW) {
            log.info("Creating average computer user motion factory");
            return FactoryTemplates.createAverageComputerUserMotionFactory(nature);
        } else if (Rs2Antiban.getActivityIntensity() == ActivityIntensity.LOW) {
            log.info("Creating normal gamer motion factory");
            return FactoryTemplates.createNormalGamerMotionFactory(nature);
        } else if (Rs2Antiban.getActivityIntensity() == ActivityIntensity.MODERATE) {
            log.info("Creating fast gamer motion factory");
            return FactoryTemplates.createFastGamerMotionFactory(nature);
        } else if (Rs2Antiban.getActivityIntensity() == ActivityIntensity.HIGH) {
            log.info("Creating fast gamer motion factory");
            return FactoryTemplates.createFastGamerMotionFactory(nature);
        } else if (Rs2Antiban.getActivityIntensity() == ActivityIntensity.EXTREME) {
            log.info("Creating super fast gamer motion factory");
            return FactoryTemplates.createSuperFastGamerMotionFactory(nature);
        } else {
            log.info("Default: Creating super fast gamer motion factory");
            return FactoryTemplates.createSuperFastGamerMotionFactory(nature);
        }*/
    }

    /**
     * Moves the mouse off-screen with a default 100% chance.
     * This method will always move the mouse off-screen when called.
     */
    public void moveOffScreen() {
        // TODO: add tab out behavior
        // TODO: add higher chance to exit toward taskbar
        // TODO: add higher chance to exit toward another screen
        final int x, y;
        final boolean exitHorizontally = random.nextBoolean();
        if (exitHorizontally) {
            x = random.nextBoolean() ? -1 : Provider.getClient().getCanvasWidth() + 1;
            y = random.nextInt(0, Provider.getClient().getCanvasHeight() + 1);
        } else {
            x = random.nextInt(0, Provider.getClient().getCanvasWidth() + 1);
            y = random.nextBoolean() ? -1 : Provider.getClient().getCanvasHeight() + 1;
        }
        move(x,y);
    }

    /**
     * Moves the mouse off-screen based on a given percentage chance.
     *
     * @param chancePercentage the chance (in percentage) to move the mouse off-screen.
     *                         This value should be between 0.0 and 100.0 (inclusive).
     *                         Note: This parameter should not be a fractional value between
     *                         0.0 and 0.99; use values representing a whole percentage (e.g., 25.0, 50.0).
     */
    public void moveOffScreen(double chancePercentage) {
        if (chancePercentage >= 1 || chancePercentage > random.nextDouble()) moveOffScreen();
    }

    // Move to a random point on the screen
    public void moveRandom() {
        move(
                random.nextInt(0, Provider.getClient().getCanvasWidth() + 1),
                random.nextInt(0, Provider.getClient().getCanvasHeight() + 1)
        );
    }

    private static class SpeedManagerImpl extends DefaultSpeedManager {
        private SpeedManagerImpl(List<Flow> flows) {
            super(flows);
        }

        @Override
        public Pair<Flow, Long> getFlowWithTime(double distance) {
            Pair<Flow, Long> pair = super.getFlowWithTime(distance);
            return new Pair<>(pair.x, pair.y);
        }
    }
}
