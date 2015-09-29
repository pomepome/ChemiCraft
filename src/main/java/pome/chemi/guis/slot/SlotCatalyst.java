package pome.chemi.guis.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import pome.chemi.items.ItemChemicals;

public class SlotCatalyst extends Slot {

	public SlotCatalyst(IInventory inv, int inventoryId, int x, int y) {
		super(inv, inventoryId, x, y);
	}
	public boolean isItemValid(ItemStack stack)
    {
		if(stack == null || stack.getItem() == null)
		{
			return false;
		}
		return stack.getItem() instanceof ItemChemicals && !this.getHasStack();
    }

}
