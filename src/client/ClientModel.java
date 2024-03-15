package client;

import exceptions.OrderNotFoundException;
import server.ClientData;
import trading.Order;
import trading.OrderSide;
import trading.Stock;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientModel {
    private ClientController controller;
    private ClientData data;
    private TreeMap<Stock,ArrayList<Integer>> prices;
    private Stock selectedStock;
    private AtomicBoolean isRunning;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private static final int MAX_PRICES_STORED = 15;

    public ClientModel(ClientController controller) {
        this.controller = controller;
        this.data = new ClientData();
        this.prices = new TreeMap<>();
        isRunning = new AtomicBoolean(true);
    }

    public void connect(){
        try {
            socket = new Socket("localhost", 12345);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Thread receiveThread = new Thread(() -> {
                while(getIsRunning()){
                    handleReceive();
                }
            });

            receiveThread.start();

        } catch (IOException e) {
            System.out.printf("CLIENT ERROR: %s",e.getMessage());
        }
    }

    private boolean getIsRunning() {
        return isRunning.get();
    }

    public synchronized void stopClient() {
        isRunning.set(false);

        try {
            if (out != null) {
                out.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.printf("ERROR IN STOPPING CLIENT: %s",e.getMessage());
        }
    }

    public synchronized void sendOrder(OrderSide side, Stock stock, int quantity, int price){
        Order orderToSend = new Order(side,stock,quantity,price);
        try{
            out.writeObject(orderToSend);
            out.flush();
        }catch(IOException e){
            System.out.println("PROBLEM WITH SERVER CONNECTION");
        }
    }

    private void handleReceive() {
        try{
            Object received = in.readObject();
            if(received instanceof ClientData receivedData){
                data = receivedData;
                try{
                    System.out.println((data.getOrders().get(0).getQuantity()));
                }catch(Exception ignored){}
                updateClientData();
            }else if(received instanceof TreeMap<?,?> receivedPrices){
                @SuppressWarnings("unchecked")
                TreeMap<Stock, Integer> prices = (TreeMap<Stock, Integer>) receivedPrices;
                updatePrices(prices);
            }
        }catch(IOException | ClassNotFoundException ignored){}
    }

    private void updateClientData() {
        TreeMap<Stock,Integer> lastPrices = new TreeMap<>();
        for(Stock stock : prices.keySet()){
            ArrayList<Integer> priceList = prices.get(stock);
            int lastPrice = priceList.get(priceList.size() - 1);
            lastPrices.put(stock,lastPrice);
        }
        controller.updateWallet(data,lastPrices);
        controller.updateOrders(data);

    }

    private void updatePrices(TreeMap<Stock,Integer> receivedPrices){
        for(Stock stock : receivedPrices.keySet()){
            if(prices.containsKey(stock) && prices.get(stock) != null){
                ArrayList<Integer> stockPrices = prices.get(stock);
                if(!receivedPrices.get(stock).equals(stockPrices.get(stockPrices.size() - 1))){
                    stockPrices.add(receivedPrices.get(stock));
                }
                if(stockPrices.size() > MAX_PRICES_STORED){
                    stockPrices.remove(0);
                }
            }else{
                ArrayList<Integer> newList = new ArrayList<>();
                newList.add(receivedPrices.get(stock));
                prices.put(stock,newList);
            }
        }

        for(Stock stock : prices.keySet()){
            if(!receivedPrices.containsKey(stock)){
                prices.remove(stock);
            }
        }

        controller.updatePrices(prices,selectedStock);
    }

    public Order getOrderFromId(int orderId) throws OrderNotFoundException {
        for(Order order : data.getOrders()){
            if(order.getOrderId() == orderId){
                return order;
            }
        }
        throw new OrderNotFoundException(orderId);
    }


    public ArrayList<Integer> getPrices(Stock stock){
        return prices.get(stock);
    }
    public void setSelectedStock(Stock stock){
        selectedStock = stock;
    }
}
