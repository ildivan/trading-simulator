package server;

import trading.*;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingDeque;

public class DataManager {
    private volatile HashMap<Integer,ClientData> clients;
    private volatile TreeMap<Stock,Integer> prices;
    private LinkedBlockingDeque<TradeReport> tradesToProcess;
    private TreeMap<Stock, TradingEngine> orderbooks;

    public DataManager() {
        clients = new HashMap<>();
        prices = new TreeMap<>();
        orderbooks = new TreeMap<>();
        for (Stock stock : Stock.values()){
            orderbooks.put(stock,new TradingEngine(this));
        }
    }

    public ClientData getClientData(int clientId){
        return clients.get(clientId);
    }

    public TreeMap<Stock,Integer> getPrices(){
        return prices;
    }

    public synchronized void addClient(int clientId) {
        clients.put(clientId, new ClientData());
    }

    public synchronized void removeClient(int clientId){
        clients.remove(clientId);
    }

    public synchronized boolean isOrderValid(Order order){
        ClientData data = clients.get(order.getOrderId());
        if(order.getSide() == OrderSide.BID){
            int totalPrice = order.getQuantity() * prices.get(order.getStock());
            return totalPrice <= data.getCash();
        }else{
            return order.getQuantity() <= data.getWallet().get(order.getStock());
        }
    }

    public synchronized void processOrder(Order order){
        TradingEngine orderbook = orderbooks.get(order.getStock());
        orderbook.insertOrder(order);
        Thread matchingThread = new Thread(orderbook::match);
        matchingThread.start();
    }

    public synchronized void submitTrade(TradeReport trade){
        ClientData buyerData = clients.get(trade.buyerOrderId());
        ClientData sellerData = clients.get(trade.sellerOrderId());

        int buyerNewCash = buyerData.getCash() - trade.quantity()* trade.price();
        int buyerNewQuantity = buyerData.getWallet().get(trade.stock()) + trade.quantity();
        buyerData.setCash(buyerNewCash);
        buyerData.getWallet().put(trade.stock(), buyerNewQuantity);

        int sellerNewCash = sellerData.getCash() + trade.quantity()* trade.price();
        int sellerNewQuantity = sellerData.getWallet().get(trade.stock()) - trade.quantity();
        sellerData.setCash(sellerNewCash);
        sellerData.getWallet().put(trade.stock(), sellerNewQuantity);
    }
}
