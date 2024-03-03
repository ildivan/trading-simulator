package trading;

import java.io.Serializable;

public class OrderRejection implements Serializable {
    private final Order rejectedOrder;
    public OrderRejection(Order order){
        rejectedOrder = order;
    }

    public Order getOrder(){
        return rejectedOrder;
    }
}
