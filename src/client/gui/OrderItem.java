package client.gui;


import client.ClientView;

import javax.swing.*;
import java.awt.*;

public class OrderItem extends ClickableItem {
    private int orderId;
    private JLabel orderSideLabel;
    private JLabel nameLabel;
    private JLabel quantityLabel;

    public OrderItem(int orderId, String orderSide, String stockName, int quantity){
        super();
        this.orderId = orderId;
        setLayout(new GridLayout(1,3));
        setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        setupLabels(orderSide,stockName,quantity);
    }

    private void setupLabels(String orderSide, String name, int quantity) {
        orderSideLabel = setupLabel(orderSide, new Font("Times New Roman", Font.BOLD, 16));
        orderSideLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        orderSideLabel.setForeground(orderSideLabel.getText().equals("BUY") ? new Color(0x7BCA44) : new Color(0xFF3737));
        add(orderSideLabel);

        nameLabel = setupLabel(name, ClientView.CONTENT_FONT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(nameLabel);

        quantityLabel = setupLabel("Q: " + quantity, ClientView.CONTENT_FONT);
        quantityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(quantityLabel);
    }

    private JLabel setupLabel(String text, Font font) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(font);
        label.setForeground(ClientView.FONT_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public int getOrderId(){
        return orderId;
    }

    public void setQuantity(int quantity){
        quantityLabel.setText("Q: " + quantity);
    }
}
