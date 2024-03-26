package dev.bliss.impl.events.network;

import dev.bliss.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.packet.Packet;

@Getter
@Setter
@AllArgsConstructor

public class NetworkPacketReceiveEvent extends CancellableEvent {
    private Packet<?> packet;
}
