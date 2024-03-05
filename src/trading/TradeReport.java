package trading;

public record TradeReport(int  buyerOrderId, int sellerOrderId, Stock stock ,int quantity, int price) {
    public TradeReport(Order buyer, Order seller, int quantity, int price){
        this(buyer.getOrderId(), seller.getOrderId(), buyer.getStock(),quantity, price);
    }
}
