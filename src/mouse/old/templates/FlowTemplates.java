package mouse.old.templates;

import mouse.old.support.Flow;

import java.util.Arrays;

public enum FlowTemplates {
    VARIATING(
            10, 13, 14, 19, 16, 13, 15, 22, 56, 90, 97, 97, 66, 51, 50, 66, 91, 95, 87, 96, 98,
            88, 70, 62, 57, 63, 79, 93, 98, 97, 100, 104, 83, 49, 37, 53, 68, 73, 61, 51, 64, 107,
            103, 111, 94, 88, 95, 86, 88, 97, 108, 85, 86, 74, 72, 73, 58, 50, 50, 60, 62, 61, 52,
            53, 44, 30, 21, 25, 21, 17, 16, 13, 8, 2, 6, 9, 6, 3, 7, 12, 13, 15, 11, 9,
            9, 7, 6, 4, 1, 2, 3, 2, 2, 11, 15, 7, 1, 0, 0, 1
    ),
    INTERRUPTED(
            12, 11, 10, 20, 24, 19, 26, 15, 9, 9, 10, 24, 26, 30, 24, 49, 72, 60, 81, 113, 82,
            99, 67, 10, 7, 7, 7, 10, 8, 7, 9, 6, 6, 7, 10, 11, 12, 8, 7, 3, 0, 2,
            8, 10, 10, 12, 6, 4, 4, 3, 8, 11, 11, 11, 11, 13, 11, 20, 25, 18, 21, 23, 56,
            40, 36, 58, 69, 60, 63, 51, 87, 71, 86, 66, 115, 97, 80, 65, 50, 66, 57, 24, 11, 11,
            7, 3, 0, 0, 1, 3, 3, 5, 6, 12, 11, 7, 11, 17, 17, 23
    ),
    INTERRUPTED_2(
            12, 11, 10, 20, 24, 19, 26, 15, 9, 9, 10, 24, 26, 30, 24, 49, 72, 60, 81, 113, 82,
            99, 67, 10, 12, 8, 11, 15, 16, 17, 17, 12, 16, 37, 10, 25, 12, 11, 41, 10, 12, 11,
            40, 36, 52, 61, 60, 64, 51, 82, 71, 81, 66, 105, 92, 59, 65, 51, 66, 54, 21, 21, 12,
            40, 36, 58, 69, 60, 63, 51, 87, 71, 86, 66, 115, 97, 80, 65, 50, 66, 57, 24, 11, 11,
            7, 3, 0, 0, 1, 3, 3, 5, 6, 12, 11, 7, 11, 17, 17, 23
    ),
    SLOW_STARTUP(
            8, 5, 1, 1, 1, 2, 2, 3, 3, 3, 5, 7, 9, 10, 10, 11, 11, 11, 12, 12, 13,
            15, 14, 13, 15, 15, 17, 17, 18, 18, 20, 19, 20, 20, 19, 20, 19, 20, 21, 22, 20, 17,
            20, 22, 18, 20, 21, 18, 20, 20, 18, 20, 19, 21, 19, 19, 19, 19, 20, 19, 20, 21, 19,
            19, 17, 21, 21, 17, 19, 18, 20, 18, 19, 24, 34, 43, 35, 40, 41, 42, 42, 38, 40, 40,
            37, 36, 42, 40, 63, 85, 98, 92, 103, 102, 95, 86, 70, 52, 31, 19
    ),
    SLOW_STARTUP_2(
            7, 2, 1, 2, 2, 3, 5, 9, 10, 10, 11, 13, 13, 10, 4, 1, 1, 2, 3, 4, 6,
            9, 11, 11, 10, 14, 11, 9, 2, 1, 2, 2, 3, 4, 8, 9, 10, 11, 11, 13, 13, 15,
            14, 15, 18, 17, 19, 21, 20, 19, 18, 20, 20, 20, 20, 19, 20, 19, 19, 18, 20, 20, 19,
            20, 18, 20, 21, 19, 21, 18, 19, 25, 37, 37, 35, 41, 43, 41, 41, 40, 48, 81, 108, 91,
            88, 74, 46, 19, 46, 84, 35, 14, 19, 12, 13, 18, 38, 35, 11, 4
    ),
    JAGGED(
            52, 106, 122, 8, 6, 117, 32, 2, 68, 34, 21, 81, 61, 86, 55, 4, 104, 21, 51, 8, 93,
            90, 43, 65, 82, 31, 40, 115, 107, 13, 35, 73, 81, 67, 31, 79, 57, 100, 55, 64, 13, 54,
            18, 68, 82, 61, 11, 84, 37, 20, 68, 33, 36, 55, 68, 75, 56, 20, 41, 120, 63, 72, 102,
            49, 4, 48, 69, 50, 35, 49, 54, 19, 95, 121, 26, 78, 31, 62, 53, 123, 73, 22, 39, 72,
            98, 33, 26, 5, 103, 23, 75, 35, 69, 33, 44, 12, 10, 101, 122, 19
    ),
    STOPPING(
            8, 20, 39, 48, 66, 71, 79, 57, 29, 5, 2, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 6, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 6, 10, 12, 15, 19,
            37, 60, 100, 103, 98, 82, 87, 74, 65, 51, 57, 54, 61, 46, 38, 16
    ),
    ADJUSTING(
            1, 1, 1, 3, 8, 7, 2, 2, 4, 8, 6, 3, 7, 13, 18, 19, 24, 35, 26, 14, 31,
            43, 49, 55, 61, 67, 61, 50, 43, 37, 30, 16, 5, 4, 4, 3, 3, 3, 4, 4, 3,
            2, 2, 3, 10, 14, 10, 7, 5, 5
    ),
    CONSTANT(constantSpeed());

    public final double[] flow;

    FlowTemplates(double... flow) {
        this.flow = flow;
    }

    public Flow getFlow() {
        return new Flow(this.flow);
    }

    public static double[] random() {
        double[] result = new double[100];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) (Math.random() * 100);
        }
        return result;
    }

    private static double[] constantSpeed() {
        final double[] flowBuckets = new double[10];
        Arrays.fill(flowBuckets, 100);
        return flowBuckets;
    }
}
