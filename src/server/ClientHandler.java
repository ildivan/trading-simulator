package server;

import trading.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;
    private final DataManager manager;

    public ClientHandler(Socket socket, DataManager manager) {
        this.clientSocket = socket;
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            while(true){
                Object receivedObject = in.readObject();

                if(receivedObject instanceof Order receivedOrder){

                }
            }
        } catch (IOException e) {
            System.out.println("CLIENT CLOSED CONNECTION");
        } catch(ClassNotFoundException e){
            System.out.printf("CLIENT HANDLER ERROR: %s\n",e.getMessage());
        }
    }
}
