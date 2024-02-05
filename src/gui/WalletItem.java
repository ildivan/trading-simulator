package gui;

import javax.swing.*;
import java.awt.*;

public class WalletItem extends ListItem {
    private JLabel nameLabel;
    private JLabel quantityLabel;
    private JLabel valueLabel;

    public WalletItem(String name, int quantity, double value){
        super();
        removeMouseListener(this);
        setLayout(new BorderLayout(0,0));
        setupLabels(name, quantity, value);
    }

    private void setupLabels(String name, int quantity, double value){

        nameLabel = setupLabel(name);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        add(nameLabel,BorderLayout.WEST);


        quantityLabel = setupLabel("Q: " + quantity);
        quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        quantityLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        add(quantityLabel,BorderLayout.CENTER);

        valueLabel = setupLabel("$" + value);
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

    public void setQuantity(int quantity) {
        quantityLabel.setText("Q: " + quantity);
    }

    public void setValue(double value) {
        valueLabel.setText("$" + value);
    }


}
