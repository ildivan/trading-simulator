package client.gui;

import client.ClientView;

import javax.swing.*;
import java.awt.*;

public class PriceItem extends ClickableItem {
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel isRisingLabel;
    private static final Font ARROW_FONT = new Font("Times New Roman",Font.BOLD, 25);

    public PriceItem(String name, double price, boolean isRising) {
        super();
        setLayout(new BorderLayout(0,0));
        setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        setupLabels(name, price, isRising);
    }

    private void setupLabels(String name, double price, boolean isRising) {

        nameLabel = setupLabel(name);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        add(nameLabel,BorderLayout.WEST);

        priceLabel = setupLabel("$"+price);
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(priceLabel,BorderLayout.CENTER);

        isRisingLabel = setupLabel("-");
        setRisingStatus(isRising);
        isRisingLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(isRisingLabel,BorderLayout.EAST);
    }

    private JLabel setupLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(ClientView.CONTENT_FONT);
        label.setForeground(ClientView.FONT_COLOR);
        return label;
    }

    public void setRisingStatus(boolean isRising) {
        isRisingLabel.setFont(ARROW_FONT);
        if(isRising){
            isRisingLabel.setText("↑");
            isRisingLabel.setForeground(Color.GREEN);
            return;
        }
        isRisingLabel.setText("↓");
        isRisingLabel.setForeground(Color.RED);
    }

    public void setPrice(double price){
        priceLabel.setText("$"+price);
    }
    public String getName() {
        return nameLabel.getText();
    }
}