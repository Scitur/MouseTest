package gui;

import gui.game.GamePanel;
import util.Constants;
import util.PointTransformer;
import util.VisualizeUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewPanel extends CardPanel {
    private Integer[] clicks, misclicks;
    private final List<Point[]> paths;
    private boolean normalize = false;

    private Point dstOrigin, dstDst;

    public ViewPanel(CardHolderPanel cardPanel) {
        super(cardPanel,Layout.NORTH);

        this.setBackground(Color.LIGHT_GRAY);

        paths = new ArrayList<>();
        clicks = misclicks = new Integer[0];

        addButton(Constants.MAIN_MENU, e -> onCardButtonClicked((JButton) e.getSource()));
        addButton("Normalize", e -> {
            normalize = !normalize;
            if (clicks.length != GamePanel.listener.getClicks().length) loadFromGame(); // TODO: Change

            if (normalize) {
                ((JButton) e.getSource()).setText("Undo");
                if (paths.size() == 1) normalize();
            } else {
                ((JButton) e.getSource()).setText("Normalize");
            }
            this.repaint();
        });

        this.setVisible(true);

        dstOrigin = new Point(this.getWidth()/10, this.getHeight()/2);
        dstDst = new Point(9*this.getWidth()/10, this.getHeight()/2);
    }

    @Override
    protected void onPanelResize() {
        if (!wasResized(this.getSize())) return;
        super.onPanelResize();
        System.out.println("1 Resize");

        dstOrigin = new Point(this.getWidth()/10, this.getHeight()/2);
        dstDst = new Point(9*this.getWidth()/10, this.getHeight()/2);

        System.out.println("2 Resize " + dstOrigin);
        System.out.println("3 Resize " + dstDst);
    }

    private void normalize() {
        PointTransformer transformer;
        for(int i = 1; i < clicks.length; i++) {
            final Point[] tmp = new Point[clicks[i]-clicks[i-1]+1];
            System.arraycopy(paths.get(0),clicks[i-1],tmp,0,tmp.length);

            transformer = new PointTransformer(tmp[0], tmp[tmp.length-1], dstOrigin, dstDst);
            for (int j = 0; j < tmp.length; j++) {
                tmp[j] = transformer.transform(tmp[j]);
            }

            paths.add(tmp);
        }
    }

    private void loadFromGame() {
        System.out.println("Load from Game");
        clicks = GamePanel.listener.getClicks();
        misclicks = GamePanel.listener.getMisclicks();

        final Point[] tmp = GamePanel.listener.getPath();
        final int start = clicks[0];
        final int end = clicks[clicks.length-1];

        final Point[] path = new Point[end-start+1];

        for (int i = 0; i < clicks.length; i++) {
            clicks[i] = clicks[i]-start;
        }
        // TODO: Fix misclicks
        System.arraycopy(tmp,start,path,0,path.length);
        paths.add(path);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (paths.isEmpty()) return;

        System.out.println("Paint");
        g.setColor(Color.RED);
        if (normalize) {
            for (int i = 1; i < paths.size(); i++) {
                VisualizeUtil.drawPath(g, paths.get(i));
            }
            final int size = 5;
            final int sizeHalf = size/2;
            g.setColor(Color.GREEN);
            g.fillOval(dstOrigin.x-sizeHalf,dstOrigin.y-sizeHalf,size,size);
            g.setColor(Color.BLUE);
            g.fillOval(dstDst.x-sizeHalf, dstDst.y-sizeHalf,size,size);
            return;
        }
        VisualizeUtil.drawPath(g, paths.get(0));

        g.setColor(Color.BLUE);
        for (int click : misclicks) {
            if(click < 0 || click >= paths.get(0).length) continue;
            final Point clickPoint = paths.get(0)[click];
            g.fillOval(clickPoint.x-2, clickPoint.y-2,5,5);
        }

        g.setColor(Color.GREEN);
        for (int click : clicks) {
            final Point clickPoint = paths.get(0)[click];
            g.fillOval(clickPoint.x-2, clickPoint.y-2,5,5);
        }
    }
}
