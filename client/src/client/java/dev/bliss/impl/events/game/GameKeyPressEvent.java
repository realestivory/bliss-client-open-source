package dev.bliss.impl.events.game;

import dev.bliss.api.event.Event;

public record GameKeyPressEvent(int key) implements Event {
}
