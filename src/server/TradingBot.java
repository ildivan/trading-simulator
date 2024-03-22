package server;

import exceptions.StockNotFoundException;
import trading.Order;
import trading.OrderCancellation;
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
    private ScheduledExecutorService tradingThread;
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

            tradingThread = Executors.newSingleThreadScheduledExecutor();
            tradingThread.scheduleAtFixedRate(this::trade,rand.nextInt(20), 20, TimeUnit.SECONDS);

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

    private void trade(){
        try{

            for(Order order : data.getOrders()){
                if(Math.abs(order.getPrice() - getLastPrice(order.getStock())) > getLastPrice(order.getStock())/20){
                    OrderCancellation cancellation = new OrderCancellation(order);
                    out.writeObject(cancellation);
                    out.flush();
                }
            }

            for(Stock stock : Stock.values()){
                if(data.getWallet().get(stock) > 0){
                    sendOrder(stock,OrderSide.ASK,data.getWallet().get(stock));
                }
            }

            if(data.getCash() > 0){
                int firstOrdinal = rand.nextInt(Stock.values().length);
                int secondOrdinal = rand.nextInt(Stock.values().length);
                try{
                    Stock firstStock = Stock.findByOrdinal(firstOrdinal);
                    Stock secondStock = Stock.findByOrdinal(secondOrdinal);
                    int firstQuantity = 0;
                    int secondQuantity = 0;
                    int firstStockPrice = getLastPrice(firstStock);
                    int secondStockPrice = getLastPrice(secondStock);
                    int cash = data.getCash();

                    while(true){
                        if(cash > firstStockPrice){
                            firstQuantity += 1;
                            cash -= firstStockPrice;
                        }
                        if(cash > secondStockPrice){
                            secondQuantity += 1;
                            cash -= secondStockPrice;
                        }

                        if(cash < firstStockPrice && cash < secondStockPrice){
                            break;
                        }
                    }

                    if(firstQuantity != 0){
                        sendOrder(firstStock,OrderSide.BID,firstQuantity);
                    }
                    if(secondQuantity != 0){
                        sendOrder(secondStock,OrderSide.BID,secondQuantity);
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

    private synchronized void sendOrder(Stock stock, OrderSide side, int quantity) throws IOException {
        int currentPrice = getLastPrice(stock);
        int price = currentPrice;
        if(side == OrderSide.BID){
            if(rand.nextBoolean()){
                price += rand.nextInt(currentPrice / 50);
            }else{
                price -= rand.nextInt(currentPrice / 100);
            }
        }else{
            if(rand.nextBoolean()){
                price -= rand.nextInt(currentPrice / 50);
            }else{
                price += rand.nextInt(currentPrice / 100);
            }
        }
        Order order = new Order(side,stock,quantity,price);
        out.writeObject(order);
        out.flush();
    }

    public static void main(String[] args) {
        ArrayList<TradingBot> bots = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            bots.add(new TradingBot());
            bots.get(i).connect();
        }
    }
}

