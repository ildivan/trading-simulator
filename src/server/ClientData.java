package server;

import trading.Order;
import trading.Stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientData implements Serializable {
    private Integer cash;
    private HashMap<Stock,Integer> wallet;
    private ArrayList<Order> orders;

    public ClientData(){
        cash = 0;
        wallet = new HashMap<>();
        orders = new ArrayList<>();
    }

    public ClientData(int cash, HashMap<Stock,Integer> wallet, ArrayList<Order> orders){
        this.wallet = wallet;
        this.orders = orders;
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
