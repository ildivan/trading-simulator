package GUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.ArrayList;

public class SectionWithList<T extends JPanel> extends Section{

    private final ArrayList<T> list;
    private static final int SCROLL_SPEED = 15;

    public SectionWithList(String title){
        super(title);
        list = new ArrayList<>();
        JPanel content = getContent();
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setAlignmentY(Component.TOP_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setUnitIncrement(SCROLL_SPEED);
        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ClientView.HEADING_BACKGROUND_COLOR;
                this.trackColor = ClientView.CONTENT_BACKGROUND_COLOR;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new BasicArrowButton(orientation) {
                    {
                        setBackground(ClientView.HEADING_BACKGROUND_COLOR);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new BasicArrowButton(orientation) {
                    {
                        setBackground(ClientView.HEADING_BACKGROUND_COLOR);
                        setForeground(ClientView.HEADING_BACKGROUND_COLOR);
                    }
                };
            }
        });
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
