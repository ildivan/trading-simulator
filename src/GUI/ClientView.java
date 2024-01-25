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

        Section portfolio = new Section("Portfolio");
        Section sale = new Section("Buy & Sell");

        portfolioAndSale.add(portfolio);
        portfolioAndSale.add(sale);
        return portfolioAndSale;
    }

    private Section getPriceSection(){
        return new Section("Prices");
    }
}
