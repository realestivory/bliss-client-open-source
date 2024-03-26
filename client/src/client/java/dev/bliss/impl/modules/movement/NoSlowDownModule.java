package dev.bliss.impl.modules.movement;

import dev.bliss.impl.events.player.PlayerSlowdownEvent;
import dev.bliss.api.module.enums.ModuleCategory;
import dev.bliss.api.module.Module;
import dev.bliss.impl.settings.ModeSetting;
import net.minecraft.item.*;
import radbus.Listen;

public final class NoSlowDownModule extends Module {
    private final ModeSetting<NoSlowDownModeEnum> mode = new ModeSetting<>("Mode", NoSlowDownModeEnum.WATCHDOG);

    public NoSlowDownModule() {
        super("NoSlow", "Stops Item Slowdown", ModuleCategory.MOVEMENT);
        this.addSettings(mode);
    }

    @Listen
    public void onSlowdown(final PlayerSlowdownEvent event) {
        final ItemStack mainHandStack = mc.player.getMainHandStack();
        final Item mainHandItem = mainHandStack.getItem();

        if(mode.getValue() == NoSlowDownModeEnum.WATCHDOG && (mainHandItem == Items.AIR || mainHandItem instanceof PotionItem || mainHandItem instanceof MilkBucketItem)) {
            return;
        }

        event.setCancelled(true);
    }

    private enum NoSlowDownModeEnum {
        WATCHDOG,
        HAIR
    }
}
