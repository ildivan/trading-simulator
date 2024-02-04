import GUI.ClientView;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
         ClientView cl = new ClientView();
         cl.addStockToWallet("BTC", 3,60000.0);
         cl.addStockToWallet("ETH", 10,4200.0);
         cl.addStockPrice("BTC",20000.0,true);
         cl.addStockPrice("ETH",420.0,false);
         ArrayList<Double> prices = new ArrayList<>();
         prices.add(500.0);
         prices.add(400.0);
         prices.add(250.0);
         prices.add(300.0);
         cl.setGraphPrices(prices);
    }
}