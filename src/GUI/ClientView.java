package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ClientView extends JFrame {
    public static final Color HEADING_BACKGROUND_COLOR = new Color(0x555557);
    public static final Color CONTENT_BACKGROUND_COLOR = Color.DARK_GRAY;
    public static final Color FONT_COLOR = Color.WHITE;
    public static final Font HEADING_FONT = new Font("Times New Roman",Font.PLAIN,24);
    public static final Font CONTENT_FONT = new Font("Times New Roman",Font.PLAIN,16);
    public static final Color SELECTED_COLOR = new Color(0x464444);
    private CustomTabbedPane tabs;
    private SectionWithList<WalletItem> wallet;
    private SectionWithList<OrderItem> orders;
    private SectionWithList<PriceItem> prices;
    private GraphSection graph;
    private SalePanel sale;

    static class CustomTabbedPane extends JTabbedPane {
        public CustomTabbedPane(int tabPlacement){
            super(tabPlacement);
        }
        @Override
        protected void paintComponent(Graphics g) {
            // Set the background color behind all tabs
            g.setColor(new Color(0x23262D));
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);

        }
    }

    public ClientView(){
        super("Client View");
        setSize(new Dimension(1000,1000));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupTabs();
        setProfileTab();
        setMarketTab();
        setVisible(true);
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
        wallet.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, ClientView.HEADING_BACKGROUND_COLOR));

        JPanel ordersAndStatus = new JPanel();
        ordersAndStatus.setLayout(new GridLayout(2,1,0,0));
        orders = new SectionWithList<>("ORDERS");
        Section orderStatus = new Section("ORDER INFORMATION");
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
        graph = new GraphSection("GRAPH");

        saleAndGraph.add(graph);
        saleAndGraph.add(sale);

        marketPanel.add(prices);
        marketPanel.add(saleAndGraph);
        tabs.add("  MARKET  ",marketPanel);
    }

    public void addStockToWallet(String name, int quantity, double value){
        wallet.addElementToList(new WalletItem(name,quantity,value));
    }

    public void addStockPrice(String name, double price, boolean isRising){
        prices.addElementToList(new PriceItem(name,price,isRising));
        Object[] stocks = prices.getListOfElements().stream().map(PriceItem::getName).toArray();
        String[] stocksCopy = Arrays.copyOf(stocks, stocks.length, String[].class);
        sale.setSelectionStocks(stocksCopy);
    }

    public void setGraphPrices(ArrayList<Double> prices){
        graph.setGraph(prices);
    }

}
