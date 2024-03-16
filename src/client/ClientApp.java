package client;

public class ClientApp {
    public static void main(String[] args) {
         ClientController controller = new ClientController();
         ClientView view = controller.getView();
         ClientModel model = controller.getModel();
         model.connect();
    }
}