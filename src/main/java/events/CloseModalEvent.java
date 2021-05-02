package events;

import javafx.event.Event;
import javafx.event.EventType;


public class CloseModalEvent extends Event {
    public static final EventType<CloseModalEvent> CLOSE_MODAL = new EventType<>("CLOSE_MODAL");

    public CloseModalEvent() {
        super(CLOSE_MODAL);
    }
}