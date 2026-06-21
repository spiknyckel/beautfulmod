package fish.spiknyckel.beautfulmod.gui.config;

import fish.spiknyckel.beautfulmod.Config;
import fish.spiknyckel.beautfulmod.gui.ConfigList;
import net.minecraft.client.Minecraft;

public class BeautfulConfigList extends ConfigList {

	public BeautfulConfigList(Minecraft mcIn, int slotHeightIn) {
		super(mcIn, slotHeightIn);
	}

	@Override
	public void initGui() {
		Config.load();
		for (Config.ConfigBoolean v : Config.BOOLEANS) {
			addEntry(new BooleanEntry(v, true));
		}

		for (Config.ConfigInteger v : Config.INTEGERS) {
			addEntry(new IntegerSliderEntry(v, true));
		}
	}

	@Override
	public void onClose() {
		super.onClose();
		Config.save();
	}
}
