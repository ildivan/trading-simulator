package GUI;

import javax.swing.*;
import java.awt.*;

public class PortfolioItem extends JPanel {
    private final String name;
    private float price;
    private int quantity;

    public PortfolioItem(String name, float price, int quantity){
        super();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        setLayout(new BorderLayout());
        setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        setupLabels();
    }

    private void setupLabels(){

        JLabel nameLabel = setupLabel(name);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        add(nameLabel,BorderLayout.WEST);


        JLabel quantityLabel = setupLabel("Q: " + quantity);
        quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        quantityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(quantityLabel,BorderLayout.CENTER);

        float value = price * (float)quantity;
        JLabel valueLabel = setupLabel("$" + value);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(valueLabel,BorderLayout.EAST);
    }

    private JLabel setupLabel(String text){
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(ClientView.CONTENT_FONT);
        label.setForeground(ClientView.FONT_COLOR);
        return label;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
