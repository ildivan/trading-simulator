package client;

import client.gui.ClientView;

public class ClientApp {
    public static void main(String[] args) {
         ClientController controller = new ClientController();
         ClientView view = controller.getView();
         ClientModel model = controller.getModel();
         Thread connectionThread = new Thread(model::connect);
         connectionThread.start();
    }
}