package client.gui;

import client.ClientView;

import javax.swing.*;
import java.awt.*;

public class OrderStatusPanel extends Section {
    private JLabel orderIdLabel;
    private JLabel orderSideLabel;
    private JLabel stockLabel;
    private JLabel quantityLabel;
    private JLabel priceLabel;
    private JLabel statusLabel;

    public OrderStatusPanel() {
        super("ORDER");
        content.setLayout(new GridLayout(6,1));
        orderIdLabel = new JLabel();
        orderSideLabel = new JLabel();
        stockLabel = new JLabel();
        quantityLabel = new JLabel();
        priceLabel = new JLabel();
        statusLabel = new JLabel();
        setupLabel(orderIdLabel);
        setupLabel(orderSideLabel);
        setupLabel(stockLabel);
        setupLabel(quantityLabel);
        setupLabel(priceLabel);
        setupLabel(statusLabel);
    }

    public void setOrder(int orderId, String orderSide, String stock, int quantity, double price, String status){
        orderIdLabel.setText("Order number: " + orderId);
        orderSideLabel.setText("Order type: " + orderSide);
        stockLabel.setText("Stock: " + stock);
        quantityLabel.setText("Quantity: " + quantity);
        priceLabel.setText("Price : " + price);
        statusLabel.setText("Order status: " + status);
    }

    private void setupLabel(JLabel label){
        label.setFont(ClientView.CONTENT_FONT);
        label.setForeground(ClientView.FONT_COLOR);

        JPanel row = new JPanel();
        row.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        row.setLayout(new BoxLayout(row,BoxLayout.X_AXIS));
        row.add(Box.createGlue());
        row.add(label);
        row.add(Box.createGlue());

        content.add(row);
    }
}
