package pome.chemi.api;

import net.minecraft.item.ItemStack;

public interface IChemiRecipe
{
	public EnumRecipeType getRecipeType();
	public boolean needFire();
	public ItemStack getDest();
	public ItemStack[] getSources();
}
