package client;

import trading.Order;
import trading.Stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientModel {
    private ClientController controller;
    private HashMap<Stock,Integer> wallet;
    private HashMap<Stock,List<Integer>> prices;
    private List<Order> orders;
    public ClientModel(ClientController controller) {
        this.controller = controller;
        this.wallet = new HashMap<>();
        this.prices = new HashMap<>();
        this.orders = new ArrayList<>();
    }
}
