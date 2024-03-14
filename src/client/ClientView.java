package client;

import client.gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientView extends JFrame {
    public static final Color HEADING_BACKGROUND_COLOR = new Color(0x555557);
    public static final Color CONTENT_BACKGROUND_COLOR = Color.DARK_GRAY;
    public static final Color FONT_COLOR = Color.WHITE;
    public static final Font HEADING_FONT = new Font("Times New Roman",Font.PLAIN,24);
    public static final Font CONTENT_FONT = new Font("Times New Roman",Font.PLAIN,16);
    public static final Color SELECTED_COLOR = new Color(0x464444);
    public static final int minimumHeight = 640;
    public static final int minimumWidth = 1040;
    private ClientController controller;
    private CustomTabbedPane tabs;
    private SectionWithList<WalletItem> wallet;
    private JLabel cashLabel;
    private SectionWithList<OrderItem> orders;
    private OrderStatusPanel orderStatus;
    private SectionWithList<PriceItem> prices;
    private GraphSection graph;
    private SalePanel sale;

    public ClientView(ClientController controller){
        super("Trading App");
        this.controller = controller;
        setSize(new Dimension(minimumWidth + 100,minimumHeight + 100));
        setMinimumSize(new Dimension(minimumWidth,minimumHeight));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(() -> {
            setupTabs();
            setProfileTab();
            setMarketTab();
        });
        setVisible(true);
        addWindowListener(controller);
    }


    private void setupTabs() {
        UIManager.put("TabbedPane.selected", HEADING_BACKGROUND_COLOR);
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.put("TabbedPane.tabAreaInsets", new Insets(0, 0, 0, 0));
        tabs = new CustomTabbedPane(JTabbedPane.TOP);
        tabs.setBackground(CONTENT_BACKGROUND_COLOR);
        tabs.setForeground(FONT_COLOR);
        tabs.setFont(HEADING_FONT);
        add(tabs);
    }

    private void setProfileTab() {
        JPanel walletAndOrders = new JPanel();
        walletAndOrders.setLayout(new GridLayout(1,2,0,0));
        walletAndOrders.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        wallet = new SectionWithList<>("WALLET");

        JPanel cashPanel = new JPanel();
        cashPanel.setPreferredSize(new Dimension(wallet.getWidth(),70));
        cashPanel.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
        cashPanel.setLayout(new BoxLayout(cashPanel,BoxLayout.X_AXIS));
        cashPanel.add(Box.createGlue());


        cashLabel = new JLabel();
        cashLabel.setFont(HEADING_FONT);
        cashLabel.setForeground(FONT_COLOR);
        setCash(0);

        cashPanel.add(cashLabel);
        cashPanel.add(Box.createGlue());

        wallet.add(cashPanel,BorderLayout.SOUTH);
        wallet.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, ClientView.HEADING_BACKGROUND_COLOR));

        JPanel ordersAndStatus = new JPanel();
        ordersAndStatus.setLayout(new GridLayout(2,1,0,0));
        orders = new SectionWithList<>("ORDERS");
        orderStatus = new OrderStatusPanel();

        ordersAndStatus.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, ClientView.HEADING_BACKGROUND_COLOR));
        ordersAndStatus.add(orders);
        ordersAndStatus.add(orderStatus);

        walletAndOrders.add(wallet);
        walletAndOrders.add(ordersAndStatus);
        tabs.add("  PROFILE  ",walletAndOrders);
    }

    private void setMarketTab(){
        JPanel marketPanel = new JPanel();
        marketPanel.setLayout(new GridLayout(1,2,0,0));

        prices = new SectionWithList<>("PRICES");
        prices.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, ClientView.HEADING_BACKGROUND_COLOR));


        JPanel saleAndGraph = new JPanel();
        saleAndGraph.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, ClientView.HEADING_BACKGROUND_COLOR));
        saleAndGraph.setLayout(new GridLayout(2,1,0,0));

        sale = new SalePanel("BUY & SELL");
        sale.getPurchaseButton().addActionListener(controller);
        graph = new GraphSection("GRAPH");

        saleAndGraph.add(graph);
        saleAndGraph.add(sale);

        marketPanel.add(prices);
        marketPanel.add(saleAndGraph);
        tabs.add("  MARKET  ",marketPanel);
    }

    public void setCash(double cash){
        SwingUtilities.invokeLater(() -> cashLabel.setText(String.format("CASH: %.2f$",cash)));
    }

    public void setStocksInWallet(ArrayList<String> nameList, ArrayList<Integer> quantityList, ArrayList<Double> valueList){
        if(nameList.size() != quantityList.size() || nameList.size() != valueList.size()){
            return;
        }

        wallet.removeAllElements();

        for (int i = 0; i < nameList.size(); i++) {
            addStockToWallet(nameList.get(i),quantityList.get(i),valueList.get(i));
        }

        getContentPane().repaint();
    }

    public void addStockToWallet(String name, int quantity, double value){
        SwingUtilities.invokeLater(() -> wallet.addElementToList(new WalletItem(name,quantity,value)));
    }

    public void setOrders
            (ArrayList<Integer> orderIdList,ArrayList<String> orderSideList,
             ArrayList<String> nameList, ArrayList<Integer> quantityList){
        if(orderSideList.size() != nameList.size() ||
                nameList.size() != quantityList.size()){
            return;
        }
        ArrayList<Integer> orderIds = new ArrayList<>(orders.getListOfElements().stream().map(OrderItem::getOrderId).toList());
        orders.removeIf((order) -> !orderIdList.contains(order.getOrderId()));
        for (int i = nameList.size()-1 ; i >= 0; i--) {
            if(!orderIds.contains(orderIdList.get(i))){
                addOrder(orderIdList.get(i),orderSideList.get(i),nameList.get(i),quantityList.get(i));
            }
        }
        getContentPane().repaint();
    }

    public void addOrder(int orderId, String side, String name, int quantity){
        OrderItem newItem = new OrderItem(orderId, side, name, quantity);
        newItem.addMouseListener(controller);
        SwingUtilities.invokeLater(() -> orders.addElementToList(newItem));
    }

    public void setOrderStatus(int orderId, String orderSide,
                               String stock, int quantity, double price, String status){
        SwingUtilities.invokeLater(() -> orderStatus.setOrder(orderId, orderSide, stock, quantity, price, status));
    }

    public void setStockPrices(ArrayList<String> nameList, ArrayList<Double> priceList, ArrayList<Boolean> risingStatus){
        if(nameList.size() != priceList.size() || nameList.size() != risingStatus.size()){
            return;
        }
        SwingUtilities.invokeLater(() -> sale.setSelectionStocks(nameList));

        if(prices.getListOfElements().isEmpty()){
            for (int i = 0; i < nameList.size(); i++) {
                addStockPrice(nameList.get(i),priceList.get(i),risingStatus.get(i));
            }
        }else{
            for (int i = 0; i < prices.getListOfElements().size(); i++) {
                PriceItem currentItem = prices.getListOfElements().get(i);
                currentItem.setPrice(priceList.get(i));
                currentItem.setRisingStatus(risingStatus.get(i));
            }
        }
        getContentPane().repaint();
    }

    public void addStockPrice(String name, double price, boolean isRising){
        PriceItem newItem = new PriceItem(name,price,isRising);
        newItem.addMouseListener(controller);
        SwingUtilities.invokeLater(() -> prices.addElementToList(newItem));
    }

    public void setGraphPrices(ArrayList<Double> prices){
        SwingUtilities.invokeLater(() -> graph.setGraph(prices));
    }

    public SalePanel getSalePanel(){
        return sale;
    }


}
