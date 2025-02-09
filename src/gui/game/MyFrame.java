package gui.game;

import util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MyFrame extends JPanel {
    private final MyMouseListener listener;
    final private JButton startButton;
    protected Point target;

    private int clicks;

    public MyFrame() {
        this.setLayout(null);

        listener = new MyMouseListener(this);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onPanelResize();
            }
        });

        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);

        this.setVisible(true);

        startButton = new JButton("Start");

        startButton.addActionListener(e -> {
            startButton.setVisible(false);
            listener.reset();
            repositionTarget();
            this.repaint();
        });
        this.add(startButton);
        reset();
    }

    private void reset() {
        clicks = 0;
        target = new Point(-Constants.TARGET_SIZE, -Constants.TARGET_SIZE);
        startButton.setVisible(true);

        final int centerX = (int) (this.getWidth() - startButton.getPreferredSize().getWidth()) / 2;
        final int centerY = (int) (this.getHeight() - startButton.getPreferredSize().getHeight()) / 2;

        startButton.setBounds(centerX, centerY, startButton.getPreferredSize().width, startButton.getPreferredSize().height);
        this.repaint();
    }

    private void onPanelResize() {
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
