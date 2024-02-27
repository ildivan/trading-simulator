package server;

import trading.Order;

public class OrderAcceptance {
    private final Order acceptedOrder;
    public OrderAcceptance(Order order){
        acceptedOrder = order;
    }

    public Order getOrder(){
        return acceptedOrder;
    }
}
