package client;

import client.gui.ClientView;
import client.gui.OrderItem;
import client.gui.PriceItem;
import client.gui.SalePanel;
import exceptions.StockNotFoundException;
import server.ClientData;
import trading.Order;
import trading.OrderSide;
import trading.Stock;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class ClientController implements ActionListener, MouseListener, WindowListener {
    private ClientModel model;
    private ClientView view;

    public ClientController() {
        this.model = new ClientModel(this);
        this.view = new ClientView(this);
    }

    public ClientModel getModel() {
        return model;
    }

    public ClientView getView() {
        return view;
    }

    public void updateWallet(ClientData data, TreeMap<Stock,Integer> prices){
        ArrayList<String> names = getStockNames(prices);

        ArrayList<Integer> quantities = new ArrayList<>(
                data.getWallet().values()
        );

        //Wallet value in dollars for each stock
        ArrayList<Double> values = new ArrayList<>();
        for (Stock stock : prices.keySet()){
            int price = prices.get(stock);
            values.add((data.getWallet().get(stock) * price) / 100.0);
        }

        view.setStocksInWallet(names,quantities,values);
        view.setCash(data.getCash());
    }

    public void updateOrders(ClientData data){
    }

    public void updatePrices(TreeMap<Stock,ArrayList<Integer>> prices){
        ArrayList<String> names = getStockNames(prices);

        ArrayList<Double> values = new ArrayList<>(
                prices.values()
                        .stream()
                        .map((list) -> list.get(list.size()-1))
                        .map((value) -> value/100.0)
                        .toList()
        );

        ArrayList<Boolean> risingStatusList = new ArrayList<>(
                prices.values()
                        .stream()
                        .map((list) -> list.get(0) <= list.get(list.size()-1))
                        .toList()
        );

        view.setStockPrices(names, values, risingStatusList);
    }

    private ArrayList<String> getStockNames(TreeMap<Stock,?> prices){
        ArrayList<String> names = new ArrayList<>(
                prices.keySet()
                        .stream()
                        .map(Stock::toString)
                        .toList()
        );

        return names;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        SalePanel sale = view.getSalePanel();
        JButton purchaseButton = sale.getPurchaseButton();
        if(actionEvent.getSource() == purchaseButton){
            try {
                OrderSide side = sale.isSetToBuy() ? OrderSide.BID : OrderSide.ASK;
                Stock stock = Stock.find(sale.getSelectedStock());
                int quantity = sale.getQuantity();
                int price = sale.getPrice();
                if(quantity != 0 && price != 0){
                    model.sendOrder(side,stock,quantity,price);
                }
            } catch (StockNotFoundException ignored) {}
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() instanceof JPanel clickedItem){
            handleItemClick(clickedItem);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    public void handleItemClick(JPanel clickedItem){
        if(clickedItem instanceof PriceItem priceItem){
            String stockName = priceItem.getName();
            try{
                Stock stock = Stock.find(stockName);
                ArrayList<Integer> pricesInCents = model.getPrices(stock);
                ArrayList<Double> prices = new ArrayList<>(pricesInCents.stream().map((v) -> v/100.0).toList());
                view.setGraphPrices(prices);
            }catch(Exception ignored){}
        }else if(clickedItem instanceof OrderItem orderItem){
            int orderId = orderItem.getOrderId();
            try{
                Order order = model.getOrderFromId(orderId);
                updateOrderStatus(order);
            }catch(Exception ignored){}
        }
    }

    private void updateOrderStatus(Order order) {
        view.setOrderStatus(
                order.getOrderId(),
                (order.getSide() == OrderSide.BID ? "BUY" : "SELL"),
                order.getStock().toString(),
                order.getQuantity(),
                order.getPrice(),
                model.getStatus(order).toString()
        );
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        model.stopClient();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
