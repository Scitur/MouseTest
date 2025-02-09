import java.awt.*;

public class VisualizeUtil {
    public static void drawPath(Graphics graphics, Point ... points) {
        for(int i = 1; i < points.length; i++) {
            graphics.drawLine(points[i-1].x, points[i-1].y, points[i].x, points[i].y);
        }
    }

    public static Point[] translate(Point newStart, Point newEnd, Point ... points) {
        Point[] translatePoints = new Point[points.length];
        translatePoints[0] = newStart;
        translatePoints[translatePoints.length - 1] = newEnd;
        return null;
    }
}
