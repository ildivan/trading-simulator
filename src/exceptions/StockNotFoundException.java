package exceptions;

public class StockNotFoundException extends Exception{
    private String stockNotFound;
    public StockNotFoundException(String stockNotFound){
        super(String.format("Stock not found: %s",stockNotFound));
        this.stockNotFound = stockNotFound;
    }

    public String getStockNotFound(){
        return stockNotFound;
    }
}
