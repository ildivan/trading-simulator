package client.gui;

import client.ClientView;

import javax.swing.*;
import java.awt.*;

public class OrderStatusPanel extends Section {
    public OrderStatusPanel() {
        super("ORDER");
        content.setLayout(new GridLayout(6,1));
    }

    public void setOrder(int orderId, String orderSide, String stock, int quantity, double price, String status){
        content.removeAll();
        content.add(getRow("Order number: " + orderId));
        content.add(getRow("Type of order: " + orderSide));
        content.add(getRow("Stock: " + stock));
        content.add(getRow("Quantity: " + quantity));
        content.add(getRow("Price: " + price));
        content.add(getRow("Order status: " + status));
    }

    private JPanel getRow(String text){
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(ClientView.CONTENT_FONT);
        label.setForeground(ClientView.FONT_COLOR);

        JPanel row = new JPanel();
        row.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        row.setLayout(new BoxLayout(row,BoxLayout.X_AXIS));
        row.add(Box.createGlue());
        row.add(label);
        row.add(Box.createGlue());

        return row;
    }
}
