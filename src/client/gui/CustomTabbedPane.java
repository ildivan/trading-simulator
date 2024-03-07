package client.gui;

import javax.swing.*;
import java.awt.*;

public class CustomTabbedPane extends JTabbedPane {
    public CustomTabbedPane(int tabPlacement){
        super(tabPlacement);
    }
    @Override
    protected void paintComponent(Graphics g) {
        // Set the background color behind all tabs
        g.setColor(new Color(0x23262D));
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);

    }
}
