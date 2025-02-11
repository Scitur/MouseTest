package mouse.old.support.mousemotion;

import mouse.old.api.InterfaceSpeedManager;
import mouse.old.support.DefaultOvershootManager;
import mouse.old.support.Flow;
import mouse.old.util.Pair;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Iterator;

public class MovementFactory {
    //private static final Logger log = LoggerFactory.getLogger(MovementFactory.class);
    private final int xDst;
    private final int yDst;
    private final InterfaceSpeedManager speedManager;
    private final DefaultOvershootManager overshootManager;
    private final Dimension screenSize;

    public MovementFactory(int xDest, int yDest, InterfaceSpeedManager speedManager,
                           DefaultOvershootManager overshootManager, Dimension screenSize) {
        this.xDst = xDest;
        this.yDst = yDest;
        this.speedManager = speedManager;
        this.overshootManager = overshootManager;
        this.screenSize = screenSize;
    }

    public ArrayDeque<MouseMovement> createMovements(Point mousePosition) {
        ArrayDeque<MouseMovement> movements = new ArrayDeque<>();

        int lastMousePositionX = mousePosition.x;
        int lastMousePositionY = mousePosition.y;
        int xDistance = xDst - lastMousePositionX;
        int yDistance = yDst - lastMousePositionY;

        final double initialDistance = Math.hypot(xDistance, yDistance);

        Pair<Flow, Long> flowTime = speedManager.getFlowWithTime(initialDistance);
        Flow flow = flowTime.x;
        long mouseMovementMs = flowTime.y;
        int overshoots = overshootManager.getOvershoots(flow, mouseMovementMs, initialDistance);

        if (overshoots == 0) {
            movements.add(new MouseMovement(xDst, yDst, initialDistance, xDistance, yDistance, mouseMovementMs, flow));
            return movements;
        }

        for (int i = overshoots; i > 0; i--) {
            Point overshoot = overshootManager.getOvershootAmount(
                    xDst - lastMousePositionX, yDst - lastMousePositionY, mouseMovementMs, i
            );
            int currentDestinationX = limitByScreenWidth(xDst + overshoot.x);
            int currentDestinationY = limitByScreenHeight(yDst + overshoot.y);
            xDistance = currentDestinationX - lastMousePositionX;
            yDistance = currentDestinationY - lastMousePositionY;
            double distance = Math.hypot(xDistance, yDistance);
            flow = speedManager.getFlowWithTime(distance).x;
            movements.add(
                    new MouseMovement(currentDestinationX, currentDestinationY, distance, xDistance, yDistance, mouseMovementMs, flow)
            );
            lastMousePositionX = currentDestinationX;
            lastMousePositionY = currentDestinationY;
            // Apply for the next overshoot if exists.
            mouseMovementMs = overshootManager.deriveNextMouseMovementTimeMs(mouseMovementMs, i - 1);
        }

        Iterator<MouseMovement> it = movements.descendingIterator();

        boolean remove = true;
        // Remove overshoots from the end, which are matching the final destination, but keep those in middle of motion.
        while (it.hasNext() && remove) {
            MouseMovement movement = it.next();
            if (movement.destX == xDst && movement.destY == yDst) {
                lastMousePositionX = movement.destX - movement.xDistance;
                lastMousePositionY = movement.destY - movement.yDistance;
                it.remove();
            } else {
                remove = false;
            }
        }

        xDistance = xDst - lastMousePositionX;
        yDistance = yDst - lastMousePositionY;
        double distance = Math.hypot(xDistance, yDistance);
        Pair<Flow, Long> movementToTargetFlowTime = speedManager.getFlowWithTime(distance);
        long finalMovementTime = overshootManager.deriveNextMouseMovementTimeMs(movementToTargetFlowTime.y, 0);
        MouseMovement finalMove = new MouseMovement(
                xDst, yDst, distance, xDistance, yDistance, finalMovementTime, movementToTargetFlowTime.x
        );
        movements.add(finalMove);

        return movements;
    }

    private int limitByScreenWidth(int value) {
        return Math.max(0, Math.min(screenSize.width - 1, value));
    }

    private int limitByScreenHeight(int value) {
        return Math.max(0, Math.min(screenSize.height - 1, value));
    }


}
