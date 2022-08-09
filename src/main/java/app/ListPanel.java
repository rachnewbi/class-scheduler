/*package app;

import javax.swing.*;
import java.awt.*;

public class ListPanel extends JPanel {
    private MainFrame mainFrame;

    public ListPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        JLabel title = new JLabel("Modules: ");
        title.setAlignmentX(CENTER_ALIGNMENT);
        setLayout(new FlowLayout());
        add(title);
        showModuleCheckBoxes();
    }

    private void showModuleCheckBoxes() {
        for (Module m : mainFrame.getModules()) {
            JCheckBox checkBox = new JCheckBox(m.getName());
            checkBox.addActionListener(actionEvent -> {
                //set show class
            });
            add(checkBox);
        }
    }
}*/
