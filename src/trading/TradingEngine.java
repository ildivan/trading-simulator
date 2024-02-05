package trading;

import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

public class TradingEngine {
    private TreeSet<Limit> bids;
    private TreeSet<Limit> asks;

    public TradingEngine(){
        bids = new TreeSet<>(new BidsComparator());
        asks = new TreeSet<>(new AsksComparator());
    }
}
