package trading;

import java.io.Serializable;

public class OrderCancellation implements Serializable {
    private final Order order;

    public OrderCancellation(Order orderToCancel){
        order = orderToCancel;
    }

    public Order getOrder(){
        return order;
    }
}
