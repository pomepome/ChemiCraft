package pome.chemi.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import pome.chemi.api.EnumRecipeType;
import pome.chemi.api.IChemiRecipe;
import pome.chemi.util.Util;

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
				if(!Util.areStacksEqual(source, comp))
				{
					flag = true;
				}
			}
			if(!flag)
			{
				ret = recipe;
				break;
			}
		}
		return ret;
	}
	public static ItemStack getDestFromStacks(EnumRecipeType type,ItemStack... stacks)
	{
		IChemiRecipe recipe = getRecipeFromStacks(type,stacks);
		return recipe != null ? recipe.getDest() : null;
	}
}
