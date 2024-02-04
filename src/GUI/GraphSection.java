package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphSection extends Section{
    public GraphSection(String title, ArrayList<Double> prices) {
        super(title);
        remove(content);
        content = new GraphPanel(prices, 20, 5);
        add(content, BorderLayout.CENTER);
    }

    public void addPriceToGraph(double newPrice){
        if(content instanceof GraphPanel graph){
            graph.addNewValue(newPrice);
        }
    }
}
