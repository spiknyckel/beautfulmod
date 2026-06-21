package fish.spiknyckel.beautfulmod.gui;

import fish.spiknyckel.beautfulmod.Config;
import fish.spiknyckel.beautfulmod.gui.config.BaseEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.EntryListWidget;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigList extends EntryListWidget {
	private final List<Entry> entries = new ArrayList<>();

	public ConfigList(Minecraft mcIn, int slotHeightIn) {
		super(mcIn, 0, 0, 0, 0, slotHeightIn);
	}

	public int getSelectedElement() { return pos; }

	public abstract void initGui();

	public void onClose()
	{
	}

	public <T> BaseEntry<T> addEntry(BaseEntry<T> entry) {
		this.entries.add(entry);
		return entry;
	}

	@Override
	public int getRowWidth() {
		return 180 * 2;
	}

	@Override
	protected int getScrollbarPosition() {
		return this.width / 2 + getRowWidth() / 2 + 4;
	}

	@Override
	public Entry getEntry(int index) {
		return entries.get(index);
	}

	@Override
	protected int size() {
		return entries.size();
	}


	@Override
	protected void entryClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
		if (getSelectedElement() != -1 && getSelectedElement() != slotIndex)
		{
			((BaseEntry<?>) this.getEntry(getSelectedElement())).testFocused(mouseX, mouseY);
		}

		if (slotIndex != -1)
		{
			((BaseEntry<?>) this.getEntry(slotIndex)).testFocused(mouseX, mouseY);
		}
	}
}
