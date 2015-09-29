package pome.chemi.recipes;

import net.minecraft.item.ItemStack;
import pome.chemi.api.EnumRecipeType;
import pome.chemi.api.IChemiRecipe;
import pome.chemi.util.Util;

public class AbsorberRecipe implements IChemiRecipe
{
	ItemStack solute;
	ItemStack solvent;
	ItemStack[] dest;
	public AbsorberRecipe(ItemStack psolute,ItemStack psolvent,ItemStack... destination)
	{
		solute = psolute;
		solvent = psolvent;
		this.dest = Util.copyStacks(destination);
	}
	@Override
	public EnumRecipeType getRecipeType()
	{
		return EnumRecipeType.ABSORBER;
	}

	@Override
	public boolean needFire()
	{
		return false;
	}

	@Override
	public ItemStack[] getDests()
	{
		return dest;
	}

	@Override
	public ItemStack[] getSources()
	{
		return new ItemStack[]{solute,solvent};
	}
	@Override
	public boolean matches(ItemStack... stacks)
	{
		if(stacks.length != 2)
		{
			return false;
		}
		return Util.rightForRecipe(solute, stacks[0]) && Util.rightForRecipe(solvent, stacks[1]);
	}

}
