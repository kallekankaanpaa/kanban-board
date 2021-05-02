package events;

import javafx.event.Event;
import javafx.event.EventType;

import data.Column;


public class RemoveColumnEvent extends Event {
    public static final EventType<RemoveColumnEvent> REMOVE = new EventType<>("REMOVE");

    private Column column;

    public RemoveColumnEvent(Column column) {
        super(REMOVE);
        this.column = column;
    }

    public Column getColumn() {
        return this.column;
    }
}