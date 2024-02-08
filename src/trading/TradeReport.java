package trading;

public record TradeReport(int  buyerOrderId, int sellerOrderId, int quantity, int price) {
    public TradeReport(Order buyer, Order seller, int quantity, int price){
        this(buyer.getOrderId(), seller.getOrderId(), quantity, price);
    }
}
