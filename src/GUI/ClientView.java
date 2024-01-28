package GUI;

import javax.swing.*;
import java.awt.*;

public class ClientView extends JFrame {


    public static final Color HEADING_BACKGROUND_COLOR = Color.GRAY;
    public static final Color CONTENT_BACKGROUND_COLOR = Color.DARK_GRAY;
    public static final Color FONT_COLOR = Color.WHITE;
    public static final Font HEADING_FONT = new Font("Times New Roman",Font.PLAIN,24);
    public static final Font CONTENT_FONT = new Font("Times New Roman",Font.PLAIN,16);

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

    private JPanel getPortfolioAndSale() {
        JPanel portfolioAndSale = new JPanel();
        portfolioAndSale.setLayout(new GridLayout(2,1));
        SectionWithList portfolio = new SectionWithList("Portfolio");
        portfolio.addElementToList(new PortfolioItem("BTC", 20000.0F,3));
        portfolio.addElementToList(new PortfolioItem("ETH", 400.0F,15));
        portfolio.addElementToList(new PortfolioItem("TESLA", 320.0F,5));
        Section sale = new Section("Buy & Sell");

        portfolioAndSale.add(portfolio);
        portfolioAndSale.add(sale);
        return portfolioAndSale;
    }

    private SectionWithList getPriceSection(){
        SectionWithList prices = new SectionWithList("Prices");
        prices.addElementToList(new PriceItem("BTC",20000.0F,true));
        prices.addElementToList(new PriceItem("ETH",400.0F,true));
        prices.addElementToList(new PriceItem("TESLA",320.0F,true));
        return prices;
    }
}
