package gui;

import util.Constants;

import javax.swing.*;
import java.awt.Color;

public class MainMenuPanel extends CardPanel {
    public MainMenuPanel(CardHolderPanel cardPanel) {
        super(cardPanel);

        this.setBackground(Color.LIGHT_GRAY);

        addButton(Constants.PLAY, e -> onCardButtonClicked((JButton) e.getSource()));
        addButton(Constants.VIEW, e -> onCardButtonClicked((JButton) e.getSource()));
        addButton(Constants.EXIT, e -> System.exit(0));
    }
}
