package dev.bliss.impl.settings;

import dev.bliss.api.setting.Setting;
import lombok.Getter;
import net.minecraft.util.math.MathHelper;

@Getter

public class FloatSetting extends Setting<Float> {
    private final float min, max, inc;

    public FloatSetting(String name, float value, float min, float max, float inc) {
        super(name, value);
        this.min = min;
        this.max = max;
        this.inc = inc;
    }

    @Override
    public void setValue(final Float value) {
        //this.value = Math.min(max, Math.max(min, Math.round(value / inc) * inc));
        super.setValue(MathHelper.clamp(Math.round(value / inc) * inc, min, max));
    }
}
