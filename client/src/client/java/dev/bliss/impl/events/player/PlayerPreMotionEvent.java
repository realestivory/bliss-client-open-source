package dev.bliss.impl.events.player;

import dev.bliss.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PlayerPreMotionEvent extends CancellableEvent {
    private double x, y, z;
    private float yaw, pitch;
    private boolean ground;
}
