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
	public static IChemiRecipe getRecipesFor(int id)
	{
		if(id >= listRecipes.size())
		{
			return null;
		}
		else
		{
			return listRecipes.get(id);
		}
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
	public static IChemiRecipe getRecipeFromStacks(EnumRecipeType type,ItemStack... stacks)
	{
		IChemiRecipe ret = null;
		for(IChemiRecipe recipe : getRecipesForType(type))
		{
			if(recipe.matches(stacks))
			{
				ret = recipe;
				break;
			}
		}
		return ret;
	}
	public static ItemStack[] getDestsFromStacks(EnumRecipeType type,ItemStack... stacks)
	{
		IChemiRecipe recipe = getRecipeFromStacks(type,stacks);
		return recipe != null ? recipe.getDests() : null;
	}
}
