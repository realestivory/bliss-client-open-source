package dev.bliss;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bliss implements ModInitializer {
	public static final String MODID = "bliss";
    public static final Logger LOGGER = LoggerFactory.getLogger("bliss");

	@Override
	public void onInitialize() {
		LOGGER.info("Bliss is initializing!");
	}
}