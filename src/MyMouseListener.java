import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MyMouseListener implements MouseListener, MouseMotionListener {
    private Component comp;
    private Instant start;
    private boolean first;

    private final List<Point> path = new ArrayList<>();

    public MyMouseListener(Component parent) {
        comp = parent;
        first = true;
        start = Instant.now();
    }

    public void paint(Graphics g) {
        System.out.println("Paint");
        VisualizeUtil.drawPath(g, path.toArray(new Point[0]));
    }

    private void logMouseEvent(MouseEvent e) {
        Instant tmp = Instant.now();
        System.out.printf("%s - %s%n", tmp.toString(), e.toString());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (first && !path.isEmpty()) {
            first = false;
            path.clear();
        }
        logMouseEvent(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        logMouseEvent(e);
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
        comp.repaint();
        logMouseEvent(e);
    }
}
