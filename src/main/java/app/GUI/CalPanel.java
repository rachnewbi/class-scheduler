package app.GUI;

import app.Main;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

public class CalPanel extends JPanel {
    private String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private String[] rows;
    private MainFrame mainFrame;


    CalPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        final JLabel title = new JLabel("Week View");
        title.setAlignmentX(CENTER_ALIGNMENT);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        add(title, BorderLayout.NORTH);
        add(createWeekPanel(), BorderLayout.SOUTH);

    }

    private JPanel createWeekPanel() {
        JPanel weekPanel = new JPanel();
        weekPanel.setLayout(new GridLayout());

        return weekPanel;
    }

    private void initRows() {
        int[] minMax = getMinMax();
        // TODO
    }

    private int[] getMinMax() {
        // TODO
        return null;
    }

}
