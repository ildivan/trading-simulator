import client.ClientController;
import client.gui.ClientView;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
         ClientController controller = new ClientController();
         ClientView view = controller.getView();
         view.addStockToWallet("BTC", 3,60000.0);
         view.addStockToWallet("ETH", 10,4200.0);
         view.addOrder("BUY","BTC",2,19000);
         view.addOrder("SELL","BTC",2,19000);
         view.addOrder("BUY","ETH",2,400);
         view.addOrder("SELL","ETH",2,400);
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