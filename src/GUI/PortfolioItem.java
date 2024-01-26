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
        setupLabels();
    }

    private void setupLabels(){
        Font font = new Font("Times New Roman",Font.PLAIN,16);

        JLabel nameLabel = setupLabel(name, font);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        add(nameLabel,BorderLayout.WEST);


        JLabel quantityLabel = setupLabel("Q: " + quantity, font );
        quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        quantityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(quantityLabel,BorderLayout.CENTER);

        float value = price * (float)quantity;
        JLabel valueLabel = setupLabel("$" + value, font);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(valueLabel,BorderLayout.EAST);
    }

    private JLabel setupLabel(String text, Font font){
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(font);
        return label;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
