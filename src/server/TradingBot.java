package server;

import exceptions.OrderNotFoundException;
import exceptions.StockNotFoundException;
import trading.Order;
import trading.OrderSide;
import trading.Stock;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class TradingBot {
    private ClientData data;
    private TreeMap<Stock,ArrayList<Integer>> prices;
    private AtomicBoolean isRunning;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Random rand;
    private static final int MAX_PRICES_STORED = 15;

    public TradingBot() {
        this.data = new ClientData();
        this.prices = new TreeMap<>();
        isRunning = new AtomicBoolean(true);
        rand = new Random();
    }

    public void connect(){
        try {
            socket = new Socket("localhost", 12345);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Thread receiveThread = new Thread(() -> {
                while(getIsRunning()){
                    handleReceive();
                }
            });
            receiveThread.start();

            ScheduledExecutorService tradingThread = Executors.newSingleThreadScheduledExecutor();
            tradingThread.scheduleAtFixedRate(this::trade,0, 2, TimeUnit.SECONDS);

        } catch (IOException e) {
            System.out.printf("CLIENT ERROR: %s",e.getMessage());
        }
    }

    private boolean getIsRunning() {
        return isRunning.get();
    }

    public synchronized void stopClient() {
        isRunning.set(false);

        try {
            if (out != null) {
                out.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.printf("ERROR IN STOPPING CLIENT: %s",e.getMessage());
        }
    }

    public synchronized void sendOrder(OrderSide side, Stock stock, int quantity, int price){
        Order orderToSend = new Order(side,stock,quantity,price);
        try{
            out.writeObject(orderToSend);
            out.flush();
        }catch(IOException e){
            System.out.println("PROBLEM WITH SERVER CONNECTION");
        }
    }

    private void handleReceive() {
        try{
            Object received = in.readObject();
            if(received instanceof ClientData receivedData){
                data = receivedData;
            }else if(received instanceof TreeMap<?,?> receivedPrices){
                @SuppressWarnings("unchecked")
                TreeMap<Stock, Integer> prices = (TreeMap<Stock, Integer>) receivedPrices;
                updatePrices(prices);
            }
        }catch(IOException | ClassNotFoundException ignored){}
    }


    private void updatePrices(TreeMap<Stock,Integer> receivedPrices){
        for(Stock stock : receivedPrices.keySet()){
            if(prices.containsKey(stock) && prices.get(stock) != null){
                ArrayList<Integer> stockPrices = prices.get(stock);
                if(!receivedPrices.get(stock).equals(stockPrices.get(stockPrices.size() - 1))){
                    stockPrices.add(receivedPrices.get(stock));
                }
                if(stockPrices.size() > MAX_PRICES_STORED){
                    stockPrices.remove(0);
                }
            }else{
                ArrayList<Integer> newList = new ArrayList<>();
                newList.add(receivedPrices.get(stock));
                prices.put(stock,newList);
            }
        }

        for(Stock stock : prices.keySet()){
            if(!receivedPrices.containsKey(stock)){
                prices.remove(stock);
            }
        }
    }

    public Order getOrderFromId(int orderId) throws OrderNotFoundException {
        for(Order order : data.getOrders()){
            if(order.getOrderId() == orderId){
                return order;
            }
        }
        throw new OrderNotFoundException(orderId);
    }

    private void trade(){
        try{
            for(Stock stock : Stock.values()){
                if(data.getWallet().get(stock) > 0){
                    int currentPrice = getLastPrice(stock);
                    int price = currentPrice + rand.nextInt(currentPrice / 50)*(rand.nextBoolean() ? 1 : -1);
                    Order order = new Order(OrderSide.ASK,stock,data.getWallet().get(stock),price);
                    out.writeObject(order);
                    out.flush();
                }
            }

            if(data.getCash() > 0){
                int firstOrdinal = rand.nextInt(Stock.values().length);
                int secondOrdinal = rand.nextInt(Stock.values().length);
                try{
                    Stock firstStock = Stock.findByOrdinal(firstOrdinal);
                    Stock secondStock = Stock.findByOrdinal(secondOrdinal);
                    int cash = data.getCash();
                    while(true){
                        int firstStockPrice = getLastPrice(firstStock);
                        int secondStockPrice = getLastPrice(secondStock);

                        if(cash > firstStockPrice){
                            sendOrder(firstStock,OrderSide.BID,1);
                            cash -= firstStockPrice;
                        }
                        if(cash > secondStockPrice){
                            sendOrder(secondStock,OrderSide.BID,1);
                            cash -= secondStockPrice;
                        }

                        if(cash < firstStockPrice && cash < secondStockPrice){
                            break;
                        }
                    }
                }catch(StockNotFoundException ignored){}


            }
        }catch(IOException e){
            stopClient();
        }
    }

    private int getLastPrice(Stock stock){
        return prices.get(stock).get(prices.get(stock).size()-1);
    }

    private void sendOrder(Stock stock, OrderSide side, int quantity) throws IOException {
        int currentPrice = getLastPrice(stock);
        int price = currentPrice + rand.nextInt(currentPrice / 50)*(rand.nextBoolean() ? 1 : -1);
        Order order = new Order(side,stock,quantity,price);
        out.writeObject(order);
        out.flush();
    }

    public static void main(String[] args) {
        ArrayList<TradingBot> bots = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            bots.add(new TradingBot());
            bots.get(i).connect();
        }
    }
}

