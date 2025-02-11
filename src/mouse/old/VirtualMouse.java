package mouse.old;

import lombok.Getter;
import mouse.InterfaceMouse;
import mouse.Provider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

public class VirtualMouse implements InterfaceMouse {
    private static final Robot ROBOT;

    static {
        try {
            ROBOT = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    protected static final int POINT_LIFETIME = 14;
    protected final int MAX_POINTS = 500; // Maximum number of points to store

    protected LinkedList<Point> points = new LinkedList<>();

    protected Point lastClick = new Point(-1, -1); // getter for last click
    protected Point lastClick2 = new Point(-1, -1); // getter for click before last click

    protected Point lastMove = new Point(-1, -1); // getter for last move

    protected float hue = 0.0f; // Initial hue value

    @Getter
    protected final ArrayList<Consumer<Point>> mouseMovementListener;

    private final ScheduledExecutorService scheduledExecutorService;
    private boolean exited = true;

    Timer timer = new Timer(POINT_LIFETIME, e -> {
        if (!points.isEmpty()) {
            try {
                points.removeFirst();
            } catch (Exception ignored) {}
        }
    });

    public VirtualMouse() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(10);
        this.mouseMovementListener = new ArrayList<>();
    }

    protected int getAutoDelay() {
        return ROBOT.getAutoDelay();
    }

    public void setLastClick(Point point) {
        lastClick2 = lastClick;
        lastClick = point;
    }

    private void handleClick(Point point, boolean rightClick) {
        final int button = rightClick ? MouseEvent.BUTTON3 : MouseEvent.BUTTON1;
        dispatchMouseEvent(MouseEvent.MOUSE_ENTERED, point);
        dispatchMouseEvent(MouseEvent.MOUSE_EXITED, point);
        dispatchMouseEvent(MouseEvent.MOUSE_MOVED, point);
        dispatchMouseEvent(MouseEvent.MOUSE_PRESSED, point, button);
        dispatchMouseEvent(MouseEvent.MOUSE_RELEASED, point, button);
        dispatchMouseEvent(MouseEvent.MOUSE_CLICKED, point, button);
        setLastClick(point);
    }

    private synchronized void dispatchMouseEvent(int event, Point point, int button) {
        final int clickCount;
        switch (event) {
            case MouseEvent.MOUSE_PRESSED:
            case MouseEvent.MOUSE_RELEASED:
            case MouseEvent.MOUSE_CLICKED:
                clickCount = 1;
                break;
            default:
                clickCount = 0;
        }

        MouseEvent mouseEvent = new MouseEvent(
                Provider.getClient().getCanvas(),
                event, System.currentTimeMillis(),
                0,
                point.x,
                point.y,
                clickCount,
                false,
                button
        );
        mouseEvent.setSource(Provider.EVENT_SRC);
        getCanvas().dispatchEvent(mouseEvent);

        if (event == MouseEvent.MOUSE_EXITED) exited = true;
        else if (event == MouseEvent.MOUSE_ENTERED) exited = false;
    }

    private synchronized void dispatchMouseEvent(int event, Point point) {
        dispatchMouseEvent(event, point, MouseEvent.NOBUTTON);
    }

    protected int randomizeClick() {
        throw new RuntimeException("Mouse randomizeClick is not currently implemented");
        //return (int) Rs2Random.normalRange(-10, 10, 4);
    }

    protected Color getRainbowColor() {
        hue += 0.001f; // Increment hue to cycle through colors
        if (hue > 1.0f) {
            hue = 0.0f; // Reset hue when it exceeds 1.0
        }
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    public Canvas getCanvas() {
        return Provider.getClient().getCanvas();
    }

    @Override
    public Point getPosition() {
        return new Point(lastMove);
    }

    @Override
    public boolean move(Point point) {
        this.mouseMovementListener.forEach(listener -> listener.accept(point));
        ROBOT.mouseMove(point.x,point.y);
        lastMove = new Point(point);
        return true;
    }

    @Override
    public boolean click(boolean rightClick) {
        handleClick(getPosition(), rightClick);
        return true;
    }

    @Override
    public boolean moveFast(Point point) {
        return move(point);
    }

    @Override
    public boolean clickFast(boolean rightClick) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public boolean scrollDown(Point point) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public boolean scrollUp(Point point) {
        throw new RuntimeException("Not Implemented");
    }
}
