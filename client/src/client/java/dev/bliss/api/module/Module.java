package dev.bliss.api.module;

import dev.bliss.client.Client;
import dev.bliss.api.module.enums.ModuleCategory;
import dev.bliss.api.setting.Setting;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor

public abstract class Module implements Bindable {
    // TODO: Dependency injection for MC and client var
    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    private final String name, description;
    private final ModuleCategory category;

    private int key;
    private boolean enabled;

    private final List<Setting<?>> settings = new ArrayList<>();

    public void setEnabled(boolean enabled) {
        if(this.enabled == enabled) {
            return;
        }

        this.enabled = enabled;

        if(enabled) {
            Client.INSTANCE.getEventBus().subscribe(this);
            onEnable();
        } else {
            Client.INSTANCE.getEventBus().unsubscribe(this);
            onDisable();
        }
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }

    public Optional<String> getSuffix() {
        return Optional.empty();
    }

    @Override
    public void onKeyPressed() {
        setEnabled(!enabled);
    }

    // I honestly hate doing this but there's no proper way of doing it in Java
    // That's why Kotlin is better!
    protected void addSettings(Setting<?>... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }
}
