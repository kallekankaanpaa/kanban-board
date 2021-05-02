package events;

import javafx.event.Event;
import javafx.event.EventType;


public class RefreshEvent extends Event {
    public static final EventType<RefreshEvent> REFRESH = new EventType<>("REFRESH");

    public RefreshEvent() {
        super(REFRESH);
    }
}