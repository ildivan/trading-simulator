package server;

import trading.*;

import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataManager {
    private volatile HashMap<Integer,ClientData> clients;
    private volatile TreeMap<Stock,Integer> prices;
    private LinkedBlockingDeque<TradeReport> tradesToProcess;
    private TreeMap<Stock, TradingEngine> orderbooks;
    private static final int RANGE_STARTING_PRICE = 100001;
    private static final int BASE_STARTING_PRICE = 1000;
    private static final int STARTING_CASH = 200000;

    public DataManager() {
        clients = new HashMap<>();
        prices = new TreeMap<>();
        orderbooks = new TreeMap<>();
        tradesToProcess = new LinkedBlockingDeque<>();

        setDefaultPrices();
        setupOrderBooks();

        ScheduledExecutorService processTradesThreadManager = Executors.newSingleThreadScheduledExecutor();
        processTradesThreadManager.scheduleAtFixedRate(
                () -> {
                    TradeReport tradeToProcess = tradesToProcess.poll();
                    if(tradeToProcess != null){
                        processTrade(tradeToProcess);
                    }},
                0, 50, TimeUnit.MILLISECONDS);
    }

    private void setDefaultPrices() {
        Random randGenerator = new Random();
        for (Stock stock : Stock.values()){
            prices.put(stock, randGenerator.nextInt(RANGE_STARTING_PRICE) + BASE_STARTING_PRICE);
        }
    }

    private void setupOrderBooks() {
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
        clients.get(clientId).setCash(STARTING_CASH);
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

    public void submitTrade(TradeReport trade){
        tradesToProcess.add(trade);
    }

    private void processTrade(TradeReport trade){
        setPrice(trade.stock(),trade.price());

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

    private void setPrice(Stock stock, int price) {
        prices.put(stock,price);
    }
}
