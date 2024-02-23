package client;

import trading.Order;
import trading.Stock;

import java.io.IOException;
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
            System.out.println("ENTER");
            //Communication cycle
            while (getIsRunning()) {

            }
            System.out.println("EXIT");
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
}
