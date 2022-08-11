package app.GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;

public class ListPanel extends JPanel {
    private final LinkedList<JCheckBox> checkBoxes = new LinkedList<>();

    ListPanel() {
        final JLabel title = new JLabel("Modules: ");
        setAlignmentX(LEFT_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new LineBorder(Color.BLACK));
        setPreferredSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width / 4),
                Toolkit.getDefaultToolkit().getScreenSize().height));
        add(title);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        for (JCheckBox c : checkBoxes) {
            add(c);
        }
    }

    void addCheckBox(String name) {
        checkBoxes.add(new JCheckBox(name));
    }
}
