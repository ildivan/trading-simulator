import javax.swing.*;
import java.awt.*;

public class ClientView extends JFrame {
    public ClientView(){
        super("Client View");
        setSize(new Dimension(250,250));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2));

        JPanel portfolio = new JPanel();
        JPanel prices = new JPanel();

        portfolio.setLayout(new BorderLayout());
        prices.setLayout(new BorderLayout());

        JPanel portfolioHeading = new JPanel();
        JPanel pricesHeading = new JPanel();
        JPanel portfolioContent = new JPanel();
        JPanel pricesContent = new JPanel();

        portfolioHeading.setBackground(Color.CYAN);
        portfolioContent.setBackground(Color.BLUE);
        pricesHeading.setBackground(Color.PINK);
        pricesContent.setBackground(Color.RED);

        portfolio.add(portfolioHeading,BorderLayout.NORTH);
        portfolio.add(portfolioContent,BorderLayout.CENTER);
        prices.add(pricesHeading,BorderLayout.NORTH);
        prices.add(pricesContent,BorderLayout.CENTER);

        add(portfolio);
        add(prices);

        setVisible(true);
    }
}
