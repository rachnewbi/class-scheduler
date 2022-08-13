package app.GUI;

/**
 * @author Rachael Newbigging
 * Displays a weekly calendar view.
 * Time blocks are displayed based on imported {@link CalEvent} and if they are selected in the {@link ListPanel}.
 */

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
    //private final String[] columnNames = {"", weekDays.MONDAY.name(), weekDays.TUESDAY.name(), weekDays.WEDNESDAY.name(), weekDays.THURSDAY.name(), weekDays.FRIDAY.name()};
    private final LinkedList<String> columns  = new LinkedList<String>();
    private String[] columnNames;
    private String[] rows;
    private final MainFrame mainFrame;

    private DefaultTableModel model;

    CalPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

        columns.addAll(Arrays.asList("", weekDays.MONDAY.name(), weekDays.TUESDAY.name(), weekDays.WEDNESDAY.name(),
                weekDays.THURSDAY.name(), weekDays.FRIDAY.name()));

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
        initColumns();
        model = new DefaultTableModel(null, columnNames);
        final JTable table = new JTable(model);
        table.getColumn("").setPreferredWidth(getWidth()/8);
        final JScrollPane pane = new JScrollPane(table);

        add(pane, BorderLayout.CENTER);
    }

    //TODO multicolumn
    private void initColumns() {
        columnNames = new String[columns.size()];
        columns.toArray(columnNames);
    }

    private void initRows() {
        try {
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
        } catch (NullPointerException nullPointerException) {
            rows = new String[0];
        }
    }
    private LocalDateTime[] getMinMaxTime() {
        final LocalDateTime[] minStartMaxEnd = new LocalDateTime[2];
        for (CalEvent event : mainFrame.getEvents()) {
            if (event.showOnCalendar()) {
                if (minStartMaxEnd[0] == null || event.getStart().isBefore(minStartMaxEnd[0])) {
                    minStartMaxEnd[0] = event.getStart();
                }
                if (minStartMaxEnd[1] == null || event.getEnd().isAfter(minStartMaxEnd[1])) {
                    minStartMaxEnd[1] = event.getEnd();
                }
            }
        }
        return minStartMaxEnd;
    }

    private void refreshWeekView() {
        initColumns();
        initRows();
        model.setRowCount(rows.length);
        for (int i = 0; i < rows.length; i++) {
            model.setValueAt(rows[i], i, 0);
        }
        checkSelectedEvents();
    }

    private void checkSelectedEvents() {
        for (CalEvent e : mainFrame.getEvents()) {
            int rowNumberStart;
            int rowNumberEnd;

            try {
                rowNumberStart = findRow(e.getStart().getHour() + ":" +
                        e.getStart().getMinute());
                rowNumberEnd = findRow(e.getEnd().getHour() + ":" +
                        e.getEnd().getMinute());
            } catch (IndexOutOfBoundsException outOfBoundsException) {
                rowNumberStart = 0;
                rowNumberEnd = rows.length;
            }

            String value;
            for (int i = 0; i < rowNumberEnd; i++) {
                if (e.showOnCalendar()) {
                    if (i < rowNumberStart) {
                        value = "";
                    } else if (i == rowNumberStart) {
                        value = e.getModName();
                    }
                    else {
                        value = "X";
                    }
                } else {
                    value = "";
                }
                model.setValueAt(value, i, e.getDayOfWeek());
            }
        }
    }

    private int findRow(String timestamp) throws IndexOutOfBoundsException {
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].contains(timestamp)) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException();
    }

}
