package client;

import server.ClientData;
import server.OrderAcceptance;
import server.OrderRejection;
import trading.Order;
import trading.Stock;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientModel {
    private ClientController controller;
    private HashMap<Stock,Integer> wallet;
    private HashMap<Stock,List<Integer>> prices;
    private List<Order> orders;
    private AtomicBoolean isRunning;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientModel(ClientController controller) {
        this.controller = controller;
        this.wallet = new HashMap<>();
        this.prices = new HashMap<>();
        this.orders = new ArrayList<>();
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
            Object received = in.readObject();
            if(received  instanceof OrderAcceptance acceptance){

            }else if(received instanceof OrderRejection rejection){

            }else if(received instanceof ClientData data){

            }else if(received instanceof HashMap<?,?> receivedPrices){
                @SuppressWarnings("unchecked")
                HashMap<Stock, Integer> castedPrices = (HashMap<Stock, Integer>) receivedPrices;
            }
        }catch(IOException e){

        } catch (ClassNotFoundException e) {

        }
    }
}
