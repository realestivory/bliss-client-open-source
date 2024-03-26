package dev.bliss.api.module;

import dev.bliss.api.event.Event;
import dev.bliss.impl.events.game.GameKeyPressEvent;
import dev.bliss.impl.modules.combat.VelocityModule;
import dev.bliss.impl.modules.movement.NoSlowDownModule;
import dev.bliss.impl.modules.render.HUDModule;
import radbus.Listen;
import radbus.PubSub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModuleRegistry {
    private final Map<Class<? extends Module>, Module> map = new HashMap<>();
    private final Map<String, Module> nameToModuleMap = new HashMap<>();

    public ModuleRegistry(final PubSub<Event> pubSub) {
        register(
                VelocityModule.class,
                NoSlowDownModule.class,
                HUDModule.class
        );

        // Module registry needs to be subscribed, so we can toggle a module whenever its bound key gets pressed
        pubSub.subscribe(this);
    }

    @SafeVarargs
    private void register(final Class<? extends Module>... klasses) {
        for (final Class<? extends Module> klass : klasses) {
            try {
                final Module instance = klass.getConstructor().newInstance();

                map.put(klass, instance);
                nameToModuleMap.put(instance.getName(), instance);
            } catch (Exception exception) {
                throw new RuntimeException("Could not instantiate " + klass, exception);
            }
        }
    }

    public ArrayList<Module> getEnabledModules() {
        ArrayList<Module> enabledModules = new ArrayList<>();
        for (Module module : map.values()) {
            if (module.isEnabled()) continue;
            enabledModules.add(module);
        }
        return enabledModules;
    }




    public <V extends Module> V get(final Class<V> klass) {
        return klass.cast(map.get(klass));
    }

    public Module getByName(final String name) {
        return nameToModuleMap.get(name);
    }


    @Listen
    public void onKeyPress(final GameKeyPressEvent event) {
        map.values().stream()
                .filter(module -> event.key() == module.getKey())
                .forEach(Bindable::onKeyPressed);
    }
}
