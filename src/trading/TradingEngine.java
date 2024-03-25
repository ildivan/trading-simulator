package trading;

import exceptions.EmptyLimitException;
import server.DataManager;

import java.util.TreeMap;

public class TradingEngine {
    private DataManager manager;
    private TreeMap<Integer,Limit> bids;
    private TreeMap<Integer,Limit> asks;

    public TradingEngine(DataManager manager){
        bids = new TreeMap<>((a , b) -> -Integer.compare(a,b)); //bid with the highest price is first
        asks = new TreeMap<>(Integer::compare); //ask with the lowest price is first
        this.manager = manager;
    }

    public synchronized void insertOrder(Order order){
        if(order.getSide() == OrderSide.BID){
            putOrderInMap(order,bids);
        }else{
            putOrderInMap(order,asks);
        }
    }

    public synchronized void match() {
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
            int tradeQuantity = buyOrder.getQuantity();
            sellOrder.decreaseQuantity(buyOrder.getQuantity());
            buyOrder.decreaseQuantity(buyOrder.getQuantity()); //sets quantity to 0
            sendTrade(buyOrder, sellOrder,tradeQuantity);
            bestBuyLimit.deleteCurrentOrder();
        } else if (sellOrder.getQuantity() == buyOrder.getQuantity()) {
            int tradeQuantity = buyOrder.getQuantity();
            buyOrder.decreaseQuantity(buyOrder.getQuantity()); //sets quantity to 0
            sellOrder.decreaseQuantity(sellOrder.getQuantity()); //sets quantity to 0
            sendTrade(buyOrder, sellOrder, tradeQuantity);
            bestSellLimit.deleteCurrentOrder();
            bestBuyLimit.deleteCurrentOrder();
        } else {
            int tradeQuantity = sellOrder.getQuantity();
            buyOrder.decreaseQuantity(sellOrder.getQuantity());
            sellOrder.decreaseQuantity(sellOrder.getQuantity()); //sets quantity to 0
            sendTrade(buyOrder, sellOrder, tradeQuantity);
            bestSellLimit.deleteCurrentOrder();
        }
    }

    private void sendTrade(Order buyOrder, Order sellOrder, int quantity) {
        manager.submitTrade(
                new TradeReport(buyOrder, sellOrder, quantity,(buyOrder.getPrice() + sellOrder.getPrice())/2)
        );
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

    public synchronized void cancelOrderIfStillPresent(Order orderToCancel) {
        if (orderToCancel.getSide() == OrderSide.BID) {
            cancelOrderFromMap(orderToCancel,bids);
        } else {
            cancelOrderFromMap(orderToCancel,asks);
        }
    }
    private void cancelOrderFromMap(Order orderToCancel, TreeMap<Integer,Limit> map){
        for (Limit limit : map.values()) {
            if (limit.getPriceLevel() == orderToCancel.getPrice()) {
                limit.deleteOrder(orderToCancel.getOrderId());
            }
        }
    }
}
