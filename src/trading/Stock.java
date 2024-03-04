package trading;

import exceptions.StockNotFoundException;

import java.io.Serializable;

public enum Stock implements Serializable {
    TEXLA,
    GOOGOL,
    MACROSOFT;

    public static Stock find(String stockName) throws StockNotFoundException {
        for(Stock stock : values()){
            if(stock.toString().equalsIgnoreCase(stockName)){
                return stock;
            }
        }
        throw new StockNotFoundException(stockName);
    }
}
