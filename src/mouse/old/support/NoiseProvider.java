package mouse.old.support;

import lombok.Getter;

import java.util.Random;

public class NoiseProvider {
    public static final double DEFAULT_NOISINESS_DIVIDER = 2;
    private static final double SMALL_DELTA = 10e-6;

    private final Random random;
    private final double noisinessDivider;

    @Getter
    private double x;
    @Getter
    private double y;

    /**
     * @param noisinessDivider bigger value means less noise.
     */
    public NoiseProvider(Random random, double noisinessDivider) {
        this.random = random;
        this.noisinessDivider = noisinessDivider;
    }

    public NoiseProvider() {
        this(new Random(), DEFAULT_NOISINESS_DIVIDER);
    }

    public DoublePoint apply(double xStepSize, double yStepSize) {
        if (Math.abs(xStepSize - 0) < SMALL_DELTA && Math.abs(yStepSize - 0) < SMALL_DELTA) return DoublePoint.ZERO;

        final double noiseX, noiseY;
        final double stepSize = Math.hypot(xStepSize, yStepSize);
        final double noisiness = 8 - stepSize;
        if (noisiness > 0 && noisiness / 50 > random.nextDouble()) {
            noiseX = (random.nextDouble() - 0.5) * noisiness / noisinessDivider;
            noiseY = (random.nextDouble() - 0.5) * noisiness / noisinessDivider;
            x += noiseX;
            y += noiseY;
        } else return DoublePoint.ZERO;
        return new DoublePoint(noiseX, noiseY);
    }

    public DoublePoint get() {
        return new DoublePoint(x, y);
    }
}
