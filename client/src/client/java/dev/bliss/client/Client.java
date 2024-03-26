package dev.bliss.client;


import dev.bliss.api.event.Event;
import dev.bliss.api.module.ModuleRegistry;
import dev.bliss.impl.modules.combat.VelocityModule;
import dev.bliss.impl.modules.movement.NoSlowDownModule;
import dev.bliss.impl.modules.render.HUDModule;
import dev.bliss.impl.utils.render.font.FontManager;
import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;

import radbus.PubSub;

@Getter

public class Client implements ClientModInitializer {
	public static final Client INSTANCE = new Client();

	public static String name = "Bliss";
	public static String version = "0.0.1";
	public static String build = "Development";

	private final PubSub<Event> eventBus = PubSub.newInstance(System.err::println);
	private final ModuleRegistry moduleRegistry = new ModuleRegistry(eventBus);
	private final FontManager fontManager = new FontManager();

	@Override
	public void onInitializeClient() {
			moduleRegistry.get(NoSlowDownModule.class).setEnabled(true);
			moduleRegistry.get(VelocityModule.class).setEnabled(true);
			moduleRegistry.get(HUDModule.class).setEnabled(true);
	}
}