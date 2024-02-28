package server;

import trading.Order;
import trading.OrderSide;
import trading.Stock;

import java.util.HashMap;
import java.util.TreeMap;

public class DataManager {
    private volatile HashMap<Integer,ClientData> clients;
    private volatile TreeMap<Stock,Integer> prices;

    public DataManager() {
        clients = new HashMap<>();
        prices = new TreeMap<>();
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

    public synchronized boolean isOrderValid(int clientId, Order order){
        ClientData data = clients.get(clientId);
        if(order.getSide() == OrderSide.BID){
            int totalPrice = order.getQuantity() * prices.get(order.getStock());
            return totalPrice <= data.getCash();
        }else{
            return order.getQuantity() <= data.getWallet().get(order.getStock());
        }
    }

    public synchronized void processOrder(int clientId, Order order){

    }
}
