package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Iterator;

import net.fortuna.ical4j.data.*;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;

public class Module extends VEvent {
    private DtStart start;
    private DtEnd end;
    private String location;

    public Module(File icsFile) throws IOException, ParserException {
        FileInputStream fin = new FileInputStream(icsFile);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);

        // output components and properties for now to trouble shoot

        for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            System.out.println("Component [" + component.getName() + "]");

            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }
    }
}
