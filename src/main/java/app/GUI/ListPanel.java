package app.GUI;

import app.CalEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;

public class ListPanel extends JPanel {
    private final LinkedList<JCheckBox> checkBoxes = new LinkedList<>();
    private MainFrame mainFrame;

    ListPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        final JLabel title = new JLabel("Modules ");
        title.setAlignmentX(SwingConstants.CENTER);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new LineBorder(Color.BLACK));
        add(title);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        for (JCheckBox c : checkBoxes) {
            add(c);
        }
        revalidate();
    }

    void addCheckBox(String name, CalEvent event) {
        final JCheckBox checkBox = new JCheckBox(name);
        checkBox.addActionListener(actionEvent -> {
            event.setShowOnCalendar(checkBox.isSelected());
            mainFrame.repaint();
        });
        checkBox.doClick();
        checkBoxes.add(checkBox);
    }
}
