package server;

import trading.Order;
import trading.Stock;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TreeMap;

public class ClientHandler implements Runnable{
    private final int clientId;
    private final Socket clientSocket;
    private final DataManager manager;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(int clientId, Socket socket, DataManager manager) {
        this.clientId = clientId;
        this.clientSocket = socket;
        this.manager = manager;
    }

    @Override
    public void run(){
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream((clientSocket.getOutputStream()));

            while(true){
                Object receivedObject = in.readObject();

                if(receivedObject instanceof Order receivedOrder){
                    manager.processOrder(clientId,receivedOrder);
                }
            }
        } catch (IOException e) {
            manager.removeClient(clientId);
        } catch(ClassNotFoundException e){
            System.out.printf("CLIENT HANDLER ERROR: %s\n",e.getMessage());
        }
    }

    public void sendData() throws IOException{
        ClientData clientData = manager.getClientData(clientId);
        if(clientData != null){
            synchronized (this){
                out.writeObject(new ClientData(clientData));
                out.flush();
            }
        }
        TreeMap<Stock,Integer> prices = manager.getPrices();
        synchronized (this){
            out.writeObject(new TreeMap<>(prices));
            out.flush();
        }
    }
}
