package gui;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {
    CardLayout layout;

    public CardPanel() {
        layout = new CardLayout();
        this.setBackground(Color.RED);
        this.setLayout(layout);
    }

    @Override
    public CardLayout getLayout() {
        return layout;
    }
}
