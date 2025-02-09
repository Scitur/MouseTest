package gui.game;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JPanel {
    private final int SIZE = 75;

    private JButton button = null;
    private MyMouseListener listener;

    public MyFrame() {
        this.setLayout(null);

        listener = new MyMouseListener(this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);

        this.setVisible(true);

        generateNewButton();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.RED);
        listener.paint(g);
    }

    public void generateNewButton() {
        if (button != null) {
            this.remove(button);
        }

        // Create a JButton
        button = new JButton("Click Me");
        button.addActionListener(e -> {
            button.setText("");
            button.setBackground(Color.CYAN);
            repositionButton();
        });

        // Calculate the center position of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) (screenSize.getWidth() - button.getPreferredSize().getWidth()) / 2;
        int centerY = (int) (screenSize.getHeight() - button.getPreferredSize().getHeight()) / 2;

        // Set the button's location to the center of the screen
        button.setBounds(centerX, centerY, button.getPreferredSize().width, button.getPreferredSize().height);

        this.add(button);
        this.repaint();
    }

    public void repositionButton() {
        int x = (int) (Math.random() * (this.getWidth() - SIZE - 16));
        int y = (int) (Math.random() * (this.getHeight() - SIZE - 39));
        button.setBounds(x, y, SIZE, SIZE);
    }
}
