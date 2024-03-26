package dev.bliss.mixin;

import dev.bliss.client.Client;
import dev.bliss.impl.events.network.NetworkPacketReceiveEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {

    @Shadow
    private static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener) {
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;)V"), method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V")
    private void receivePacket(Packet<?> packet, PacketListener listener) {
        NetworkPacketReceiveEvent packetReceiveEvent = new NetworkPacketReceiveEvent(packet);
        Client.INSTANCE.getEventBus().publish(packetReceiveEvent);

        if (!packetReceiveEvent.isCancelled()) {
            handlePacket(packetReceiveEvent.getPacket(), listener);
        }
    }
    
}
