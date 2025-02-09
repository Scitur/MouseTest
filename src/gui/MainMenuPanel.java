package gui;

import util.Constants;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainMenuPanel extends JPanel {
    private final CardPanel cardPanel;

    private List<JButton> buttons;

    public MainMenuPanel(CardPanel cardPanel) {
        this.cardPanel = cardPanel;

        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onPanelResize();
            }
        });

        buttons = new ArrayList<>();
        addButton(Constants.PLAY, e -> onPlayClicked((JButton) e.getSource()));
        addButton(Constants.VIEW, null);
        addButton(Constants.EXIT, null);
    }

    private void addButton(String name, Consumer<ActionEvent> listener) {
        final JButton button = new JButton(name);

        if (listener != null) button.addActionListener(listener::accept);

        this.add(button);
        buttons.add(button);
    }

    private void onPanelResize() {
        System.out.println("Main Menu Panel resized to: " + this.getSize());

        int maxWidthPreferred = 0;
        int maxHeightPreferred = 0;

        for(JButton button : buttons) {
            final Dimension dim = button.getPreferredSize();
            maxWidthPreferred = (int) Math.max(maxWidthPreferred, dim.getWidth());
            maxHeightPreferred = (int) Math.max(maxHeightPreferred, dim.getHeight());
        }

        final int spacing = maxHeightPreferred / 3;
        final int totalHeight = maxHeightPreferred * buttons.size() + spacing * (buttons.size()-1);

        Rectangle center = calculateCenter(this.getSize(), new Dimension(maxWidthPreferred, totalHeight));

        for(int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setBounds(center.x, center.y + i*(maxHeightPreferred+spacing), maxWidthPreferred, maxHeightPreferred);
        }
    }

    private Rectangle calculateCenter(Dimension parent, Dimension child) {
        final int childWidth = (int) child.getWidth();
        final int childHeight = (int) child.getHeight();

        final int parentWidthCenter = (int) (parent.getWidth()-childWidth) / 2;
        final int parentHeightCenter = (int) (parent.getHeight()-childHeight) / 2;

        System.out.printf("x-%d y-%d h-%d w-%d%n", parentWidthCenter, parentHeightCenter, childWidth, childHeight);

        return new Rectangle(parentWidthCenter, parentHeightCenter, childWidth, childHeight);
    }

    protected void onPlayClicked(JButton button) {
        button.setBackground(Color.CYAN);

        cardPanel.getLayout().show(cardPanel, button.getText());

        onPanelResize();
        this.repaint();
    }
}
