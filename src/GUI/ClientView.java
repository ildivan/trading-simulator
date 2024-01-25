package GUI;

import javax.swing.*;
import java.awt.*;

public class ClientView extends JFrame {
    public ClientView(){
        super("Client View");

        JPanel portfolioAndSale = getPortfolioAndSale();
        Section prices = getPriceSection();

        setSize(new Dimension(500,500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2));
        add(portfolioAndSale);
        add(prices);

        setVisible(true);
    }

    private static JPanel getPortfolioAndSale() {
        JPanel portfolioAndSale = new JPanel();
        portfolioAndSale.setLayout(new GridLayout(2,1));

        Section portfolio = new Section();
        JPanel portfolioHeading = portfolio.getHeading();
        JPanel portfolioContent = portfolio.getContent();
        portfolioHeading.setBackground(Color.PINK);
        portfolioContent.setBackground(Color.RED);


        Section sale = new Section();
        JPanel saleHeading = sale.getHeading();
        JPanel saleContent = sale.getContent();
        saleHeading.setBackground(Color.CYAN);
        saleContent.setBackground(Color.BLUE);

        portfolioAndSale.add(portfolio);
        portfolioAndSale.add(sale);
        return portfolioAndSale;
    }

    private Section getPriceSection(){
        Section prices = new Section();
        JPanel pricesHeading = prices.getHeading();
        JPanel pricesContent = prices.getContent();
        pricesHeading.setBackground(Color.GRAY);
        pricesContent.setBackground(Color.LIGHT_GRAY);
        return prices;
    }
}
