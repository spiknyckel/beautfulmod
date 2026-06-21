package fish.spiknyckel.beautfulmod.gui.config;

import fish.spiknyckel.beautfulmod.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.Objects;

public class IntegerEntry extends StandardRowEntry<IntegerEntry> {
	private ButtonWidget button;
	private Config.ConfigValue<Integer> configOption;

	public IntegerEntry(Config.ConfigValue<Integer> option, boolean reset) {
		super(option.getName(), true, reset, "");

		this.configOption = option;
		this.button = new ButtonWidget(0, 0, 0, 100, 20, option.get().toString());

		onReset((source) -> configOption.set(configOption.getDefaultValue()));
	}

	public ButtonWidget getButton() { return button; }

	@Override
	protected void draw(int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, float partialTicks) {
		this.button.x = x + listWidth / 2;
		this.button.y = y;
		this.button.message = this.getDisplayString();
		this.button.render(Minecraft.getInstance(), mouseX, mouseY, partialTicks);

		if (this.reset)
			this.resetButton.active = !Objects.equals(this.configOption.get(), this.configOption.getDefaultValue());
	}

	protected String getDisplayString() {
		return this.configOption.get().toString();
	}

	@Override
	protected boolean mouseDown(int x, int y, int button) {
		if (this.button.mouseClicked(Minecraft.getInstance(), x, y)) {
			this.button.playClickSound(Minecraft.getInstance().getSoundManager());
			this.performAction();
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void mouseUp(int x, int y, int button) {
		this.button.mouseReleased(x, y);
	}
}
