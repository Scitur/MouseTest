package mouse;

import lombok.Getter;
import mouse.old.VirtualMouse;

import java.awt.*;

public final class Provider {
    public static String EVENT_SRC = "RobotMouse";
    public static VirtualMouse ROBOT_MOUSE = new VirtualMouse();
    public static Dimension SCREEN_SIZE = new Dimension(1920,1080);
    public static Canvas CANVAS = new Canvas();

    @Getter
    static Client client = new Client();

    public static Dimension getScreenSize() {
        return SCREEN_SIZE;
    }

    public static void sleep(long time) {}

    public static class Client {
        public boolean isClientThread() {
            return false;
        }

        public Canvas getCanvas() {
            return CANVAS;
        }

        public int getCanvasWidth() {
            return getCanvas().getWidth();
        }

        public int getCanvasHeight() {
            return getCanvas().getHeight();
        }
    }
}
