package GUI;

import javax.swing.*;
import java.awt.*;

public class SectionWithList extends Section{

    public SectionWithList(String title){
        super(title);
        JPanel content = getContent();
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setAlignmentY(Component.TOP_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }

    public void addElementToList(JPanel element){
        element.setPreferredSize(new Dimension(getContent().getWidth(),70));
        element.setMaximumSize(new Dimension(Integer.MAX_VALUE,70));
        getContent().add(element);
    }
}
