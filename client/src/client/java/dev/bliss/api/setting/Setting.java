package dev.bliss.api.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public abstract class Setting<T> {
    private final String name;
    private T value;
}
