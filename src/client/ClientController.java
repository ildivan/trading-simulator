package client;

import client.gui.ClientView;
import client.gui.OrderItem;
import client.gui.PriceItem;
import client.gui.SalePanel;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ClientController implements ActionListener, MouseListener, WindowListener {
    private ClientModel model;
    private ClientView view;

    public ClientController() {
        this.model = new ClientModel(this);
        this.view = new ClientView(this);

        
    }

    public ClientModel getModel() {
        return model;
    }

    public ClientView getView() {
        return view;
    }

    public void updateWallet(ArrayList<String> stockNames, ArrayList<Integer> quantityList, ArrayList<Double> values){
        view.setStocksInWallet(stockNames,quantityList,values);
    }

    public void updatePrices(ArrayList<String> stockNames, ArrayList<Double> priceList, ArrayList<Boolean> risingStatus){
        view.setStockPrices(stockNames, priceList, risingStatus);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        SalePanel sale = view.getSalePanel();
        JButton purchaseButton = sale.getPurchaseButton();
        if(actionEvent.getSource() == purchaseButton){
            System.out.println("Type of order: " + (sale.isSetToBuy() ? "Buy" : "Sell"));
            System.out.println("Selected stock: " + sale.getSelectedStock());
            System.out.println("Selected quantity: " + sale.getStockQuantity());
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() instanceof JPanel clickedItem){
            handleItemClick(clickedItem);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    public void handleItemClick(JPanel clickedItem){
        if(clickedItem instanceof PriceItem priceItem){
            System.out.println("Price  item clicked");
        }else if(clickedItem instanceof OrderItem orderItem){
            System.out.println("order item clicked");
        }
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        model.stopClient();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}