package client;

import server.ClientData;
import trading.OrderAcceptance;
import trading.OrderRejection;
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
    private AtomicBoolean isRunning;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

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

    public void stopClient() {
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

    private void handleReceive() {
        try{
            Object received;
            synchronized (this){
                received = in.readObject();
            }
            if(received  instanceof OrderAcceptance acceptance){

            }else if(received instanceof OrderRejection rejection){

            }else if(received instanceof ClientData receivedData){
                data = receivedData;
            }else if(received instanceof TreeMap<?,?> receivedPrices){
                @SuppressWarnings("unchecked")
                TreeMap<Stock, Integer> castedPrices = (TreeMap<Stock, Integer>) receivedPrices;
                updatePrices(castedPrices);
            }
        }catch(IOException | ClassNotFoundException ignored){}
    }

    private void updatePrices(TreeMap<Stock,Integer> receivedPrices){
        ArrayList<String> names = new ArrayList<>(
                receivedPrices.keySet()
                        .stream()
                        .map(Stock::toString)
                        .toList()
        );

        ArrayList<Double> values = new ArrayList<>(
                receivedPrices.values()
                        .stream()
                        .map((value) -> value/100.0)
                        .toList()
        );

        for(Stock stock : receivedPrices.keySet()){
            if(prices.containsKey(stock) && prices.get(stock) != null){
                ArrayList<Integer> stockPrices = prices.get(stock);
                stockPrices.add(receivedPrices.get(stock));
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

        ArrayList<Boolean> risingStatusList = new ArrayList<>();
        for(Stock stock : prices.keySet()){
            boolean isRising = false;
            ArrayList<Integer> stockPrices = prices.get(stock);
            if(stockPrices.get(0) <= stockPrices.get(stockPrices.size()-1)){
                isRising = true;
            }
            risingStatusList.add(isRising);
        }

        controller.updatePrices(names,values,risingStatusList);
    }
}
