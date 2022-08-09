package app;

import net.fortuna.ical4j.data.ParserException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class MainFrame extends JFrame {
    //private final ListPanel listPanel = new ListPanel();
    public LinkedList<Module> modules = new LinkedList<>();
    private JPanel listPanel;

    public MainFrame() {
        super("Module Scheduling Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(new BorderLayout());

        initMenuBar();
        loadListPanel();
        add(listPanel, BorderLayout.EAST);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem loadModule = new JMenuItem("Add Module");
        JMenuItem exit = new JMenuItem("Exit");

        menuBar.add(menu);
        menu.add(loadModule);
        menu.add(exit);
        setJMenuBar(menuBar);

        loadModule.addActionListener(actionEvent -> loadICSFile());
        exit.addActionListener(actionEvent -> System.exit(0));
    }

    private void loadICSFile() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(new FileNameExtensionFilter("*.ics", "ics"));
        if (chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
            try {
                File[] files = chooser.getSelectedFiles();
                for (File f : files) {
                    modules.add(new Module(f));
                }
            } catch (IOException | ParserException e) {
                System.out.println(e.getMessage());
            }
            loadListPanel();
            revalidate();
            repaint();
            //show in list panel
        }
    }

    private void loadListPanel() {
        listPanel = new JPanel();
        JLabel title = new JLabel("Modules: ");
        title.setAlignmentX(CENTER_ALIGNMENT);
        listPanel.setLayout(new FlowLayout());
        listPanel.add(title);

        for (Module m : modules) {
            JCheckBox checkBox = new JCheckBox("Test");
            checkBox.addActionListener(actionEvent -> {
                //set show class
            });
            listPanel.add(checkBox);
        }
    }
}
