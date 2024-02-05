package trading;

import exceptions.EmptyLimitException;
import exceptions.WrongLimitException;

import java.util.ArrayDeque;
import java.util.Queue;

public class Limit {
    private ArrayDeque<Order> orders;
    private int priceLevel;

    public Limit(int priceLevel){
        this.priceLevel = priceLevel;
        orders = new ArrayDeque<>();
    }

    public int getPriceLevel(){
        return priceLevel;
    }

    public void insertOrder(Order toInsert){
        if(toInsert.getPrice() != priceLevel){
            throw new WrongLimitException(toInsert.getPrice(),priceLevel);
        }
        orders.add(toInsert);
    }

    public Order getNextOrder() throws EmptyLimitException{
        if(orders.isEmpty()){
            throw new EmptyLimitException(priceLevel);
        }
        return orders.peek();
    }

}
