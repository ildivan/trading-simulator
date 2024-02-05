package exceptions;

public class EmptyLimitException extends Exception{
    public EmptyLimitException(int priceLevel) {
        super(String.format("No orders are present on limit with price %d",priceLevel));
    }
}
