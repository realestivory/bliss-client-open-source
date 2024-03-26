package dev.bliss.api.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CancellableEvent implements Event, Cancellable {
    private boolean cancelled;
}
