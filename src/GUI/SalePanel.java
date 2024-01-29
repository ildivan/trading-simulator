package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SalePanel extends Section {
    private JRadioButton buyButton;
    private JRadioButton sellButton;
    public SalePanel(String title) {
        super(title);
        JPanel content = getContent();
        content.setLayout(new GridLayout(4,1,0,0));
        setupBuyOrSellChoicePanel();
    }

    private void setupBuyOrSellChoicePanel(){
        ButtonGroup buyAndSellGroup = new ButtonGroup();
        JPanel buyAndSellChoicePanel = new JPanel();
        buyAndSellChoicePanel.setLayout(new BoxLayout(buyAndSellChoicePanel,BoxLayout.X_AXIS));
        buyAndSellChoicePanel.add(Box.createGlue());
        buyButton = new JRadioButton("Buy");
        sellButton = new JRadioButton("Sell");
        setupRadioButton(buyButton);
        setupRadioButton(sellButton);
        buyButton.setBorder(new EmptyBorder(0,0,0,20));
        sellButton.setBorder(new EmptyBorder(0,20,0,0));

        buyAndSellGroup.add(buyButton);
        buyAndSellGroup.add(sellButton);
        buyAndSellGroup.setSelected(buyButton.getModel(),true);
        buyAndSellChoicePanel.add(buyButton);
        buyAndSellChoicePanel.add(sellButton);
        buyAndSellChoicePanel.add(Box.createGlue());
        buyAndSellChoicePanel.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        getContent().add(buyAndSellChoicePanel);
    }

    private void setupRadioButton(JRadioButton button){
        button.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        button.setForeground(ClientView.FONT_COLOR);
        button.setFont(ClientView.HEADING_FONT);
        button.setFocusable(false);
    }
}
