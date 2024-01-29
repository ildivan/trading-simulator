package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SalePanel extends Section {
    private JRadioButton buyButton;
    private JRadioButton sellButton;
    private String[] stocks;
    public SalePanel(String title, String... stocks) {
        super(title);
        JPanel content = getContent();
        content.setLayout(new GridLayout(4,1,0,0));
        setupBuyOrSellChoicePanel();
        setupSelectStockPanel(stocks);
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

    private void setupSelectStockPanel(String... stocks) {
        JPanel selectStockPanel = new JPanel();
        selectStockPanel.setLayout(new BoxLayout(selectStockPanel,BoxLayout.X_AXIS));
        selectStockPanel.add(Box.createGlue());

        JLabel selectStockText = new JLabel();
        selectStockText.setText("Select stock: ");
        selectStockText.setFont(ClientView.CONTENT_FONT);
        selectStockText.setForeground(ClientView.FONT_COLOR);
        selectStockText.setHorizontalAlignment(SwingConstants.CENTER);
        selectStockPanel.add(selectStockText);

        JComboBox<String> stockSelection = new JComboBox<>(stocks);
        stockSelection.setPreferredSize(new Dimension(160,40));
        stockSelection.setMaximumSize(new Dimension(160,40));
        stockSelection.setFont(new Font("TImes New Roman",Font.BOLD,16));
        stockSelection.setBackground(ClientView.HEADING_BACKGROUND_COLOR);
        stockSelection.setForeground(ClientView.FONT_COLOR);
        selectStockPanel.add(stockSelection);

        selectStockText.setBorder(new EmptyBorder(0,0,0,30));

        selectStockPanel.add(Box.createGlue());
        selectStockPanel.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        getContent().add(selectStockPanel);
    }
}
