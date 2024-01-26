package GUI;

import javax.swing.*;
import java.awt.*;

public class ClientView extends JFrame {


    private final Color headingBackgroundColor;
    private final Color contentBackgroundColor;

    public ClientView(){
        super("Client View");

        headingBackgroundColor = Color.GRAY;
        contentBackgroundColor = Color.LIGHT_GRAY;

        JPanel portfolioAndSale = getPortfolioAndSale();
        Section prices = getPriceSection();

        setSize(new Dimension(500,500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2));
        add(portfolioAndSale);
        add(prices);

        setVisible(true);
    }

    private JPanel getPortfolioAndSale() {
        JPanel portfolioAndSale = new JPanel();
        portfolioAndSale.setLayout(new GridLayout(2,1));
        SectionWithList portfolio = new SectionWithList("Portfolio",70,headingBackgroundColor,contentBackgroundColor);
        for (int i = 25; i > 0; i--) {
            JPanel toAdd = new JPanel();
            toAdd.setBackground(new Color(3*i,6*i,9*i));
            portfolio.addElementToList(toAdd);
        }
        Section sale = new Section("Buy & Sell",headingBackgroundColor,contentBackgroundColor);

        portfolioAndSale.add(portfolio);
        portfolioAndSale.add(sale);
        return portfolioAndSale;
    }

    private SectionWithList getPriceSection(){
        SectionWithList prices = new SectionWithList("Prices",70,headingBackgroundColor,contentBackgroundColor);
        for (int i = 25; i > 0; i--) {
            JPanel toAdd = new JPanel();
            toAdd.setBackground(new Color(9*i,3*i,6*i));
            prices.addElementToList(toAdd);
        }
        return prices;
    }
}
