package gui.game;

import util.Constants;
import util.VisualizeUtil;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MyMouseListener implements MouseListener, MouseMotionListener {
    private final MyFrame comp;
    private Instant start;

    private List<Point> path;
    protected List<Integer> clicks;
    protected List<Integer> misclicks;

    private Rectangle target = new Rectangle(Constants.TARGET_SIZE, Constants.TARGET_SIZE);

    public MyMouseListener(MyFrame parent) {
        comp = parent;
        reset();
    }

    public void reset() {
        path = new ArrayList<>();
        clicks = new ArrayList<>();
        misclicks = new ArrayList<>();
        start = Instant.now();
    }

    public void paint(Graphics g) {
        if (Constants.LIVE_VIEW) {
            final Point[] points = path.toArray(new Point[0]);
            VisualizeUtil.drawPath(g, points);

            g.setColor(Color.BLUE);
            for (int click : misclicks) {
                final Point clickPoint = points[click];
                g.fillOval(clickPoint.x-2, clickPoint.y-2,5,5);
            }

            g.setColor(Color.GREEN);
            for (int click : clicks) {
                final Point clickPoint = points[click];
                g.fillOval(clickPoint.x-2, clickPoint.y-2,5,5);
            }
        }
    }

    private void logMouseEvent(MouseEvent e) {
        Instant tmp = Instant.now();
        //System.out.printf("%s - %s%n", tmp.toString(), e.toString());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        logMouseEvent(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        logMouseEvent(e);
        if (target.contains(e.getX()-comp.target.x, e.getY()-comp.target.y)) {
            clicks.add(path.size()-1);
        } else {
            misclicks.add(path.size()-1);
            if (!Constants.REPOSITION_ON_MISCLICK) return;
        }

        comp.onTargetClicked();
        if (Constants.LIVE_VIEW) comp.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        logMouseEvent(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        logMouseEvent(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        logMouseEvent(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        logMouseEvent(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        path.add(e.getPoint());
        if (Constants.LIVE_VIEW) comp.repaint();
        logMouseEvent(e);
    }
}
