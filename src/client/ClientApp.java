package client;

import client.gui.ClientView;

public class ClientApp {
    public static void main(String[] args) {
         ClientController controller = new ClientController();
         ClientView view = controller.getView();
         view.addStockPrice("BUBU",500,true);
         ClientModel model = controller.getModel();
         Thread connectionThread = new Thread(model::connect);
         connectionThread.start();
    }
}