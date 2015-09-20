package pome.chemi.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import pome.chemi.api.EnumRecipeType;
import pome.chemi.api.IChemiRecipe;

public class RecipesRegistry
{
	private static List<IChemiRecipe> listRecipes = new ArrayList<IChemiRecipe>();

	public static void registerRecipe(IChemiRecipe recipe)
	{
		listRecipes.add(recipe);
	}
	public static List<IChemiRecipe> getRecipesForType(EnumRecipeType type)
	{
		List<IChemiRecipe> list = new ArrayList<IChemiRecipe>();
		for(IChemiRecipe recipe : listRecipes)
		{
			if(recipe.getRecipeType() == type)
			{
				list.add(recipe);
			}
		}
		return list;
	}
	public static ItemStack getDestFromStacks(ItemStack... stacks)
	{
		ItemStack ret = null;
		for(IChemiRecipe recipe : listRecipes)
		{
			ItemStack[] sources = recipe.getSources();
			boolean flag = false;
			if(sources.length != sources.length)
			{
				continue;
			}
			for(int i = 0;i < sources.length;i++)
			{
				ItemStack comp = stacks[i];
				ItemStack source = sources[i];
				if(comp == null && source == null)
				{
					continue;
				}
				if(comp == null || source == null)
				{
					flag = true;
					continue;
				}
				if(comp.getItem() != source.getItem() || comp.getItemDamage() != source.getItemDamage())
				{
					flag = true;
				}
			}
			if(flag)
			{
				continue;
			}
			else
			{
				ret = recipe.getDest();
				break;
			}
		}
		return ret;
	}
}
