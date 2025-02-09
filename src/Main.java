import gui.CardHolderPanel;
import gui.MainMenuPanel;
import gui.ViewPanel;
import gui.game.GamePanel;
import util.Constants;

import javax.swing.*;

public class Main {
    private static CardHolderPanel cardPanel;

    public static void main(String ... args) {
        JFrame frame = new JFrame("Full Screen JFrame");

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new CardHolderPanel();
        frame.add(cardPanel);

        cardPanel.add(new MainMenuPanel(cardPanel), Constants.MAIN_MENU);
        cardPanel.add(new GamePanel(cardPanel), Constants.PLAY);
        cardPanel.add(new ViewPanel(cardPanel), Constants.VIEW);

        //new MainMenuPanel(frame)
        frame.setVisible(true);
    }
}
