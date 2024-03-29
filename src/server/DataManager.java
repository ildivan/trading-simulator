package server;

import exceptions.ClientNotFoundException;
import exceptions.OrderNotFoundException;
import trading.*;

import java.util.ArrayList;
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
    private volatile int orderCounter;
    private static final int RANGE_STARTING_PRICE = 20001;
    private static final int BASE_STARTING_PRICE = 1000;
    private static final int STARTING_QUANTITY_PER_STOCK = 2;
    private final int STARTING_CASH;

    public DataManager() {
        clients = new HashMap<>();
        prices = new TreeMap<>();
        orderbooks = new TreeMap<>();
        tradesToProcess = new LinkedBlockingDeque<>();
        orderCounter = 0;

        setDefaultPrices();

        STARTING_CASH = calculateStartingCash();

        setupOrderBooks();

        ScheduledExecutorService processTradesThreadManager = Executors.newSingleThreadScheduledExecutor();
        processTradesThreadManager.scheduleAtFixedRate(
                () -> {
                    TradeReport tradeToProcess = tradesToProcess.poll();
                    if(tradeToProcess != null){
                        processTrade(tradeToProcess);
                    }},
                0, 100, TimeUnit.MILLISECONDS);
    }

    private int calculateStartingCash() {
        int startingCash = 0;
        for(Stock stock : Stock.values()){
            startingCash += prices.get(stock)*STARTING_QUANTITY_PER_STOCK;
        }
        return startingCash;
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
        setDefaultStocks(clientId);
    }

    private void setDefaultStocks(int clientId) {
        for (Stock stock : Stock.values()){
            clients.get(clientId).getWallet().put(stock,STARTING_QUANTITY_PER_STOCK);
        }
    }

    public synchronized void removeClient(int clientId){
        clients.remove(clientId);
    }

    public boolean isOrderValid(int clientId, Order order){
        if(order.getSide() == OrderSide.BID){
            int totalPrice = order.getQuantity() * prices.get(order.getStock());
            return totalPrice <= getAvailableCash(clientId);
        }else{
            return order.getQuantity() <= getAvailableQuantity(clientId,order.getStock());
        }
    }

    private int getAvailableCash(int clientId){
        ClientData data = clients.get(clientId);
        int availableCash = data.getCash();
        for(Order order : data.getOrders()){
            if(
                    order.getSide() == OrderSide.BID
                    && order.getStatus() == OrderStatus.ACCEPTED
                    && !order.isCompleted()
            ){
                availableCash -= order.getQuantity() * order.getPrice();
            }
        }
        return availableCash;
    }

    private int getAvailableQuantity(int clientId, Stock stock){
        ClientData data = clients.get(clientId);
        int availableQuantity = (data.getWallet().get(stock) == null ? 0 : data.getWallet().get(stock));
        for(Order order : data.getOrders()){
            if(
                    order.getSide() == OrderSide.ASK
                    && order.getStock() == stock
                    && order.getStatus() == OrderStatus.ACCEPTED
                    && !order.isCompleted()
            ){
                availableQuantity -= order.getQuantity();
            }
        }
        return availableQuantity;
    }

    public synchronized void processOrder(int clientId, Order order){
        orderCounter += 1;
        order.setOrderId(orderCounter);
        order.setStatus(OrderStatus.ACCEPTED);

        if(!isOrderValid(clientId,order)){
            order.setStatus(OrderStatus.REJECTED);
        }else{
            TradingEngine orderbook = orderbooks.get(order.getStock());
            orderbook.insertOrder(order);
        }

        ClientData data = clients.get(clientId);
        ArrayList<Order> orders = data.getOrders();
        orders.add(order);
    }

    public void submitTrade(TradeReport trade){
        tradesToProcess.add(trade);
    }

    private void processTrade(TradeReport trade){
        setPrice(trade.stock(),trade.price());

        try{
            ClientData buyerData = findClientFromOrderId(trade.buyerOrderId());

            int buyerNewCash = buyerData.getCash() - trade.quantity()* trade.price();
            int buyerNewQuantity = buyerData.getWallet().get(trade.stock()) + trade.quantity();
            buyerData.setCash(buyerNewCash);
            buyerData.getWallet().put(trade.stock(), buyerNewQuantity);
        }catch(ClientNotFoundException ignored){} //Exception thrown if seller disconnected

        try{
            ClientData sellerData = findClientFromOrderId(trade.sellerOrderId());

            int sellerNewCash = sellerData.getCash() + trade.quantity() * trade.price();
            int sellerNewQuantity = sellerData.getWallet().get(trade.stock()) - trade.quantity();
            sellerData.setCash(sellerNewCash);
            sellerData.getWallet().put(trade.stock(), sellerNewQuantity);
        }catch(ClientNotFoundException ignored){} //Exception thrown if buyer disconnected
    }

    private ClientData findClientFromOrderId(int orderId) throws ClientNotFoundException {
        for(ClientData client : clients.values()){
            ArrayList<Order> clientOrders = client.getOrders();
            for (Order order : clientOrders){
                if(order.getOrderId() == orderId){
                    return client;
                }
            }
        }
        throw new ClientNotFoundException(orderId);
    }

    private Order findOrderFromOrderId(int clientId , int orderId) throws OrderNotFoundException {
        ClientData client = clients.get(clientId);
        ArrayList<Order> clientOrders = client.getOrders();
        for (Order order : clientOrders){
            if(order.getOrderId() == orderId){
                return order;
            }
        }
        throw new OrderNotFoundException(orderId);
    }

    public synchronized void cancelOrder(int clientId, Order toCancel){
        try{
            Order orderToCancel = findOrderFromOrderId(clientId, toCancel.getOrderId());
            orderToCancel.setStatus(OrderStatus.CANCELLED);
            orderbooks.get(orderToCancel.getStock()).cancelOrderIfStillPresent(orderToCancel);
            System.out.println("ORDINE CANCELLATO");
        }catch(OrderNotFoundException ignored){}
    }

    private void setPrice(Stock stock, int price) {
        prices.put(stock,price);
    }
}
