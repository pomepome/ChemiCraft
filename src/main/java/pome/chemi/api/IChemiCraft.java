package pome.chemi.api;

import net.minecraft.item.ItemStack;

public interface IChemiCraft
{
	ItemStack getChemicals(int ID);
	int registerChemicals(String name, String formula, EnumChemicalType type);
	void registerChemicalRecipe(IChemiRecipe recipe);
}
