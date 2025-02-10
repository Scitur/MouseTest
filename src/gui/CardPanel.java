package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class CardPanel extends JPanel {
    protected final CardHolderPanel cardPanel;
    protected final List<JButton> buttons;

    private final Dimension prevSize;

    protected Layout layout;

    public CardPanel(CardHolderPanel cardPanel) {
        this(cardPanel,Layout.CENTER);
    }

    public CardPanel(CardHolderPanel cardPanel, Layout layout) {
        this.cardPanel = cardPanel;
        this.layout = layout;

        this.buttons = new ArrayList<>();
        this.prevSize = new Dimension(0,0);

        this.setLayout(null);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onPanelResize();
            }
        });
    }

    protected void addButton(String name, Consumer<ActionEvent> listener) {
        final JButton button = new JButton(name);

        if (listener != null) button.addActionListener(listener::accept);

        this.add(button);
        buttons.add(button);
    }

    protected void onCardButtonClicked(JButton button) {
        cardPanel.getLayout().show(cardPanel, button.getText());
        this.repaint();
    }

    protected Rectangle calculateCenter(Dimension parent, Dimension child) {
        final int childWidth = (int) child.getWidth();
        final int childHeight = (int) child.getHeight();
        final int parentWidthCenter = (int) (parent.getWidth()-childWidth) / 2;
        final int parentHeightCenter = (int) (parent.getHeight()-childHeight) / 2;

        return new Rectangle(parentWidthCenter, parentHeightCenter, childWidth, childHeight);
    }

    protected boolean wasResized(Dimension newDim) {
        return prevSize.width != newDim.width || prevSize.height != newDim.height;
    }

    protected void onPanelResize() {
        if (!wasResized(this.getSize())) return;

        prevSize.setSize(this.getSize());
        System.out.println(this.getClass().getSimpleName() + " resized to: " + this.getSize());

        if (buttons.isEmpty()) return;

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

        switch (layout) {
            case NORTH:
                center.x = Math.min(center.x, spacing);
            default:
                break;
        }

        for(int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setBounds(center.x, center.y + i*(maxHeightPreferred+spacing), maxWidthPreferred, maxHeightPreferred);
        }
    }

    enum Layout {
        CENTER,
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
