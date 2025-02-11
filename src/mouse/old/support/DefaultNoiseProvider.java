package mouse.old.support;

import java.util.Random;

public class DefaultNoiseProvider {
    public static final double DEFAULT_NOISINESS_DIVIDER = 2;
    private static final double SMALL_DELTA = 10e-6;
    private final double noisinessDivider;

    /**
     * @param noisinessDivider bigger value means less noise.
     */
    public DefaultNoiseProvider(double noisinessDivider) {
        this.noisinessDivider = noisinessDivider;
    }

    public DefaultNoiseProvider() {
        this(DEFAULT_NOISINESS_DIVIDER);
    }

    public DoublePoint getNoise(Random random, double xStepSize, double yStepSize) {
        if (Math.abs(xStepSize - 0) < SMALL_DELTA && Math.abs(yStepSize - 0) < SMALL_DELTA) {
            return DoublePoint.ZERO;
        }

        final double noiseX, noiseY;
        final double stepSize = Math.hypot(xStepSize, yStepSize);
        final double noisiness = 8 - stepSize;
        if (noisiness > 0 && noisiness / 50 > random.nextDouble()) {
            noiseX = (random.nextDouble() - 0.5) * noisiness / noisinessDivider;
            noiseY = (random.nextDouble() - 0.5) * noisiness / noisinessDivider;
        } else {
            return DoublePoint.ZERO;
        }
        return new DoublePoint(noiseX, noiseY);
    }
}
