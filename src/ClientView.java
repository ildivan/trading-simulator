import javax.swing.*;
import java.awt.*;

public class ClientView extends JFrame {
    public ClientView(){
        super("Client View");

        JPanel portfolioAndSale = new JPanel();
        portfolioAndSale.setLayout(new GridLayout(2,1));

        JPanel portfolio = new JPanel();
        portfolio.setLayout(new BorderLayout());
        JPanel portfolioHeading = new JPanel();
        JPanel portfolioContent = new JPanel();
        portfolioHeading.setBackground(Color.PINK);
        portfolioContent.setBackground(Color.RED);
        portfolio.add(portfolioHeading,BorderLayout.NORTH);
        portfolio.add(portfolioContent,BorderLayout.CENTER);

        JPanel sale = new JPanel();
        sale.setLayout(new BorderLayout());
        JPanel saleHeading = new JPanel();
        JPanel saleContent = new JPanel();
        saleHeading.setBackground(Color.CYAN);
        saleContent.setBackground(Color.BLUE);
        sale.add(saleHeading,BorderLayout.NORTH);
        sale.add(saleContent,BorderLayout.CENTER);

        portfolioAndSale.add(portfolio);
        portfolioAndSale.add(sale);

        JPanel prices = new JPanel();
        prices.setLayout(new BorderLayout());
        JPanel pricesHeading = new JPanel();
        JPanel pricesContent = new JPanel();
        pricesHeading.setBackground(Color.GRAY);
        pricesContent.setBackground(Color.LIGHT_GRAY);
        prices.add(pricesHeading,BorderLayout.NORTH);
        prices.add(pricesContent,BorderLayout.CENTER);

        setSize(new Dimension(500,500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2));
        add(portfolioAndSale);
        add(prices);

        setVisible(true);
    }
}
