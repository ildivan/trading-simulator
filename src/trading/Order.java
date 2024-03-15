package trading;

import java.io.Serializable;

public class Order implements Serializable {
    private int orderId;
    private OrderSide side;
    private Stock stock;
    private int quantity;
    private int price;
    private OrderStatus status;

    public Order(int orderId, OrderSide side,  Stock stock, int quantity, int price) {
        this.orderId = orderId;
        this.side = side;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        status = OrderStatus.PENDING;
    }

    public Order(OrderSide side,  Stock stock, int quantity, int price) {
        this(0,side,stock,quantity,price);
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
    }

    public OrderSide getSide() {
        return side;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }
    public void decreaseQuantity(int quantityToDecrease){
        if(quantityToDecrease > quantity){
            quantity = 0;
        }
        quantity -= quantityToDecrease;
    }

    public int getPrice() {
        return price;
    }

    public OrderStatus getStatus(){
        return status;
    }

    public void setStatus(OrderStatus status){
        this.status = status;
    }

    public boolean isCompleted(){
        return quantity == 0;
    }

}
