package fish.spiknyckel.beautfulmod.gui.config;

import fish.spiknyckel.beautfulmod.Config;
import fish.spiknyckel.beautfulmod.gui.widgets.SliderWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;

public class IntegerSliderEntry extends StandardRowEntry<IntegerSliderEntry> {
	private SliderWidget slider;
	private Config.ConfigInteger configOption;

	public IntegerSliderEntry(Config.ConfigInteger option, boolean reset) {
		super(option.getName(), true, reset, "");

		this.configOption = option;
		this.slider = new SliderWidget(0, 0, 0, 100, 20, option.asFloatRange());

		onAction((source) -> configOption.set(configOption.calculateValue(slider.getValue())));
		onReset((source) -> configOption.set(configOption.getDefaultValue()));
	}

	public SliderWidget getButton() { return slider; }

	@Override
	protected void draw(int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, float partialTicks) {
		this.slider.x = x + listWidth / 2;
		this.slider.y = y;
		this.slider.message = configOption.message(this.slider.getValue());
		this.slider.render(Minecraft.getInstance(), mouseX, mouseY, partialTicks);
	}

	protected String getDisplayString() {
		return this.configOption.get().toString();
	}

	@Override
	protected boolean mouseDown(int x, int y, int button) {
		if (this.slider.mouseClicked(Minecraft.getInstance(), x, y)) {
			this.slider.playClickSound(Minecraft.getInstance().getSoundManager());

			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void mouseUp(int x, int y, int button) {
		this.slider.mouseReleased(x, y);
		this.performAction();
	}

}
