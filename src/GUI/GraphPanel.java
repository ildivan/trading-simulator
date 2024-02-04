package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class GraphPanel extends JPanel {
    private ArrayList<Double> values;
    private ArrayList<Double> yValues;
    private ArrayList<Integer> xValues;
    private double max;
    private double min;
    private int verticalMargin;
    private int horizontalMargin;
    private int maxNumberOfValues;

    public GraphPanel(ArrayList<Double> values, int verticalMargin) {
        this.values = values;
        this.verticalMargin = verticalMargin;
        this.max = Collections.max(this.values);
        this.min = Collections.min(this.values);
    }

    private void setYValues() {
        int height = this.getHeight() - 2* verticalMargin;
        yValues = new ArrayList<>();
        for (Double value : values) {
            yValues.add(adjustY(value, height));
        }

    }

    private Double adjustY(Double value, int height) {
        return height - height*((value - min)/(max - min)) + verticalMargin;
    }

    private void setXValues(){
        int width = getWidth() - 2* horizontalMargin;
        xValues = new ArrayList<>();
        xValues.add(horizontalMargin);
        int increment = (int)Math.floor(((double)1/(double)(yValues.size()-1))*width);
        for (int i = 0; i < yValues.size()-1; i++) {
            xValues.add(
                    xValues.get(i) + increment
            );
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(ClientView.CONTENT_FONT);
        horizontalMargin = calculateHorizontalMargin(g2D);
        setYValues();
        setXValues();
        drawBackGround(g2D);
        drawGraph(g2D);
        drawPriceLines(g2D);
        drawPrices(g2D);
    }

    private int calculateHorizontalMargin(Graphics2D g2D){
        FontMetrics fontMetrics = g2D.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(formatToPrintOnScreen(max));

        return stringWidth + 10;
    }

    private void drawBackGround(Graphics2D g2D) {
        g2D.setPaint(ClientView.CONTENT_BACKGROUND_COLOR);
        g2D.fillRect(0,0,getWidth(),getHeight());
    }

    private void drawGraph(Graphics2D g2D) {
        g2D.setPaint((values.get(0) <= values.get(values.size()-1) ? Color.GREEN : Color.RED));
        g2D.setStroke(new BasicStroke(2));
        for (int i = 0; i < xValues.size()-1; i++) {
            g2D.drawLine(xValues.get(i),(int)Math.floor(yValues.get(i)),xValues.get(i+1),(int)Math.floor(yValues.get(i+1)));
        }
    }

    private void drawPriceLines(Graphics2D g2D){
        g2D.setStroke(new BasicStroke(1));
        g2D.setPaint(Color.WHITE);
        g2D.drawLine(horizontalMargin, verticalMargin,getWidth()- horizontalMargin, verticalMargin);
        g2D.drawLine(horizontalMargin,getHeight()- verticalMargin,getWidth()- horizontalMargin,getHeight()- verticalMargin);
        if(yValues.get(yValues.size()-1) != min && yValues.get(yValues.size()-1) != max){
            g2D.drawLine(horizontalMargin,(int)Math.floor(yValues.get(yValues.size()-1)),
                    getWidth()- horizontalMargin,(int)Math.floor(yValues.get(yValues.size()-1)));
        }
    }

    private void drawPrices(Graphics2D g2D){
        double lastPrice = values.get(values.size()-1);
        int lastPriceConverted = (int)(Math.floor(yValues.get(yValues.size()-1)));

        String maxValueString = formatToPrintOnScreen(max);
        String minValueString = formatToPrintOnScreen(min);
        String lastValueString = formatToPrintOnScreen(lastPrice);

        g2D.drawString(maxValueString,5,verticalMargin + ClientView.CONTENT_FONT.getSize()/2);
        g2D.drawString(minValueString,5,getHeight() - verticalMargin + ClientView.CONTENT_FONT.getSize()/2);

        if(lastPrice != min && lastPrice != max){
            g2D.drawString(lastValueString,5,lastPriceConverted + ClientView.CONTENT_FONT.getSize()/2);
        }
    }

    private static String formatToPrintOnScreen(double toFormat) {
        return String.format("%.2f$", toFormat);
    }
}
