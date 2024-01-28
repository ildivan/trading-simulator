package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SectionWithList<T extends JPanel> extends Section{

    private final ArrayList<T> list;

    public SectionWithList(String title){
        super(title);
        list = new ArrayList<>();
        JPanel content = getContent();
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setAlignmentY(Component.TOP_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }

    public void addElementToList(T element){
        element.setPreferredSize(new Dimension(getContent().getWidth(),70));
        element.setMaximumSize(new Dimension(Integer.MAX_VALUE,70));
        getContent().add(element);
        list.add(element);
    }

    public void removeElementFromList(T element){
        getContent().remove(element);
        list.remove(element);
    }

    public ArrayList<T> getListOfElements() {
        return list;
    }
}
