package exceptions;

public class OrderNotFoundException extends Exception{
    public OrderNotFoundException(int orderId) {
        super(Integer.toString(orderId));
    }
}
