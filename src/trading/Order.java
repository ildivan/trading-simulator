package trading;

import exceptions.NegativeQuantityException;

public class Order {
    private int orderId;
    private OrderSide side;

    private Stock stock;
    private int quantity;
    private int price;

    public Order(int orderId, OrderSide side,  Stock stock, int quantity, int price) {
        this.orderId = orderId;
        this.side = side;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
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
            throw new NegativeQuantityException(quantity,quantityToDecrease);
        }
        quantity -= quantityToDecrease;
    }

    public int getPrice() {
        return price;
    }
}
