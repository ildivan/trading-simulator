package client.gui;


import client.ClientController;

import javax.swing.*;
import java.awt.*;

public class OrderItem extends ListItem {
    private JLabel orderSideLabel;
    private JLabel nameLabel;
    private JLabel quantityLabel;
    private JLabel priceLabel;

    public OrderItem(ClientController controller,String orderSide, String stockName, int quantity, double price){
        super(controller);
        setLayout(new GridLayout(1,4));
        setupLabels(orderSide,stockName,quantity,price);
    }

    private void setupLabels(String orderSide, String name, int quantity, double price) {
        orderSideLabel = setupLabel(orderSide, new Font("Times New Roman", Font.BOLD, 16));
        orderSideLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        add(orderSideLabel);

        nameLabel = setupLabel(name, ClientView.CONTENT_FONT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(nameLabel);

        quantityLabel = setupLabel("Q: " + quantity, ClientView.CONTENT_FONT);
        quantityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(quantityLabel);

        priceLabel = setupLabel("price: " + price, ClientView.CONTENT_FONT);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(priceLabel);
    }

    private JLabel setupLabel(String text, Font font) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(font);
        label.setForeground(ClientView.FONT_COLOR);
        return label;
    }
}
