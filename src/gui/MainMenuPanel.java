package gui;

import util.Constants;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainMenuPanel extends CardPanel {
    public MainMenuPanel(CardHolderPanel cardPanel) {
        super(cardPanel,true);

        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);

        addButton(Constants.PLAY, e -> onCardButtonClicked((JButton) e.getSource()));
        addButton(Constants.VIEW, e -> onCardButtonClicked((JButton) e.getSource()));
        addButton(Constants.EXIT, e -> System.exit(0));
    }
}
