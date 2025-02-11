package gui;

import mouse.Provider;
import mouse.old.api.MouseMotion;
import mouse.old.api.MouseMotionFactory;
import util.Constants;
import util.VisualizeUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RobotPanel extends CardPanel {
    private Integer[] clicks, misclicks;
    private final List<Point[]> paths;
    private boolean normalize = false;

    private Point dstOrigin, dstDst;

    public RobotPanel(CardHolderPanel cardPanel) {
        super(cardPanel, CardPanel.Layout.NORTH);

        this.setBackground(Color.LIGHT_GRAY);

        paths = new ArrayList<>();
        clicks = misclicks = new Integer[0];

        addButton(Constants.MAIN_MENU, e -> onCardButtonClicked((JButton) e.getSource()));
        addButton("Generate", e -> generate());
        addButton("Normalize", e -> {
            normalize = !normalize;
            if (normalize) {
                ((JButton) e.getSource()).setText("Undo");
            } else {
                ((JButton) e.getSource()).setText("Normalize");
            }
            this.repaint();
        });

        this.setVisible(true);

        dstOrigin = new Point(this.getWidth() / 10, this.getHeight() / 2);
        dstDst = new Point(9 * this.getWidth() / 10, this.getHeight() / 2);
    }

    @Override
    protected void onPanelResize() {
        if (!wasResized(this.getSize())) return;
        super.onPanelResize();

        dstOrigin = new Point(this.getWidth() / 10, this.getHeight() / 2);
        dstDst = new Point(9 * this.getWidth() / 10, this.getHeight() / 2);
    }

    private void generate() {
        System.out.println("Generating");
        Provider.CANVAS.setSize(this.getSize());

        List<Point> points = new ArrayList<>();
        Provider.ROBOT_MOUSE.getMouseMovementListener().add(points::add);
        Provider.ROBOT_MOUSE.move(dstOrigin);

        MouseMotionFactory factory = new MouseMotionFactory();
        MouseMotion motion = factory.build(dstDst.x, dstDst.y);
        try {
            motion.move();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Provider.ROBOT_MOUSE.getMouseMovementListener().clear();
        paths.add(points.toArray(new Point[0]));
        this.repaint();
    }

    private void normalize() {
        /*PointTransformer transformer;
        for (int i = 1; i < clicks.length; i++) {
            final Point[] tmp = new Point[clicks[i] - clicks[i - 1] + 1];
            System.arraycopy(paths.get(0), clicks[i - 1], tmp, 0, tmp.length);

            transformer = new PointTransformer(tmp[0], tmp[tmp.length - 1], dstOrigin, dstDst);
            for (int j = 0; j < tmp.length; j++) {
                tmp[j] = transformer.transform(tmp[j]);
            }

            paths.add(tmp);
        }*/
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (paths.isEmpty()) return;

        System.out.println("RobotPanel Paint");
        g.setColor(Color.RED);
        if (normalize) {
            for (int i = 0; i < paths.size(); i++) {
                g.setColor(new Color((47*i)%255, (55*i)%255, (33*i)%255));
                VisualizeUtil.drawPath(g, paths.get(i));
            }
            final int size = 5;
            final int sizeHalf = size / 2;
            g.setColor(Color.GREEN);
            g.fillOval(dstOrigin.x - sizeHalf, dstOrigin.y - sizeHalf, size, size);
            g.setColor(Color.BLUE);
            g.fillOval(dstDst.x - sizeHalf, dstDst.y - sizeHalf, size, size);
            return;
        }
        VisualizeUtil.drawPath(g, paths.get(0));

        g.setColor(Color.BLUE);
        for (int click : misclicks) {
            if (click < 0 || click >= paths.get(0).length) continue;
            final Point clickPoint = paths.get(0)[click];
            g.fillOval(clickPoint.x - 2, clickPoint.y - 2, 5, 5);
        }

        g.setColor(Color.GREEN);
        for (int click : clicks) {
            final Point clickPoint = paths.get(0)[click];
            g.fillOval(clickPoint.x - 2, clickPoint.y - 2, 5, 5);
        }
    }
}
