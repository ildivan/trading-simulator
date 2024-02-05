package exceptions;

public class WrongLimitException extends RuntimeException{
    public WrongLimitException(int wrongPrice, int correctPrice) {
        super(String.format("Tried to add order with price %d to limit with price level %d", wrongPrice,correctPrice));
    }
}
