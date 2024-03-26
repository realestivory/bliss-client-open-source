package dev.bliss.impl.modules.render;

import dev.bliss.api.module.Module;
import dev.bliss.api.module.enums.ModuleCategory;
import dev.bliss.client.Client;
import dev.bliss.impl.events.render.RenderInGameHudEvent;
import dev.bliss.impl.utils.render.font.FontRenderer;
import radbus.Listen;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;

public class HUDModule extends Module {
    public HUDModule() {
        super("HUD", "Heads up display.", ModuleCategory.RENDER);
        addSettings();
    }

    @Listen
    public void onRender(RenderInGameHudEvent event) {
        FontRenderer font = Client.INSTANCE.getFontManager().fontRenderer;

        if (mc.world == null || mc.player == null) {
            return;
        }
        if (mc.getDebugHud().shouldShowDebugHud()) {
            return;
        }

        font.drawString("Bliss Client | " + Client.version + " | " + Client.build, 4, 2, Color.WHITE);

        Comparator<Object> sortingMethod = Comparator.comparingDouble(module -> {
            String name = ((Module) module).getName();
            return font.getStringWidth(name);
        }).reversed();

        int moduleCount = 0;
        int screenWidth = mc.getWindow().getScaledWidth();
        List<Module> enabledModules = Client.INSTANCE.getModuleRegistry().getEnabledModules();
        enabledModules.sort(sortingMethod);

        for (Module m : enabledModules) {
            int offset = (int) (moduleCount * font.getHeight());
            float xPos = screenWidth - font.getStringWidth(m.getName() + " " + m.getSuffix());
            font.drawString(m.getName(), xPos - (!m.getSuffix().isEmpty() ? font.getStringWidth(" ") : 0) + 60, offset - 2, Color.WHITE);
            moduleCount++;
        }
    }
}
