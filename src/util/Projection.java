package util;

import java.awt.*;

public class Projection {
    protected Point origin;
    protected double vx, vy, vSquared;

    public Projection(Point line1, Point line2) {
        this.origin = line1;

        // Compute the direction vector AB
        vx = line2.x - line1.x;
        vy = line2.y - line1.y;

        // Compute the squared magnitude of AB
        vSquared = vx * vx + vy * vy;
    }

    public Point project(Point point) {
        // Compute the vector AP
        double apx = point.x - origin.x;
        double apy = point.y - origin.y;

        // Compute the dot product of AB and AP
        double dotABAP = vx * apx + vy * apy;

        // Compute the projection parameter t
        double t = dotABAP / vSquared;

        // Compute the projected point Q
        double xq = origin.x + t * vx;
        double yq = origin.y + t * vy;

        return new Point((int) xq, (int) yq);
    }

    public Point project(Projection projectTo, Point point) {
        // Compute the vector AP
        double apx = point.x - origin.x;
        double apy = point.y - origin.y;

        // Compute the dot product of AB and AP
        double dotABAP = vx * apx + vy * apy;

        // Compute the projection parameter t
        double t = dotABAP / vSquared;

        // Compute the projected point Q
        double xq = origin.x + t * vx;
        double yq = origin.y + t * vy;

        return new Point((int) xq, (int) yq);
    }
}
