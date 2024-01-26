package GUI;

import javax.swing.*;
import java.awt.*;

public class SectionWithList extends Section{

    private final int fixedHeight;

    public SectionWithList(String title, int fixedHeight ,Color headingBackgroundColor, Color contentBackgroundColor){
        super(title,headingBackgroundColor,contentBackgroundColor);
        this.fixedHeight = fixedHeight;
        JPanel content = getContent();
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setAlignmentY(Component.TOP_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }

    public void addElementToList(JPanel element){
        element.setPreferredSize(new Dimension(getContent().getWidth(),fixedHeight));
        element.setMaximumSize(new Dimension(Integer.MAX_VALUE,fixedHeight));
        getContent().add(element);
    }
}
