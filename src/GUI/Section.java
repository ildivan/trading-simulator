package GUI;

import javax.swing.*;
import java.awt.*;

public class Section extends JPanel {
    private final JPanel heading;
    private final JPanel content;

    public Section(){
        super();
        setLayout(new BorderLayout());
        heading = new JPanel();
        content = new JPanel();
        add(heading,BorderLayout.NORTH);
        add(content,BorderLayout.CENTER);
    }

    public JPanel getHeading() {
        return heading;
    }

    public JPanel getContent() {
        return content;
    }
}
