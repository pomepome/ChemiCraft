package pome.chemi.recipes;

import net.minecraft.item.ItemStack;
import pome.chemi.api.EnumRecipeType;
import pome.chemi.api.IChemiRecipe;

public class OxidizerRecipe implements IChemiRecipe
{
	ItemStack oxydized;
	ItemStack catalyst;
	ItemStack dest;
	boolean needFire;
	public OxidizerRecipe(ItemStack toOxydize,ItemStack catalyst,ItemStack destination,boolean needFire)
	{
		oxydized = toOxydize;
		this.catalyst = catalyst;
		this.needFire = needFire;
	}
	@Override
	public EnumRecipeType getRecipeType() {
		return EnumRecipeType.OXYDIZER;
	}

	@Override
	public boolean needFire()
	{
		return needFire;
	}

	@Override
	public ItemStack getDest() {
		return dest;
	}

	@Override
	public ItemStack[] getSources() {
		return new ItemStack[]{oxydized,catalyst};
	}

}
