package dev.bliss.mixin;

import dev.bliss.client.Client;
import dev.bliss.impl.events.network.NetworkPacketSendEvent;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;send(Lnet/minecraft/network/packet/Packet;)V"), method = "sendPacket")
    private void sendPacket(ClientConnection instance, Packet<?> packet) {
        NetworkPacketSendEvent packetSendEvent = new NetworkPacketSendEvent(packet);
        Client.INSTANCE.getEventBus().publish(packetSendEvent);
        if (!packetSendEvent.isCancelled()) {
            instance.send(packetSendEvent.getPacket());
        }
    }
}
