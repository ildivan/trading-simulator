package gui;

import java.awt.*;
import java.util.ArrayList;

public class GraphSection extends Section{
    public GraphSection(String title, ArrayList<Double> prices) {
        super(title);
        setGraph(prices);
    }

    public GraphSection(String title) {
        this(title,new ArrayList<>());
    }

    public void setGraph(ArrayList<Double> prices){
        if(prices.size() < 2) {
            return;
        }
        remove(content);
        content = new GraphPanel(prices, 20,20);
        add(content, BorderLayout.CENTER);
    }
}
