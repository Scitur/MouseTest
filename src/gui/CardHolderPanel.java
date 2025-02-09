package gui;

import javax.swing.*;
import java.awt.*;

public class CardHolderPanel extends JPanel {
    CardLayout layout;

    public CardHolderPanel() {
        layout = new CardLayout();
        this.setBackground(Color.RED);
        this.setLayout(layout);
    }

    @Override
    public CardLayout getLayout() {
        return layout;
    }
}
