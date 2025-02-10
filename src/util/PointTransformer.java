package util;

import java.awt.*;

public class PointTransformer {
    private final double sx, sy, svx, svy, dx, dy, dvx, dvy;
    private final double thetaS, thetaD, scale;

    public PointTransformer(double srcX, double srcY, double srcVectorX, double srcVectorY,
                            double dstX, double dstY, double dstVectorX, double dstVectorY) {
        this.sx = srcX;
        this.sy = srcY;
        this.svx = srcVectorX;
        this.svy = srcVectorY;

        this.dx = dstX;
        this.dy = dstY;
        this.dvx = dstVectorX;
        this.dvy = dstVectorY;

        final double Ls = Math.sqrt(srcVectorX * srcVectorX + srcVectorY * srcVectorY);
        final double Ld = Math.sqrt(dstVectorX * dstVectorX + dstVectorY * dstVectorY);

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

        // Scale distance
        final double rd = rs * scale;

        // Compute new position in destination frame (corrected rotation)
        final double xNew = dx + rd * Math.cos(thetaD + alphaS);
        final double yNew = dy + rd * Math.sin(thetaD + alphaS);

        return new Point((int) Math.round(xNew), (int) Math.round(yNew));
    }

    public Point transformOld(double x, double y) {
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

    public String toString() {
        return String.format("%s[origin={%d,%d} vector={%d,%d} dstOrigin={%d,%d} dstVector={%d,%d}]", this.getClass().getCanonicalName(),
                (int) sx, (int) sy, (int) svx, (int) svy,
                (int) dx, (int) dy, (int) dvx, (int) dvy);
    }

    public String debugString() {
        return String.format("%s[scale=%.2f thetaS=%.2f thetaD=%.2f]", this.getClass().getCanonicalName(),
                scale, thetaS, thetaD);
    }
}
