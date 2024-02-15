package trading;

import exceptions.EmptyLimitException;

import java.util.TreeMap;

public class TradingEngine {
    private TreeMap<Integer,Limit> bids;
    private TreeMap<Integer,Limit> asks;

    public TradingEngine(){
        bids = new TreeMap<>((a , b) -> -Integer.compare(a,b)); //bid with the highest price is first
        asks = new TreeMap<>(Integer::compare); //ask with the lowest price is first
    }

    public void insertOrder(Order order){
        if(order.getSide() == OrderSide.BID){
            putOrderInMap(order,bids);
        }else{ // order.getSide() == OrderSide.ASK
            putOrderInMap(order,asks);
        }
    }

    public void match() {
        while(!asks.isEmpty() && !bids.isEmpty()){
            Limit bestBuyLimit = bids.firstEntry().getValue();
            Limit bestSellLimit = asks.firstEntry().getValue();
            try {
                if(bestBuyLimit.getPriceLevel() < bestSellLimit.getPriceLevel()){
                    return;
                }
                matchOrder(bestBuyLimit,bestSellLimit);
            } catch (EmptyLimitException e) {
                if(bestBuyLimit.isEmpty()){
                    bids.remove(bestBuyLimit.getPriceLevel());
                }
                if(bestSellLimit.isEmpty()){
                    asks.remove(bestSellLimit.getPriceLevel());
                }
            }
        }
    }

    private void matchOrder(Limit bestBuyLimit, Limit bestSellLimit) throws EmptyLimitException {
        Order buyOrder = bestBuyLimit.getCurrentOrder();
        Order sellOrder = bestSellLimit.getCurrentOrder();

        if (sellOrder.getQuantity() > buyOrder.getQuantity()) {
            sellOrder.decreaseQuantity(buyOrder.getQuantity());
            sendTrade();
            bestBuyLimit.deleteCurrentOrder();
        } else if (sellOrder.getQuantity() == buyOrder.getQuantity()) {
            sendTrade();
            bestSellLimit.deleteCurrentOrder();
            bestBuyLimit.deleteCurrentOrder();
        } else {
            buyOrder.decreaseQuantity(sellOrder.getQuantity());
            sendTrade();
            bestSellLimit.deleteCurrentOrder();
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
