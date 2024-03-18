package client;

public class ClientApp {
    public static void main(String[] args) {
         ClientController controller = new ClientController();
         ClientModel model = controller.getModel();
         model.connect();
    }
}