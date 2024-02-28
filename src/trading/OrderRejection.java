package trading;

import trading.Order;

public class OrderRejection {
    private final Order rejectedOrder;
    public OrderRejection(Order order){
        rejectedOrder = order;
    }

    public Order getOrder(){
        return rejectedOrder;
    }
}
