package util;

import java.awt.*;

public class PointTransformer {
    private final double sx, sy, dx, dy;
    private final double thetaS, thetaD, scale;

    public PointTransformer(double srcX, double srcY, double srcVectorX, double srcVectorY,
                            double dstX, double dstY, double dstVectorX, double dstVectorY) {
        this.sx = srcX;
        this.sy = srcY;
        this.dx = dstX;
        this.dy = dstY;

        double Ls = Math.sqrt(srcVectorX * srcVectorX + srcVectorY * srcVectorY);
        double Ld = Math.sqrt(dstVectorX * dstVectorX + dstVectorY * dstVectorY);

        if (Ls == 0 || Ld == 0) {
            throw new IllegalArgumentException("Source or destination vector must not be zero-length.");
        }

        this.thetaS = Math.atan2(srcVectorY, srcVectorX);
        this.thetaD = Math.atan2(dstVectorY, dstVectorX);
        this.scale = Ld / Ls; // Precompute the scaling factor
    }

    public PointTransformer(Point srcOrigin, Point srcDest, Point dstOrigin, Point dstDest) {
        this(srcOrigin.x,srcOrigin.y,srcDest.x-srcOrigin.x,srcDest.y-srcOrigin.y,
                dstOrigin.x,dstOrigin.y,dstDest.x-dstOrigin.x,dstDest.y-dstOrigin.y);
    }

    public Point transform(Point point) {
        return transform(point.x, point.y);
    }

    public Point transform(double x, double y) {
        // Compute relative position in source frame
        final double px = x - sx;
        final double py = y - sy;
        final double rs = Math.sqrt(px * px + py * py);
        final double alphaS = Math.atan2(py, px) - thetaS;

        // Scale distance and adjust angle
        final double rd = rs * scale;
        final double alphaD = alphaS + (thetaD - thetaS);

        // Compute new position in destination frame
        final int xNew = (int) Math.round(dx + rd * Math.cos(thetaD + alphaD));
        final int yNew = (int) Math.round(dy + rd * Math.sin(thetaD + alphaD));

        return new Point(xNew,yNew);
    }
}
