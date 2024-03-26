package dev.bliss.impl.utils.render;

import dev.bliss.api.module.enums.ModuleCategory;
import dev.bliss.api.setting.Setting;
import dev.bliss.impl.utils.Utils;

public class ClickGuiModuleButtonUtil implements Utils {
    public ModuleCategory category;
    public Module module;

    public double x, y, width, height;
    private boolean sliderDrag;

    public void ModuleButton(Module mod, ModuleCategory tab) {
        this.module = mod;
        this.category = tab;
    }
    double sCount = 0;
    double sIndex = 0;
    Setting numberSet;
}
