import util.PointTransformer;

import java.awt.*;

public class Test {
    public static void main(String ... args) {
        basicTest();
    }

    public static void basicTest() {
        Point srcOrigin = new Point(0,0);
        Point srcDst = new Point(5,5);
        Point point = new Point(3,3);

        PointTransformer transformer = new PointTransformer(srcOrigin, srcDst, srcDst, srcOrigin);

        System.out.println(transformer);
        System.out.println(transformer.debugString());
        System.out.println(transformer.transform(point));
    }
}
