package trading;

import exceptions.EmptyLimitException;

import java.util.TreeMap;

public class TradingEngine {
    private TreeMap<Integer,Limit> bids;
    private TreeMap<Integer,Limit> asks;

    public TradingEngine(){
        bids = new TreeMap<>((a , b) -> -Integer.compare(a,b));
        asks = new TreeMap<>(Integer::compare);
    }

    public void insertOrder(Order order){
        if(order.getType() == OrderType.MARKET){
            if(order.getSide() == OrderSide.BID){
                matchMarketOrder(order,asks);
            }else{ // order.getSide() == OrderSide.ASK
                matchMarketOrder(order,bids);
            }
        }
        if(order.getSide() == OrderSide.BID){
            putOrderInMap(order,bids);
        }else{ // order.getSide() == OrderSide.ASK
            putOrderInMap(order,asks);
        }

        match();
    }

    private void match(){

    }

    private void matchMarketOrder(Order order,TreeMap<Integer,Limit> otherSideMap){
        if(otherSideMap.isEmpty()){
            return;
        }

        Limit bestPriceLimit = otherSideMap.firstEntry().getValue();
        try {
            Order currentOrder;
            while (true) {
                currentOrder = bestPriceLimit.getNextOrder();
                if (currentOrder.getQuantity() > order.getQuantity()) {
                    currentOrder.decreaseQuantity(order.getQuantity());
                    sendTrade();
                    break;
                } else if (currentOrder.getQuantity() == order.getQuantity()) {
                    sendTrade();
                    bestPriceLimit.deleteFirstOrder();
                    break;
                } else {
                    order.decreaseQuantity(currentOrder.getQuantity());
                    sendTrade();
                    bestPriceLimit.deleteFirstOrder();
                }
            }
        } catch (EmptyLimitException e) {
            otherSideMap.remove(otherSideMap.firstKey());
            matchMarketOrder(order,otherSideMap);
        }
    }

    //TODO Figure out how to send trade report
    private void sendTrade() {
    }

    private static void putOrderInMap(Order order, TreeMap<Integer,Limit> map){
        if(map.containsKey(order.getPrice())){
            Limit limitLevel = map.get(order.getPrice());
            limitLevel.insertOrder(order);
        }else{
            map.put(order.getPrice(),new Limit(order.getPrice()));
            Limit limitLevel = map.get(order.getPrice());
            limitLevel.insertOrder(order);
        }
    }
}
