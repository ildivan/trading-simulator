package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommunicationServer {

    private final DataManager manager;

    public CommunicationServer(DataManager manager){
        this.manager = manager;
    }
    public void start() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try(ServerSocket serverSocket = new ServerSocket(12345)){
            System.out.println("Server in attesa di connessioni...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connessione accettata.");

                Runnable clientHandler = new ClientHandler(1,socket, manager);
                executorService.execute(clientHandler);
            }
        } catch (IOException e) {
            System.out.printf("SERVER ERROR: %s\n",e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }

    public static void main(String[] args) {
        CommunicationServer server = new CommunicationServer(new DataManager());
        server.start();
    }
}
