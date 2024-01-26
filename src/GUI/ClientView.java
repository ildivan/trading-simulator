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
        SectionWithList portfolio = new SectionWithList("Portfolio",headingBackgroundColor,contentBackgroundColor);
        portfolio.addElementToList(new PortfolioItem("BTC", 20000.0F,3));
        portfolio.addElementToList(new PortfolioItem("ETH", 400.0F,15));
        portfolio.addElementToList(new PortfolioItem("TESLA", 320.0F,5));
        Section sale = new Section("Buy & Sell",headingBackgroundColor,contentBackgroundColor);

        portfolioAndSale.add(portfolio);
        portfolioAndSale.add(sale);
        return portfolioAndSale;
    }

    private SectionWithList getPriceSection(){
        return new SectionWithList("Prices",headingBackgroundColor,contentBackgroundColor);
    }
}
