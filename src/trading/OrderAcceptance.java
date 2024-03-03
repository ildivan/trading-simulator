package trading;

import java.io.Serializable;

public class OrderAcceptance implements Serializable {
    private final Order acceptedOrder;
    public OrderAcceptance(Order order){
        acceptedOrder = order;
    }

    public Order getOrder(){
        return acceptedOrder;
    }
}
