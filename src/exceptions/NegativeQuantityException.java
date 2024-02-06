package exceptions;

public class NegativeQuantityException extends RuntimeException{
    public NegativeQuantityException(int quantity,int decrease){
        super(String.format("Tried subtracting %d from an order with %d quantity",decrease,quantity));
    }
}
