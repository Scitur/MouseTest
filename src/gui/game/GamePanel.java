package gui.game;

import gui.CardPanel;
import gui.CardHolderPanel;
import util.Constants;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends CardPanel {
    public static GameMouseListener listener;
    protected Point target;

    public GamePanel(CardHolderPanel cardPanel) {
        super(cardPanel);

        listener = new GameMouseListener(this);

        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);

        this.setVisible(true);

        addButton("Start", e -> {
            for (JButton button : buttons) {
                button.setVisible(false);
            }
            listener.reset();
            repositionTarget();
            this.repaint();
        });
        addButton(Constants.MAIN_MENU,e -> onCardButtonClicked((JButton) e.getSource()));
        reset();
    }

    private void reset() {
        System.out.println("reset");
        target = new Point(-Constants.TARGET_SIZE, -Constants.TARGET_SIZE);
        for (JButton button : buttons) {
            button.setVisible(true);
        }
        this.repaint();
    }

    @Override
    protected void onPanelResize() {
        if (!wasResized(this.getSize())) return;
        super.onPanelResize();
        reset();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.CYAN);
        g.fillRect(target.x, target.y, Constants.TARGET_SIZE, Constants.TARGET_SIZE);

        g.setColor(Color.RED);
        listener.paint(g);
    }

    public void onTargetClicked() {
        if (listener.clicks.size() >= Constants.TARGET_MAX_CLICKS) {
            reset();
            this.repaint();
        } else {
            repositionTarget();
        }
    }

    private void repositionTarget() {
        int x = (int) (Math.random() * (this.getWidth() - Constants.TARGET_SIZE));
        int y = (int) (Math.random() * (this.getHeight() - Constants.TARGET_SIZE));

        target.setLocation(x, y);
    }
}
