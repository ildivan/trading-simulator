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
    private double start;
    private int verticalMargin;
    private int horizontalMargin;
    private int maxNumberOfValues;

    public GraphPanel(ArrayList<Double> values, int verticalMargin, int maxNumberOfValues) {
        this.values = values;
        while(this.values.size() > maxNumberOfValues){
            this.values.remove(0);
        }
        this.verticalMargin = verticalMargin;
        this.max = Collections.max(this.values);
        this.min = Collections.min(this.values);
        this.start = this.values.get(0);
        this.maxNumberOfValues = maxNumberOfValues;
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

    public void addNewValue(double newValue){
        if(values.size() >= maxNumberOfValues){
            values.remove(0);
        }
        values.add(newValue);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setFont(ClientView.CONTENT_FONT);

        String maxValueString = String.format("%.4f$", max);
        String minValueString = String.format("%.4f$", min);
        String lastValueString = String.format("%.4f$",values.get(values.size()-1));
        // Get the FontMetrics to calculate the width of the string
        FontMetrics fontMetrics = g2D.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(maxValueString);

        horizontalMargin = stringWidth + 10;

        setYValues();
        setXValues();

        g2D.setPaint(ClientView.CONTENT_BACKGROUND_COLOR);
        g2D.fillRect(0,0,getWidth(),getHeight());

        g2D.setPaint((values.get(0) <= values.get(values.size()-1) ? Color.GREEN : Color.RED));
        g2D.setStroke(new BasicStroke(2));
        for (int i = 0; i < xValues.size()-1; i++) {
            g2D.drawLine(xValues.get(i),(int)Math.floor(yValues.get(i)),xValues.get(i+1),(int)Math.floor(yValues.get(i+1)));
        }

        g2D.setStroke(new BasicStroke(1));
        g2D.setPaint(Color.WHITE);
        g2D.drawLine(horizontalMargin, verticalMargin,getWidth()- horizontalMargin, verticalMargin);
        g2D.drawLine(horizontalMargin,getHeight()- verticalMargin,getWidth()- horizontalMargin,getHeight()- verticalMargin);
        if(yValues.get(yValues.size()-1) != min && yValues.get(yValues.size()-1) != max){
            g2D.drawLine(horizontalMargin,(int)Math.floor(yValues.get(yValues.size()-1)),
                    getWidth()- horizontalMargin,(int)Math.floor(yValues.get(yValues.size()-1)));
        }


        g2D.drawString(maxValueString,5,verticalMargin + ClientView.CONTENT_FONT.getSize()/2);
        g2D.drawString(minValueString,5,getHeight() - verticalMargin + ClientView.CONTENT_FONT.getSize()/2);
        if(values.get(values.size()-1) != min && values.get(values.size()-1) != max){
            g2D.drawString(lastValueString,5,(int)(Math.floor(yValues.get(yValues.size()-1) )+ ClientView.CONTENT_FONT.getSize()/2));
        }
    }
}
