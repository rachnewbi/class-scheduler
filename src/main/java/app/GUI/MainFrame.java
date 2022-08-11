/**
 * @author Rachael Newbigging
 */

package app.GUI;

import app.Module;
import net.fortuna.ical4j.data.ParserException;
import org.apache.commons.lang.StringUtils;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class MainFrame extends JFrame {
    private final ListPanel listPanel = new ListPanel();

    protected LinkedList<Module> modules = new LinkedList<>();

    MainFrame() {
        super("Module Scheduling Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(new BorderLayout());

        initMenuBar();
        add(listPanel, BorderLayout.EAST);
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
                    modules.add(new Module(f));
                    final String[] nameParts = f.getName().split("\\.")[0].split("_");
                    final String name = StringUtils.join(Arrays.copyOfRange(nameParts, 1, nameParts.length), " ");
                    listPanel.addCheckBox(name);
                }
            } catch (final IOException | ParserException e) {
                System.out.println(e.getMessage());
            }
            repaint();
            revalidate();
        }
    }

    private LinkedList<Module> getModules() {
        return modules;
    }
}
