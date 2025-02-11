package mouse.old.support;

// SinusoidalDeviationProvider
public class DeviationProvider {
    public static final int DEFAULT_SLOPE_DIVIDER = 10;

    private final double slopeDivider;

    public DeviationProvider(double slopeDivider) {
        this.slopeDivider = slopeDivider;
    }

    public DeviationProvider() {
        this(DEFAULT_SLOPE_DIVIDER);
    }

    public DoublePoint getDeviation(double totalDistanceInPixels, double percentageOfTotalDistance) {
        double deviationFunctionResult = (1 - Math.cos(percentageOfTotalDistance * Math.PI * 2)) / 2;

        double deviationX = totalDistanceInPixels / slopeDivider;
        double deviationY = totalDistanceInPixels / slopeDivider;

        return new DoublePoint(deviationFunctionResult * deviationX, deviationFunctionResult * deviationY);
    }
}