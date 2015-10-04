package pome.chemi.api;

import net.minecraft.item.ItemStack;

public interface IChemiCraft
{
	ItemStack getChemicals(int ID);
	ItemStack getChemicals(String internalName);
	int registerChemicals(String display_name, String internal_name, String formula, EnumChemicalType type);
	void registerChemicalRecipe(IChemiRecipe recipe);
}
