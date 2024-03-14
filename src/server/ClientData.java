package server;

import trading.Order;
import trading.Stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class ClientData implements Serializable {
    private int cash;
    private TreeMap<Stock,Integer> wallet;
    private ArrayList<Order> orders;

    public ClientData(){
        cash = 0;
        wallet = new TreeMap<>();
        orders = new ArrayList<>();
    }

    public ClientData(ClientData toCopy){
        cash = toCopy.getCash();
        wallet = new TreeMap<>(toCopy.getWallet());
        orders = new ArrayList<>(toCopy.getOrders());
    }

    public int getCash(){
        return cash;
    }
    public void setCash(int cash) {
        this.cash = cash;
    }

    public TreeMap<Stock, Integer> getWallet() {
        return wallet;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
