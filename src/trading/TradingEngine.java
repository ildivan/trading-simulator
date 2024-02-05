package trading;

import java.util.TreeMap;

public class TradingEngine {
    private TreeMap<Integer,Limit> bids;
    private TreeMap<Integer,Limit> asks;

    public TradingEngine(){
        bids = new TreeMap<>((a , b) -> -Integer.compare(a,b));
        asks = new TreeMap<>(Integer::compare);
    }

    public void insertOrder(Order order){
        if(order.getSide() == OrderSide.BID){
            putOrderInMap(order,bids);
        }else{ // order.getSide() == OrderSide.ASK
            putOrderInMap(order,asks);
        }
    }

    private static void putOrderInMap(Order order, TreeMap<Integer,Limit> map){
        if(map.containsKey(order.getPrice())){
            Limit limitLevel = map.get(order.getPrice());
            limitLevel.insertOrder(order);
        }else{
            map.put(order.getPrice(),new Limit(order.getPrice()));
        }
    }
}
