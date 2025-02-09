package gui.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MyFrame extends JPanel {
    private final int SIZE = 75;
    private final int MAX_CLICKS = 10;

    private final MyMouseListener listener;
    final private JButton startButton;
    final private JButton button;

    private int clicks;

    public MyFrame() {
        this.setLayout(null);

        listener = new MyMouseListener(this);

        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onPanelResize();
            }
        });

        this.setVisible(true);

        startButton = new JButton("Start");
        button = new JButton();

        startButton.addActionListener(e -> {
            startButton.setVisible(false);
            repositionButton();
            button.setVisible(true);
        });
        this.add(startButton);

        button.setBackground(Color.CYAN);
        button.addActionListener(e -> {
            if(clicks++ >= MAX_CLICKS) {
                reset();
                return;
            }

            repositionButton();
        });
        this.add(button);
        reset();
    }

    private void reset() {
        clicks = 0;
        button.setVisible(false);
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

        g.setColor(Color.RED);
        listener.paint(g);
    }

    public void repositionButton() {
        int x = (int) (Math.random() * (this.getWidth() - SIZE - 16));
        int y = (int) (Math.random() * (this.getHeight() - SIZE - 39));
        button.setBounds(x, y, SIZE, SIZE);
    }
}
