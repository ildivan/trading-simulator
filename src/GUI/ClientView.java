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

        setSize(new Dimension(1000,1000));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2,0,0));
        add(portfolioAndSale);
        add(prices);

        setVisible(true);
    }

    private JPanel getPortfolioAndSale() {
        JPanel portfolioAndSale = new JPanel();
        portfolioAndSale.setLayout(new GridLayout(2,1,0,0));
        SectionWithList<PortfolioItem> portfolio = new SectionWithList<>("PORTFOLIO");
        portfolio.addElementToList(new PortfolioItem("BTC", 3,60000.F));
        portfolio.addElementToList(new PortfolioItem("ETH", 15,6000.F));
        portfolio.addElementToList(new PortfolioItem("TESLA", 5,1600.F));
        portfolio.addElementToList(new PortfolioItem("BTC", 3,60000.F));
        portfolio.addElementToList(new PortfolioItem("ETH", 15,6000.F));
        portfolio.addElementToList(new PortfolioItem("TESLA", 5,1600.F));
        portfolio.addElementToList(new PortfolioItem("BTC", 3,60000.F));
        portfolio.addElementToList(new PortfolioItem("ETH", 15,6000.F));
        portfolio.addElementToList(new PortfolioItem("TESLA", 5,1600.F));
        Section sale = new Section("BUY & SELL");

        portfolioAndSale.add(portfolio);
        portfolioAndSale.add(sale);
        return portfolioAndSale;
    }

    private SectionWithList<PriceItem> getPriceSection(){
        SectionWithList<PriceItem> prices = new SectionWithList<>("PRICES");
        prices.addElementToList(new PriceItem("BTC",20000.0F,true));
        prices.addElementToList(new PriceItem("ETH",400.0F,false));
        prices.addElementToList(new PriceItem("TESLA",320.0F,true));
        prices.addElementToList(new PriceItem("BTC",20000.0F,true));
        prices.addElementToList(new PriceItem("ETH",400.0F,false));
        prices.addElementToList(new PriceItem("TESLA",320.0F,true));
        prices.addElementToList(new PriceItem("BTC",20000.0F,true));
        prices.addElementToList(new PriceItem("ETH",400.0F,false));
        prices.addElementToList(new PriceItem("TESLA",320.0F,true));
        prices.addElementToList(new PriceItem("BTC",20000.0F,true));
        prices.addElementToList(new PriceItem("ETH",400.0F,false));
        prices.addElementToList(new PriceItem("TESLA",320.0F,true));
        return prices;
    }
}
