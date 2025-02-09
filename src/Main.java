import gui.CardPanel;
import gui.MainMenuPanel;
import gui.game.MyFrame;
import util.Constants;

import javax.swing.*;

public class Main {
    private static CardPanel cardPanel;

    public static void main(String ... args) {
        JFrame frame = new JFrame("Full Screen JFrame");

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new CardPanel();
        frame.add(cardPanel);

        cardPanel.add(new MainMenuPanel(cardPanel), Constants.MAIN_MENU);
        cardPanel.add(new MyFrame(), Constants.PLAY);

        //new MainMenuPanel(frame)
        frame.setVisible(true);
    }
}
