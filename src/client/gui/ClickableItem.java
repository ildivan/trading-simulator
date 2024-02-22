package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickableItem extends JPanel implements MouseListener {

    public ClickableItem(){
        super();
        addMouseListener(this);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() instanceof JPanel clickedItem){
            clickedItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            clickedItem.setBackground(ClientView.SELECTED_COLOR);
        }
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() instanceof JPanel clickedItem){
            clickedItem.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            clickedItem.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }
}
