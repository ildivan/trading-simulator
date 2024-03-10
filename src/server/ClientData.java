package server;

import trading.Order;
import trading.Stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientData implements Serializable {
    private int cash;
    private HashMap<Stock,Integer> wallet;
    private ArrayList<Order> orders;

    public ClientData(){
        cash = 0;
        wallet = new HashMap<>();
        orders = new ArrayList<>();
    }

    public ClientData(ClientData toCopy){
        cash = toCopy.getCash();
        wallet = new HashMap<>(toCopy.getWallet());
        orders = new ArrayList<>(toCopy.getOrders());
    }

    public int getCash(){
        return cash;
    }
    public void setCash(int cash) {
        this.cash = cash;
    }

    public HashMap<Stock, Integer> getWallet() {
        return wallet;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
