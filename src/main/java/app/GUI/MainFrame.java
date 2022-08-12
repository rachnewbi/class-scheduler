/**
 * @author Rachael Newbigging
 */

package app.GUI;

import app.CalEvent;
import net.fortuna.ical4j.data.ParserException;
import org.apache.commons.lang.StringUtils;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class MainFrame extends JFrame {
    private final ListPanel listPanel = new ListPanel(this);
    private final CalPanel calPanel = new CalPanel(this);
    private LinkedList<CalEvent> events = new LinkedList<>();

    public MainFrame() {
        super("Module Scheduling Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLayout(new BorderLayout());

        initMenuBar();
        add(listPanel, BorderLayout.EAST);
        add(calPanel, BorderLayout.CENTER);
    }

    private void initMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu menu = new JMenu("File");
        final JMenuItem loadModule = new JMenuItem("Add Module");
        final JMenuItem exit = new JMenuItem("Exit");

        menuBar.add(menu);
        menu.add(loadModule);
        menu.add(exit);
        setJMenuBar(menuBar);

        loadModule.addActionListener(actionEvent -> loadICSFile());
        exit.addActionListener(actionEvent -> System.exit(0));
    }

    private void loadICSFile() {
        final JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(new FileNameExtensionFilter("*.ics", "ics"));
        if (chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
            try {
                final File[] files = chooser.getSelectedFiles();
                System.out.println(files[0].getName());
                for (final File f : files) {
                    events.add(new CalEvent(f));
                    final String[] nameParts = f.getName().split("\\.")[0].split("_");
                    final String name = StringUtils.join(Arrays.copyOfRange(nameParts, 1, nameParts.length), " ");
                    listPanel.addCheckBox(name, events.getLast());
                }
            } catch (final IOException | ParserException | NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
            //calPanel.refreshWeekView();
            repaint();
        }
    }

    LinkedList<CalEvent> getEvents() {
        return events;
    }
}
