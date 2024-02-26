package server;

import trading.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final int clientId;
    private final Socket clientSocket;
    private final DataManager manager;

    public ClientHandler(int clientId, Socket socket, DataManager manager) {
        this.clientId = clientId;
        this.clientSocket = socket;
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream((clientSocket.getOutputStream()));
            while(true){
                Object receivedObject = in.readObject();

                if(receivedObject instanceof Order receivedOrder){
                    if(manager.isOrderValid(clientId, receivedOrder)){
                        manager.processOrder(clientId, receivedOrder);
                        //send order acceptance
                    }else{
                        //send order rejection
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("CLIENT CLOSED CONNECTION");
        } catch(ClassNotFoundException e){
            System.out.printf("CLIENT HANDLER ERROR: %s\n",e.getMessage());
        }
    }
}
