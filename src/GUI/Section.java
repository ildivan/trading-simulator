package GUI;

import javax.swing.*;
import java.awt.*;

public class Section extends JPanel {
    private final JPanel heading;
    private final JPanel content;

    public Section(String title, Color headingBackgroundColor, Color contentBackgroundColor){
        super();
        setLayout(new BorderLayout());
        heading = new JPanel();
        JLabel headingTitle = new JLabel();
        headingTitle.setFont(new Font("Times New Roman",Font.PLAIN,24));
        headingTitle.setText(title);
        heading.add(headingTitle);
        content = new JPanel();
        add(heading,BorderLayout.NORTH);
        add(content,BorderLayout.CENTER);
        heading.setBackground(headingBackgroundColor);
        content.setBackground(contentBackgroundColor);
    }

    public JPanel getHeading() {
        return heading;
    }

    public JPanel getContent() {
        return content;
    }
}
