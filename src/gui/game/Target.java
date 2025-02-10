package gui.game;

import util.Constants;

import java.awt.*;
import java.util.Random;

public class Target extends Point {
    private static final Random random = new Random();

    private Rectangle confines;
    private double dx, dy, vx, vy;

    public Target(Rectangle confines, Point point) {
        super(point);
        this.confines = confines;
        vx = vy = 0;
        dx = point.x;
        dy = point.y;
    }

    public Target(Rectangle confines, int x, int y) {
        this(confines,new Point(x,y));
    }

    public Target(Rectangle confines) {
        this(confines,0,0);
    }

    public void setConfines(Rectangle confines) {
        this.confines = confines;
    }

    public double[] generateRandomVector() {
        double angle = random.nextDouble() * 2 * Math.PI; // Random angle [0, 2π]
        vx = Constants.TARGET_SPEED * Math.cos(angle);
        vy = Constants.TARGET_SPEED * Math.sin(angle);
        return new double[]{vx, vy};
    }

    private boolean breachesConfines(int x, int y, int width, int height) {
        return x + width > confines.width || y + height > confines.height || x < 0 || y < 0;
    }

    private void confineToConfines() {
        final int maxX = confines.width-Constants.TARGET_SIZE;
        final int maxY = confines.height-Constants.TARGET_SIZE;
        setLocation(Math.min(maxX,Math.max(0, x)),Math.min(maxY,Math.max(0, y)));
    }

    public void move(int ms) {
        if(vx == 0 && vy == 0) generateRandomVector();
        if(breachesConfines(x, y, Constants.TARGET_SIZE, Constants.TARGET_SIZE)) confineToConfines();

        double tdx = dx + vx * ms / 1000;
        double tdy = dy + vy * ms / 1000;
        int tries = 0;
        while(breachesConfines((int) tdx, (int) tdy, Constants.TARGET_SIZE, Constants.TARGET_SIZE)) {
            generateRandomVector();
            tdx = dx + vx * ms / 1000;
            tdy = dy + vy * ms / 1000;

            if (++tries >= 1000) break;
        }

        dx = tdx;
        dy = tdy;

        super.setLocation(dx,dy);
        System.out.printf("Move %s dv={%.2f,%.2f} %n",this.getLocation(),dx,dy);
    }

    public void reposition() {
        dx = Math.random() * (confines.getWidth() - Constants.TARGET_SIZE);
        dy = Math.random() * (confines.getHeight() - Constants.TARGET_SIZE);

        super.setLocation(dx,dy);
        System.out.println("Reposition " + this.getLocation());
    }

    @Override
    public void setLocation(double x, double y) {
        super.setLocation(x,y);
        this.dx = x;
        this.dy = y;
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x,y);
        this.dx = x;
        this.dy = y;
    }

    enum Behavior {
        REPOSITION_ON_LEAVING,
        BOUNCE,
        NEW_RANDOM_VECTOR
    }
}
