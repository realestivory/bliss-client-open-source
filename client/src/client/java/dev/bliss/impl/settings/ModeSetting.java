package dev.bliss.impl.settings;

import dev.bliss.api.setting.Setting;
import lombok.Getter;

@Getter

public class ModeSetting<T extends Enum<?>> extends Setting<T> {
    private final T[] values;

    @SuppressWarnings("unchecked")
    public ModeSetting(String name, T value) {
        super(name, value);
        values = (T[]) value.getClass().getEnumConstants();
    }
}
