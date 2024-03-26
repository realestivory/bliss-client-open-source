package dev.bliss.impl.modules.combat;

import dev.bliss.impl.events.network.NetworkPacketReceiveEvent;
import dev.bliss.api.module.enums.ModuleCategory;
import dev.bliss.api.module.Module;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import radbus.Listen;

public final class VelocityModule extends Module {
    public VelocityModule() {
        super("Velocity", "fat", ModuleCategory.COMBAT);
    }

    @Listen
    public void onPacketReceive(NetworkPacketReceiveEvent event) {
        final var player = mc.player;
        if(player == null) {
            return;
        }

        if(!(event.getPacket() instanceof EntityVelocityUpdateS2CPacket packet)) {
            return;
        }

        if(packet.getId() == player.getId()) {
            event.setCancelled(true);
        }
    }
}
