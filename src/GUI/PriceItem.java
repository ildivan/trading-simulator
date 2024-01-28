package GUI;

import javax.swing.*;
import java.awt.*;

public class PriceItem extends JPanel {
    private String name;
    private float price;
    private boolean isRising;

    public PriceItem(String name, float price, boolean isRising) {
        this.name = name;
        this.price = price;
        this.isRising = isRising;
        setLayout(new BorderLayout());
        setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        setupLabels();
    }

    private void setupLabels() {

        JLabel nameLabel = setupLabel(name);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        add(nameLabel,BorderLayout.WEST);

        JLabel priceLabel = setupLabel("$"+price);
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(priceLabel,BorderLayout.CENTER);

        JLabel risingLabel = setupLabel("-");
        risingLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(risingLabel,BorderLayout.EAST);
    }

    private JLabel setupLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(ClientView.CONTENT_FONT);
        label.setForeground(ClientView.FONT_COLOR);
        return label;
    }
}
