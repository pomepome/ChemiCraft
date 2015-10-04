package pome.chemi.api;

import net.minecraft.item.ItemStack;

public interface IChemiRecipe
{
	public EnumRecipeType getRecipeType();
	public boolean needFire();
	public boolean causeExplosion();
	public boolean matches(ItemStack... stacks);
	public ItemStack[] getDests();
	public ItemStack[] getSources();
}
