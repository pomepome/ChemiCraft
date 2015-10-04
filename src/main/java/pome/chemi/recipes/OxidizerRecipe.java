package pome.chemi.recipes;

import net.minecraft.item.ItemStack;
import pome.chemi.api.EnumRecipeType;
import pome.chemi.api.IChemiRecipe;
import pome.chemi.util.Util;

public class OxidizerRecipe implements IChemiRecipe
{
	ItemStack oxidized;
	ItemStack catalyst;
	ItemStack[] dest;
	boolean needFire;
	boolean causeExplosion;
	public OxidizerRecipe(ItemStack toOxidize,ItemStack catalyst,boolean needFire, boolean causeExplosion,ItemStack... destination)
	{
		oxidized = toOxidize;
		this.catalyst = catalyst;
		this.needFire = needFire;
		this.dest = Util.copyStacks(destination);
		this.causeExplosion = causeExplosion;
	}
	@Override
	public EnumRecipeType getRecipeType() {
		return EnumRecipeType.OXIDIZER;
	}

	@Override
	public boolean needFire()
	{
		return needFire;
	}

	@Override
	public ItemStack[] getDests() {
		return dest;
	}

	@Override
	public ItemStack[] getSources() {
		return new ItemStack[]{oxidized,catalyst};
	}
	@Override
	public boolean matches(ItemStack... stacks)
	{
		if(stacks.length != 2)
		{
			return false;
		}
		if(catalyst != null)
		{
			if(stacks[1] == null || !Util.areStacksEqualRecipe(catalyst, stacks[1]))
			{
				return false;
			}
		}
		return Util.areStacksEqual(oxidized, stacks[0]) && stacks[0].stackSize >= oxidized.stackSize;
	}
	@Override
	public boolean causeExplosion()
	{
		return causeExplosion;
	}

}
