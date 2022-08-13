package app;

/**
 * @author Rachael Newbigging
 * Represents an event as defined in the imported ics file.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import net.fortuna.ical4j.data.*;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import org.apache.commons.lang.StringUtils;


@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class CalEvent extends VEvent {
    private Calendar calendar;
    private HashMap<Component, LinkedList<Property>> componentProperties;
    private HashMap<String,String> eventProperties = new HashMap<>();
    private String eventName;
    private boolean showOnCalendar;

    /**
     * Creates a new CalEvent by parsing the given ics file.
     * Saves event properties to List for further access.
     * @param icsFile The ics file from which the event is created.
     * @throws IOException
     * @throws ParserException
     */
    public CalEvent(File icsFile) throws IOException, ParserException {
        final String[] nameParts = icsFile.getName().split("\\.")[0].split("_");
        eventName = StringUtils.join(Arrays.copyOfRange(nameParts, 1, nameParts.length), " ");

        FileInputStream fin = new FileInputStream(icsFile);
        CalendarBuilder builder = new CalendarBuilder();
        calendar = builder.build(fin);
        calendar.validate();

        // output components and properties for now to trouble shoot

        for (final CalendarComponent calendarComponent : calendar.getComponents()) {
            Component component = (Component) calendarComponent;
            System.out.println("Component [" + component.getName() + "]");
            for (Property property : component.getProperties()) {
                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
                if(component.getName().equals("VEVENT")) {
                    eventProperties.put(property.getName(), property.getValue());
                }
            }
        }
    }

    public LocalDateTime getStart() {
        StringBuilder sb = new StringBuilder(eventProperties.get("DTSTART")); //YYYYMMDDTHHMMSS
        sb
                .insert(4, "-")
                .insert(7, "-")
                .insert(13, ":")
                .insert(16, ":");
        return LocalDateTime.parse(sb.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public LocalDateTime getEnd() {
        StringBuilder sb = new StringBuilder(eventProperties.get("DTEND")); //YYYYMMDDTHHMMSS
        sb
                .insert(4, "-")
                .insert(7, "-")
                .insert(13, ":")
                .insert(16, ":");
        return LocalDateTime.parse(sb.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getEventSummary() {
        return eventProperties.get("SUMMARY");
    }

    public String getEventLocation() {
        return eventProperties.get("LOCATION");
    }

    public String getModName() {
        return eventName;
    }

    public void setShowOnCalendar(boolean showOnCalendar) {
        this.showOnCalendar = showOnCalendar;
    }

    public boolean showOnCalendar() {
        return showOnCalendar;
    }

    public int getDayOfWeek() {
        return LocalDateTime.parse(String.valueOf(getStart())).getDayOfWeek().getValue();
    }
}
