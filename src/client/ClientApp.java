package client;

import client.gui.ClientView;

import java.util.ArrayList;

public class ClientApp {
    public static void main(String[] args) {
         ClientController controller = new ClientController();
         ClientView view = controller.getView();
         ClientModel model = controller.getModel();
         Thread connectionThread = new Thread(model::connect);
         connectionThread.start();
         view.addStockToWallet("BTC", 3,60000.0);
         view.addStockToWallet("ETH", 10,4200.0);
         view.addOrder("BUY","BTC",2,19000);
         view.addOrder("SELL","BTC",2,19000);
         view.addOrder("BUY","ETH",2,400);
         view.addOrder("SELL","ETH",2,400);
         view.setOrderStatus(1234,"18/2/2024 10:35","BUY","BTC",4,19000,"PENDING");
         view.addStockPrice("BTC",20000.0,true);
         view.addStockPrice("ETH",420.0,false);
         ArrayList<Double> prices = new ArrayList<>();
         prices.add(500.0);
         prices.add(400.0);
         prices.add(250.0);
         prices.add(300.0);
         view.setGraphPrices(prices);
    }
}