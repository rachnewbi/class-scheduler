package app.GUI;

import app.CalEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class CalPanel extends JPanel {
    enum weekDays { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY };
    private String[] columnNames = {"", weekDays.MONDAY.name(), weekDays.TUESDAY.name(), weekDays.WEDNESDAY.name(), weekDays.THURSDAY.name(), weekDays.FRIDAY.name()};
    private String[] rows;
    private MainFrame mainFrame;

    private DefaultTableModel model;

    CalPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

        add(new JLabel("Week View", JLabel.CENTER), BorderLayout.NORTH);
        createWeekPanel();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (!mainFrame.getEvents().isEmpty()) {
            refreshWeekView();
        }
    }

    private void createWeekPanel() {
        model = new DefaultTableModel(null, columnNames);
        final JTable table = new JTable(model);
        table.getColumn("").setPreferredWidth(getWidth()/8);
        final JScrollPane pane = new JScrollPane(table);

        add(pane, BorderLayout.CENTER);
    }

    private void initRows() {
        final long offset = 15L;
        final LocalDateTime[] minMax = getMinMaxTime();
        LocalTime time = minMax[0].toLocalTime();
        final LinkedList<String> rowsList = new LinkedList<>();
        while (time.isBefore(minMax[1].plusMinutes(offset).toLocalTime())) {
            final String minute;
            if (time.getMinute() == 0) {
                minute = "00";
            } else {
                minute = Integer.toString(time.getMinute());
            }
            rowsList.add(time.getHour() + ":" + minute);
            time = time.plusMinutes(offset);
        }
        rows = new String[rowsList.size()];
        rowsList.toArray(rows);
    }

    private LocalDateTime[] getMinMaxTime() {
        final LocalDateTime[] minStartMaxEnd = {
                mainFrame.getEvents().get(0).getStart(),
                mainFrame.getEvents().get(0).getEnd()
        };
        for (CalEvent m : mainFrame.getEvents()) {
            final LocalDateTime min = m.getStart();
            final LocalDateTime max = m.getEnd();
            if (min.isBefore(minStartMaxEnd[0])) {
                minStartMaxEnd[0] = min;
            }
            if (max.isAfter(minStartMaxEnd[1])) {
                minStartMaxEnd[1] = max;
            }
        }
        return minStartMaxEnd;
    }

    private void refreshWeekView() {
        initRows();
        model.setRowCount(rows.length);
        for (int i = 0; i < rows.length; i++) {
            model.setValueAt(rows[i], i, 0);
        }
        checkSelectedEvents();
    }

    private void checkSelectedEvents() {
        for (CalEvent e : mainFrame.getEvents()) {
            final int rowNumberStart = findRow(e.getStart().getHour() + ":" +
                    e.getStart().getMinute());
            final int rowNumberEnd = findRow(e.getEnd().getHour() + ":" +
                    e.getEnd().getMinute());

            if (e.getShowOnCalendar()) {
                final String name = e.getModName();
                model.setValueAt(name, rowNumberStart, e.getDayOfWeek());
                for (int i = rowNumberStart + 1; i < rowNumberEnd + 1; i++) { //change to filled cell
                    model.setValueAt("X" , i, e.getDayOfWeek());
                }
            } else {
                for (int i = rowNumberStart; i < rowNumberEnd + 1; i++) { //reformat if filled
                    model.setValueAt("" , i, e.getDayOfWeek());
                }
            }
        }
    }

    private int findRow(String timestamp) {
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].contains(timestamp)) {
                return i;
            }
        }
        return -1;
    }

}
