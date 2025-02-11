package mouse.old.support;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Random;

public class DefaultOvershootManager {
    public static final double OVERSHOOT_SPEEDUP_DIVIDER = 1.8;
    public static final int MIN_OVERSHOOT_MOVEMENT_MS = 40;
    public static final int OVERSHOOT_RANDOM_MODIFIER_DIVIDER = 20;
    public static final int MIN_DISTANCE_FOR_OVERSHOOTS = 10;
    public static final int DEFAULT_OVERSHOOT_AMOUNT = 3;

    private final Random random;
    @Setter
    @Getter
    private long minOvershootMovementMs = MIN_OVERSHOOT_MOVEMENT_MS;
    @Setter
    @Getter
    private long minDistanceForOvershoots = MIN_DISTANCE_FOR_OVERSHOOTS;
    @Setter
    @Getter
    private double overshootRandomModifierDivider = OVERSHOOT_RANDOM_MODIFIER_DIVIDER;
    @Setter
    @Getter
    private double overshootSpeedupDivider = OVERSHOOT_SPEEDUP_DIVIDER;
    @Setter
    @Getter
    private int overshoots = DEFAULT_OVERSHOOT_AMOUNT;

    public DefaultOvershootManager(Random random) {
        this.random = random;
    }

    public int getOvershoots(Flow flow, long mouseMovementMs, double distance) {
        if (distance < minDistanceForOvershoots) {
            return 0;
        }
        return overshoots;
    }

    public Point getOvershootAmount(double distanceToRealTargetX, double distanceToRealTargetY, long mouseMovementMs, int overshootsRemaining) {
        double distanceToRealTarget = Math.hypot(distanceToRealTargetX, distanceToRealTargetY);

        double randomModifier = distanceToRealTarget / overshootRandomModifierDivider;
        //double speedPixelsPerSecond = distanceToRealTarget / mouseMovementMs * 1000; // TODO utilize speed
        int x = (int) (random.nextDouble() * randomModifier - randomModifier / 2d) * overshootsRemaining;
        int y = (int) (random.nextDouble() * randomModifier - randomModifier / 2d) * overshootsRemaining;
        return new Point(x, y);
    }

    public long deriveNextMouseMovementTimeMs(long mouseMovementMs, int overshootsRemaining) {
        return Math.max((long) (mouseMovementMs / overshootSpeedupDivider), minOvershootMovementMs);
    }
}
