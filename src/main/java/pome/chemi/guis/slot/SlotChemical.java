package pome.chemi.guis.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import pome.chemi.items.ItemChemicals;

public class SlotChemical extends Slot
{
	public SlotChemical(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_)
	{
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}
	public boolean isItemValid(ItemStack stack)
    {
		if(stack == null || stack.getItem() == null)
		{
			return false;
		}
		return stack.getItem() instanceof ItemChemicals;
    }
}
