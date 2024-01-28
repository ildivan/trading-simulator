package GUI;

import javax.swing.*;
import java.awt.*;

public class Section extends JPanel {
    private final JPanel heading;
    private final JPanel content;

    public Section(String title){
        super();
        setLayout(new BorderLayout());
        heading = new JPanel();
        JLabel headingTitle = new JLabel();
        headingTitle.setFont(ClientView.HEADING_FONT);
        headingTitle.setForeground(ClientView.FONT_COLOR);
        headingTitle.setText(title);
        heading.add(headingTitle);
        content = new JPanel();
        add(heading,BorderLayout.NORTH);
        add(content,BorderLayout.CENTER);
        heading.setBackground(ClientView.HEADING_BACKGROUND_COLOR);
        content.setBackground(ClientView.CONTENT_BACKGROUND_COLOR);
    }

    public JPanel getHeading() {
        return heading;
    }

    public JPanel getContent() {
        return content;
    }
}
