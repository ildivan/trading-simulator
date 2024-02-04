package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SalePanel extends Section implements ActionListener {
    private JRadioButton buyButton;
    private JRadioButton sellButton;
    private JComboBox<String> stockSelection;
    private JPanel selectStockPanel;
    private JTextField quantityField;
    private JButton purchaseButton;
    private static final Font INPUT_FONT = new Font("TImes New Roman",Font.BOLD,16);
    private final ChangeListener BUY_CHANGE_LISTENER;
    private final ChangeListener SELL_CHANGE_LISTENER;

    ;
    public SalePanel(String title) {
        super(title);

        BUY_CHANGE_LISTENER  = (evt) -> {
            UIManager.put("Button.select", new Color(0x28FF26));
            ButtonModel model = purchaseButton.getModel();
            if(model.isRollover()){
                purchaseButton.setBackground(new Color(0x2eab3f));
            }else{
                purchaseButton.setBackground(new Color(0x45ff5e));
            }
        };

        SELL_CHANGE_LISTENER  = (evt) -> {
            UIManager.put("Button.select", new Color(0xFF1919));
            ButtonModel model = purchaseButton.getModel();
            if(model.isRollover()){
                purchaseButton.setBackground(new Color(0xc43623));
            }else{
                purchaseButton.setBackground(new Color(0xE84A36));
            }
        };

        content.setLayout(new GridLayout(4,1,0,0));
        setupBuyOrSellChoicePanel();
        setupSelectStockPanel();
        setupInsertQuantityPanel();
        setupPurchaseButton();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == buyButton){
            setPurchaseButtonToBuy();
        }else if(actionEvent.getSource() == sellButton){
            setPurchaseButtonToSell();
        }else if(actionEvent.getSource() == purchaseButton){
            System.out.println("Type of order: " + (isSetToBuy() ? "Buy" : "Sell"));
            System.out.println("Selected stock: " + getSelectedStock());
            System.out.println("Selected quantity: " + getStockQuantity());
        }
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

        buyButton.addActionListener(this);
        sellButton.addActionListener(this);

        buyAndSellGroup.add(buyButton);
        buyAndSellGroup.add(sellButton);
        buyAndSellGroup.setSelected(buyButton.getModel(),true);
        buyAndSellChoicePanel.add(buyButton);
        buyAndSellChoicePanel.add(sellButton);
        buyAndSellChoicePanel.add(Box.createGlue());
        buyAndSellChoicePanel.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        content.add(buyAndSellChoicePanel);
    }

    private void setupRadioButton(JRadioButton button){
        button.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        button.setForeground(ClientView.FONT_COLOR);
        button.setFont(ClientView.HEADING_FONT);
        button.setFocusable(false);
    }

    private void setupSelectStockPanel() {
        selectStockPanel = new JPanel();
        selectStockPanel.setLayout(new BoxLayout(selectStockPanel,BoxLayout.X_AXIS));
        selectStockPanel.add(Box.createGlue());

        JLabel selectStockText = new JLabel();
        selectStockText.setText("Select stock: ");
        selectStockText.setFont(ClientView.CONTENT_FONT);
        selectStockText.setForeground(ClientView.FONT_COLOR);
        selectStockText.setHorizontalAlignment(SwingConstants.CENTER);
        selectStockText.setBorder(new EmptyBorder(0,0,0,30));
        selectStockPanel.add(selectStockText);

        stockSelection = new JComboBox<>();
        stockSelection.setPreferredSize(new Dimension(160,40));
        stockSelection.setMaximumSize(new Dimension(160,40));
        stockSelection.setFont(INPUT_FONT);
        stockSelection.setBackground(ClientView.HEADING_BACKGROUND_COLOR);
        stockSelection.setForeground(ClientView.FONT_COLOR);
        stockSelection.setFocusable(false);
        selectStockPanel.add(stockSelection);

        selectStockPanel.add(Box.createGlue());
        selectStockPanel.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        content.add(selectStockPanel);
    }

    public void setSelectionStocks(String... stocks){
        stockSelection.removeAllItems();
        for(int i = 0; i < stocks.length; i++){
            stockSelection.insertItemAt(stocks[i],i);
        }
        stockSelection.setSelectedIndex(0);
    }


    private void setupInsertQuantityPanel() {
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new BoxLayout(quantityPanel,BoxLayout.X_AXIS));
        quantityPanel.add(Box.createGlue());

        JLabel selectQuantityLabel = new JLabel();
        selectQuantityLabel.setText("Select quantity: ");
        selectQuantityLabel.setFont(ClientView.CONTENT_FONT);
        selectQuantityLabel.setForeground(ClientView.FONT_COLOR);
        selectQuantityLabel.setBorder(new EmptyBorder(0,0,0,30));

        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(100, 40));
        quantityField.setMaximumSize(new Dimension(100, 40));
        quantityField.setHorizontalAlignment(JTextField.RIGHT);
        quantityField.setBackground(ClientView.HEADING_BACKGROUND_COLOR);
        quantityField.setFont(INPUT_FONT);
        quantityField.setForeground(ClientView.FONT_COLOR);
        quantityField.setCaretColor(ClientView.FONT_COLOR);

        ((AbstractDocument) quantityField.getDocument()).setDocumentFilter(new NumericFilter());

        quantityPanel.add(selectQuantityLabel);
        quantityPanel.add(quantityField);
        quantityPanel.add(Box.createGlue());
        quantityPanel.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        content.add(quantityPanel);
    }

    private static class NumericFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            // Allow only numeric characters to be inserted
            if (string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            // Allow only numeric characters to replace existing text
            if (text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    private void setupPurchaseButton(){
        JPanel purchaseButtonPanel = new JPanel();
        purchaseButtonPanel.setLayout(new BoxLayout(purchaseButtonPanel,BoxLayout.X_AXIS));
        purchaseButtonPanel.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        purchaseButtonPanel.add(Box.createGlue());

        purchaseButton = new JButton();
        purchaseButton.setPreferredSize(new Dimension(150, 60));
        purchaseButton.setMaximumSize(new Dimension(150, 60));
        purchaseButton.setFont(ClientView.HEADING_FONT);
        purchaseButton.setFocusable(false);
        purchaseButton.setBorder(new EmptyBorder(0,0,0,0));
        purchaseButton.addActionListener(this);
        setPurchaseButtonToBuy();

        purchaseButtonPanel.add(purchaseButton);
        purchaseButtonPanel.add(Box.createGlue());
        content.add(purchaseButtonPanel);
    }

    private void setPurchaseButtonToBuy(){
        purchaseButton.setText("Buy");
        purchaseButton.setBackground(new Color(0x45ff5e));
        purchaseButton.removeChangeListener(SELL_CHANGE_LISTENER);
        purchaseButton.addChangeListener(BUY_CHANGE_LISTENER);
    }
    private void setPurchaseButtonToSell(){
        purchaseButton.setText("Sell");
        purchaseButton.setBackground(new Color(0xff3838));
        purchaseButton.removeChangeListener(BUY_CHANGE_LISTENER);
        purchaseButton.addChangeListener(SELL_CHANGE_LISTENER);
    }

    public boolean isSetToBuy(){
        return buyButton.isSelected();
    }

    public String getSelectedStock(){
        return (String) stockSelection.getSelectedItem();
    }

    public int getStockQuantity(){
        return (quantityField.getText().isBlank() ? 0 : Integer.parseInt(quantityField.getText()));
    }
}
