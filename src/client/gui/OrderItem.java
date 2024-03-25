package client.gui;


import client.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrderItem extends ClickableItem {
    private int orderId;
    private JLabel orderSideLabel;
    private JLabel nameLabel;
    private JLabel quantityLabel;
    private JLabel cancelLabel;

    public OrderItem(int orderId, String orderSide, String stockName, int quantity){
        super();
        this.orderId = orderId;
        setLayout(new GridLayout(1,4));
        setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        setupLabels(orderSide,stockName,quantity);
    }

    private void setupLabels(String orderSide, String name, int quantity) {
        orderSideLabel = setupLabel(orderSide, new Font("Times New Roman", Font.BOLD, 16), ClientView.FONT_COLOR);
        orderSideLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        orderSideLabel.setForeground(orderSideLabel.getText().equals("BUY") ? new Color(0x7BCA44) : new Color(0xFF3737));
        add(orderSideLabel);

        nameLabel = setupLabel(name, ClientView.CONTENT_FONT, ClientView.FONT_COLOR);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(nameLabel);

        quantityLabel = setupLabel("Q: " + quantity, ClientView.CONTENT_FONT, ClientView.FONT_COLOR);
        quantityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(quantityLabel);

        cancelLabel = setupLabel("CANCEL", new Font("Times New Roman", Font.BOLD, 16), new Color(0xBE2424));
        cancelLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        cancelLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cancelLabel.setForeground(new Color(0xFF3A3A));
                cancelLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                setBackground(new Color(0x14F73B3B, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                cancelLabel.setForeground(new Color(0xBE2424));
                cancelLabel.setCursor(Cursor.getDefaultCursor());
                setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
            }
        });
        add(cancelLabel);
    }

    private JLabel setupLabel(String text, Font font, Color fontColor) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(font);
        label.setForeground(fontColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public int getOrderId(){
        return orderId;
    }

    public void setQuantity(int quantity){
        quantityLabel.setText("Q: " + quantity);
    }

    public JLabel getCancelLabel(){
        return cancelLabel;
    }
}
