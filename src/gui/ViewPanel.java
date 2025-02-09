package gui;

import gui.game.GamePanel;
import util.Constants;
import util.VisualizeUtil;

import javax.swing.*;
import java.awt.*;

public class ViewPanel extends CardPanel {
    public ViewPanel(CardHolderPanel cardPanel) {
        super(cardPanel);

        this.setBackground(Color.LIGHT_GRAY);

        addButton(Constants.MAIN_MENU, e -> onCardButtonClicked((JButton) e.getSource()));
        addButton("Normalize", e -> onCardButtonClicked((JButton) e.getSource()));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        final Integer[] clicks = GamePanel.listener.getClicks();
        final Integer[] misclicks = GamePanel.listener.getMisclicks();
        final Point[] tmp = GamePanel.listener.getPath();

        final int start = clicks[0];
        final int end = clicks[clicks.length-1];
        final Point[] path = new Point[end-start+1];

        for (int i = 0; i < clicks.length; i++) {
            clicks[i] = clicks[i]-start;
        }
        // TODO: Fix misclicks

        if (end - start >= 0) System.arraycopy(tmp,start,path,0,end-start+1);

        g.setColor(Color.RED);
        VisualizeUtil.drawPath(g, path);

        g.setColor(Color.BLUE);
        for (int click : misclicks) {
            if(click < 0 || click >= path.length) continue;
            final Point clickPoint = path[click];
            g.fillOval(clickPoint.x-2, clickPoint.y-2,5,5);
        }

        g.setColor(Color.GREEN);
        for (int click : clicks) {
            final Point clickPoint = path[click];
            g.fillOval(clickPoint.x-2, clickPoint.y-2,5,5);
        }
    }
}
