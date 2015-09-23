package pome.chemi.recipes;

import net.minecraft.item.ItemStack;
import pome.chemi.api.EnumRecipeType;
import pome.chemi.api.IChemiRecipe;

public class AbsorberRecipe implements IChemiRecipe
{
	ItemStack solute;
	ItemStack solvent;
	ItemStack dest;
	public AbsorberRecipe(ItemStack psolute,ItemStack psolvent,ItemStack dest)
	{
		solute = psolute;
		solvent = psolvent;
		this.dest = dest;
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
	public ItemStack getDest()
	{
		return dest;
	}

	@Override
	public ItemStack[] getSources()
	{
		return new ItemStack[]{solute,solvent};
	}

}
