import util.PointTransformer;

import java.awt.*;

public class Test {
    public static void main(String ... args) {
        //basicTest();
        fadeTest();
    }

    public static void fadeTest() {
        final int steps = 11;
        int effectFadeSteps = 5;

        effectFadeSteps = Math.max(1, effectFadeSteps);
        for (int i = 0; i < steps; i++) {
            final int stepsLeft = steps - i - 1;
            final double effectFadeMultiplier = stepsLeft > effectFadeSteps ? 1 : stepsLeft / (double) effectFadeSteps;
            System.out.printf("%d. %d %.2f%n", i, stepsLeft, effectFadeMultiplier);
        }
    }

    public static void basicTest() {
        Point srcOrigin = new Point(0,0);
        Point srcDst = new Point(5,5);
        Point point = new Point(0,0);

        PointTransformer transformer = new PointTransformer(srcOrigin, srcDst, srcDst, srcOrigin);

        System.out.println(transformer);
        System.out.println(transformer.debugString());
        System.out.println(transformer.transform(point));
    }
}
