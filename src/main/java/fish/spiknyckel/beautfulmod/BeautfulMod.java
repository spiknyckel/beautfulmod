package fish.spiknyckel.beautfulmod;

import net.minecraft.client.options.KeyBinding;
import net.ornithemc.osl.keybinds.api.KeyBindingEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.ornithemc.osl.entrypoints.api.ModInitializer;
import org.lwjgl.input.Keyboard;

public class BeautfulMod implements ModInitializer {

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("Beautful Mod");


	@Override
	public void init() {
		KeyBindingEvents.REGISTER_KEYBINDS.register(registry -> {
			KeyBindings.carpetFaceInto = registry.register("Carpet Face Into", Keyboard.KEY_NONE, "BeautfulMod");
			KeyBindings.carpetFlipFace = registry.register("Carpet Flip Face", Keyboard.KEY_NONE, "BeautfulMod");
		});
		Config.load();
	}


}
