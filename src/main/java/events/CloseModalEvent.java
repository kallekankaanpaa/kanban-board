package events;

import javafx.event.Event;
import javafx.event.EventType;


public class CloseModalEvent extends Event {
    public static final EventType<CloseModalEvent> CLOSE_MODAL = new EventType<>("CLOSE_MODAL");

    private Boolean _new = false;

    public CloseModalEvent() {
        super(CLOSE_MODAL);
    }

    public CloseModalEvent(Boolean _new) {
        super(CLOSE_MODAL);
        this._new = _new;
    }

    public Boolean isNew() { return _new; }

    public void setNew(Boolean value) {
        _new = value;
    }
}