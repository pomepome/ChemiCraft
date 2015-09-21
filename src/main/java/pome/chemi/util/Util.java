package pome.chemi.util;

import net.minecraft.item.ItemStack;

public class Util
{
	public static boolean areStacksEqual(ItemStack src,ItemStack cmp)
	{
		if(src == null && cmp != null)
		{
			return true;
		}
		if(src == null || cmp == null)
		{
			return false;
		}
		return src.getItem() == cmp.getItem() && src.getItemDamage() == cmp.getItemDamage();
	}
}
