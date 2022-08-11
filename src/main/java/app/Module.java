package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

import net.fortuna.ical4j.data.*;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import org.apache.commons.lang.StringUtils;

import static org.apache.commons.lang3.StringUtils.chop;
import static org.apache.commons.lang3.StringUtils.substringBetween;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class Module extends VEvent {
/*    private DtStart start;
    private DtEnd end;
    private String location;*/
    private Calendar calendar;
    private HashMap<Component, LinkedList<Property>> componentProperties;

    public Module(File icsFile) throws IOException, ParserException {
        FileInputStream fin = new FileInputStream(icsFile);
        CalendarBuilder builder = new CalendarBuilder();
        calendar = builder.build(fin);
        calendar.validate();

        // output components and properties for now to trouble shoot

        for (Iterator<CalendarComponent> i = calendar.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            System.out.println("Component [" + component.getName() + "]");

            LinkedList<Property> properties = new LinkedList<>();
            componentProperties.put(component, properties);

            for (Iterator<Property> j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
                properties.add(property);
            }
        }
    }

    public Integer getStart() {
        final String date = calendar.getProperty(Property.DTSTART).toString();
        int i = -1;
        try {
            i = Integer.parseInt(chop(chop(substringBetween(date, "T", ""))));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public String getEnd() {
        String date = calendar.getProperty(Property.DTEND).toString();
        return chop(chop(substringBetween(date, "T", "")));
    }
}
