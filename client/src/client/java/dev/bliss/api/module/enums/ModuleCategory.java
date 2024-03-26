package dev.bliss.api.module.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModuleCategory {
    COMBAT("Combat"),
    PLAYER("Player"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    EXPLOIT("Exploit");

    private final String name;
}
