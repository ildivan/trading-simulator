package exceptions;

public class ClientNotFoundException extends Exception{
    public ClientNotFoundException(int orderId){
        super(String.format("Client not found for order with id: %d",orderId));
    }
}
