package trading;

import java.util.Comparator;

public class BidsComparator implements Comparator<Limit> {

    @Override
    public int compare(Limit l1, Limit l2) {
        return -Integer.compare(l1.getPriceLevel(), l2.getPriceLevel());
    }
}
