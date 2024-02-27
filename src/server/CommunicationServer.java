package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommunicationServer {

    private final DataManager manager;
    private final ArrayList<ClientHandler> handlers;
    private static final int DELAY = 5;

    public CommunicationServer(DataManager manager){
        this.manager = manager;
        handlers = new ArrayList<>();
    }
    public void start() {
        int clientId = 0;
        ExecutorService clientsThreadManager = Executors.newFixedThreadPool(10);
        ScheduledExecutorService dataSender = Executors.newSingleThreadScheduledExecutor();
        // Schedule sending data to the client every 5 seconds
        dataSender.scheduleAtFixedRate(this::sendDataToAllClients, 0, DELAY, TimeUnit.SECONDS);

        try(ServerSocket serverSocket = new ServerSocket(12345)){
            System.out.println("Server in attesa di connessioni...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connessione accettata.");
                clientId += 1;
                ClientHandler clientHandler = new ClientHandler(clientId,socket, manager);
                handlers.add(clientHandler);
                clientsThreadManager.execute(clientHandler);
            }
        } catch (IOException e) {
            System.out.printf("SERVER ERROR: %s\n",e.getMessage());
        } finally {
            clientsThreadManager.shutdown();
        }
    }

    private void sendDataToAllClients(){
        for (ClientHandler handler : handlers){
            try{
                handler.sendData();
            }catch(IOException e){
                System.out.printf("ERROR SENDING DATA TO CLIENT: %s",e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        CommunicationServer server = new CommunicationServer(new DataManager());
        server.start();
    }
}
