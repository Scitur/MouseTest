package mouse;

import java.awt.*;

public interface InterfaceMouse {
    Point getPosition();

    boolean move(Point point);
    default boolean move(int x, int y) {
        return move(new Point(x, y));
    }
    default boolean move(Rectangle rect) {
        return rect.contains(getPosition()) || move(rect.x + rect.width / 2, rect.y + rect.height / 2);
    }
    default boolean move(Shape shape) {
        return shape.contains(getPosition()) || move(shape.getBounds());
    }

    boolean click(boolean rightClick);
    default boolean click() {
        return click(false);
    }
    default boolean click(Point point, boolean rightClick) {
        return move(point) && click(rightClick);
    }
    default boolean click(Point point) {
        return click(point, false);
    }
    default boolean click(int x, int y, boolean rightClick) {
        return click(new Point(x,y), rightClick);
    }
    default boolean click(int x, int y) {
        return click(x,y,false);
    }
    default boolean click(Rectangle rect, boolean rightClick) {
        return move(rect) && click(rightClick);
    }
    default boolean click(Rectangle rect) {
        return move(rect) && click();
    }
    default boolean click(Shape shape, boolean rightClick) {
        return move(shape) && click(rightClick);
    }
    default boolean click(Shape shape) {
        return click(shape,false);
    }

    boolean moveFast(Point point);
    default boolean moveFast(int x, int y) {
        return moveFast(new Point(x, y));
    }
    default boolean moveFast(Rectangle rect) {
        return rect.contains(getPosition()) || moveFast(rect.x + rect.width / 2, rect.y + rect.height / 2);
    }
    default boolean moveFast(Shape shape) {
        return shape.contains(getPosition()) || moveFast(shape.getBounds());
    }

    boolean clickFast(boolean rightClick);
    default boolean clickFast() {
        return clickFast(false);
    }
    default boolean clickFast(Point point, boolean rightClick) {
        return moveFast(point) && clickFast();
    }
    default boolean clickFast(Point point) {
        return clickFast(point,false);
    }
    default boolean clickFast(int x, int y, boolean rightClick) {
        return clickFast(new Point(x,y),rightClick);
    }
    default boolean clickFast(int x, int y) {
        return clickFast(x,y,false);
    }
    default boolean clickFast(Rectangle rect, boolean rightClick) {
        return moveFast(rect) && clickFast(rightClick);
    }
    default boolean clickFast(Rectangle rect) {
        return clickFast(rect,false);
    }
    default boolean clickFast(Shape shape, boolean rightClick) {
        return moveFast(shape) && clickFast(rightClick);
    }
    default boolean clickFast(Shape shape) {
        return clickFast(shape,false);
    }

    default boolean rightClick() {
        return click(true);
    }
    default boolean rightClick(Point point) {
        return click(point,true);
    }
    default boolean rightClick(int x, int y) {
        return click(x,y,true);
    }
    default boolean rightClick(Rectangle rectangle) {
        return click(rectangle,true);
    }
    default boolean rightClick(Shape shape) {
        return click(shape,true);
    }

    boolean scrollDown(Point point);
    boolean scrollUp(Point point);
}