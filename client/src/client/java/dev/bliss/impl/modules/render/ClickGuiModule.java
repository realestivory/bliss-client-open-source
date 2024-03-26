package dev.bliss.impl.modules.render;

import dev.bliss.api.module.Module;
import dev.bliss.api.module.enums.ModuleCategory;
import dev.bliss.impl.events.render.RenderScreen;
import net.minecraft.client.MinecraftClient;
import radbus.Listen;

public class ClickGuiModule extends Module {
    public ClickGuiModule() {
        super("Click Gui", "Shows Modules", ModuleCategory.RENDER);
    }

    @Listen
    public void onRenderScreen(RenderScreen event) {
        if (MinecraftClient.getInstance().mouse.wasLeftButtonClicked()) {

        }
    }
}