package trading;

import exceptions.StockNotFoundException;

import java.io.Serializable;

public enum Stock implements Serializable {
    TEXLA,
    GOOGOL,
    MACROSOFT,
    FAKEBOOK,
    SPACEY;

    public static Stock find(String stockName) throws StockNotFoundException {
        for(Stock stock : values()){
            if(stock.toString().equalsIgnoreCase(stockName)){
                return stock;
            }
        }
        throw new StockNotFoundException(stockName);
    }

    public static Stock findByOrdinal(int ordinal) throws StockNotFoundException {
        if(ordinal > values().length){
            throw new StockNotFoundException("Ordinal too high");
        } else if (ordinal < 0) {
            throw new StockNotFoundException("Negative ordinal");
        }

        for(Stock stock : values()){
            if(stock.ordinal() == ordinal){
                return stock;
            }
        }

        throw new StockNotFoundException("Stock not found by ordinal");
    }
}
