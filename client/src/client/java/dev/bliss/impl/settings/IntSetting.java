package dev.bliss.impl.settings;

import dev.bliss.api.setting.Setting;
import lombok.Getter;
import net.minecraft.util.math.MathHelper;

@Getter

public class IntSetting extends Setting<Integer> {
    private final int min, max, inc;

    public IntSetting(String name, int value, int min, int max, int inc) {
        super(name, value);
        this.min = min;
        this.max = max;
        this.inc = inc;
    }

    @Override
    public void setValue(Integer value) {
        //this.number = Math.min(max, Math.max(min, Math.round(number / inc) * inc));
        super.setValue(MathHelper.clamp((value / inc) * inc, min, max));
    }
}
