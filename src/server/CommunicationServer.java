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
    private final ExecutorService clientsThreadManager;
    private final ScheduledExecutorService dataSender;
    private static final int DELAY = 1;

    public CommunicationServer(DataManager manager){
        this.manager = manager;
        this.handlers = new ArrayList<>();
        this.clientsThreadManager = Executors.newFixedThreadPool(10);
        this.dataSender = Executors.newSingleThreadScheduledExecutor();
    }
    public void start() {
        int clientId = 0;

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
            System.out.println("SERVER ERROR");
        } finally {
            clientsThreadManager.shutdown();
        }
    }

    private void sendDataToAllClients(){
        handlers.removeIf(
                (handler) -> {
                    try{
                        handler.sendData();
                        return false;
                    }catch(IOException e){
                        System.out.println("DELETING CLIENT HANDLER");
                        return true;
                    }
                }
        );
    }

    public static void main(String[] args) {
        CommunicationServer server = new CommunicationServer(new DataManager());
        server.start();
    }
}
