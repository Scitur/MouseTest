package gui;

import util.Constants;

import javax.swing.*;
import java.awt.*;

public class ViewPanel extends CardPanel {
    public ViewPanel(CardHolderPanel cardPanel) {
        super(cardPanel);

        this.setBackground(Color.LIGHT_GRAY);

        addButton(Constants.MAIN_MENU, e -> onCardButtonClicked((JButton) e.getSource()));
        addButton("Normalize", e -> onCardButtonClicked((JButton) e.getSource()));
    }
}
