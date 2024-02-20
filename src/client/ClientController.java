package client;

import client.gui.ClientView;
import client.gui.OrderItem;
import client.gui.PriceItem;
import client.gui.SalePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientController implements ActionListener {
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

    public void handleItemClick(JPanel clickedItem){
        if(clickedItem instanceof PriceItem priceItem){
            System.out.println("Price  item clicked");
        }else if(clickedItem instanceof OrderItem orderItem){
            System.out.println("order item clicked");
        }
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
}
