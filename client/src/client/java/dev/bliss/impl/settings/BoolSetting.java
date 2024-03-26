package dev.bliss.impl.settings;

import dev.bliss.api.setting.Setting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BoolSetting extends Setting<Boolean> {
    public BoolSetting(String name, boolean state) {
        super(name, state);
    }
}
