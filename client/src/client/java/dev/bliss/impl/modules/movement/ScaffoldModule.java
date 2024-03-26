package dev.bliss.impl.modules.movement;


import dev.bliss.api.module.enums.ModuleCategory;
import dev.bliss.api.module.Module;
import dev.bliss.impl.settings.ModeSetting;
import net.minecraft.util.math.BlockPos;
import radbus.Listen;

import java.util.Arrays;
import java.util.List;

public class ScaffoldModule extends Module {

    private final ModeSetting<ScaffoldModeEnum> mode = new ModeSetting<>("Mode", ScaffoldModeEnum.WATCHDOG);
    public ScaffoldModule() {
        super("Scaffold", "Automatically places blocks under you", ModuleCategory.MOVEMENT);
        List<String> invalidBlocks = Arrays.asList("air", "water", "lava", "flowing_water", "flowing_lava");
        this.addSettings(mode);
    }
    @Listen
    public void onTick() {
        BlockPos blockPos = new BlockPos((int) mc.player.getX(), (int) (mc.player.getY()- 1), (int) mc.player.getZ());

    }
    public void placeBlock(BlockPos blockPos) {

    }
   private enum ScaffoldModeEnum{
        WATCHDOG,
        HAIR
    }
}
