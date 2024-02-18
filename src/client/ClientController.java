package client;

import client.gui.*;

import java.util.ArrayList;

public class ClientController{
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

    public void handleItemClick(ListItem clickedItem){
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
}
