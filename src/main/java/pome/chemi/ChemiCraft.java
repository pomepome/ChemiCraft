package pome.chemi;

import static pome.chemi.api.Constants.*;
import static pome.chemi.api.EnumChemicalType.*;
import static pome.chemi.util.Util.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import pome.chemi.api.ChemiCraftAPI;
import pome.chemi.api.EnumChemicalType;
import pome.chemi.api.IChemiCraft;
import pome.chemi.api.IChemiRecipe;
import pome.chemi.blocks.BlockOxidizer;
import pome.chemi.handlers.GuiHandler;
import pome.chemi.items.ItemChemicals;
import pome.chemi.recipes.OxidizerRecipe;
import pome.chemi.recipes.RecipesRegistry;
import pome.chemi.tiles.TileEntityOxidizer;
import pome.chemi.util.Util;

@Mod(modid="ChemiCraft",name="ChemiCraft",version="test5")
public class ChemiCraft implements IChemiCraft
{
	@Mod.Instance("ChemiCraft")
	public static ChemiCraft instance;
	private static boolean test = true;
	public static CreativeTabs TabChemical;

	//Items
	public static Item chemicals;

	//Blocks
	public static Block oxidizer;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Util.init();
		TabChemical = new CreativeTabChemicraft();
		registerChemicals();
	}
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		chemicals = new ItemChemicals();

		oxidizer = new BlockOxidizer();
		{
			ItemStack is = ChemiCraftAPI.getAPI().getChemicals(2);
			addSRecipe(is,gs(Items.redstone));
		}
		registerRecipes();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		FMLCommonHandler.instance().bus().register(this);
		GameRegistry.registerTileEntity(TileEntityOxidizer.class, "Oxidizer");
	}

	public void registerChemicals()
	{
		registerChemicals("Vanadium pentoxide", "V2O5", SOLID);
		registerChemicals("Platinum", "Pt", SOLID);
		registerChemicals("Sulfur", "S", SOLID);
		registerChemicals("Sulfur dioxide", "SO2", GAS);
		registerChemicals("Sulfur trioxide", "SO3",GAS);
		registerChemicals("Hydrogen chloride", "HCl",GAS);
		registerChemicals("Nitrogen oxide", "NO", GAS);
		registerChemicals("Nitrogen dioxide", "NO2",GAS);
		registerChemicals("Concentrated sulfuric acid", "H2SO4", FLUID);
		registerChemicals("Dilute sulfuric acid", "H2SO4", FLUID);
		registerChemicals("Concentrated hydrochloric acid", "HCl", FLUID);
		registerChemicals("Dilute hydrochloric acid", "HCl", FLUID);
	}

	public void registerRecipes()
	{
		registerChemicalRecipe(new OxidizerRecipe(getChemicals(S),null,true,getChemicals(SO2)));
		registerChemicalRecipe(new OxidizerRecipe(getChemicals(SO2),getChemicals(V2O5),true,getChemicals(SO3)));
		registerChemicalRecipe(new OxidizerRecipe(getChemicals(NO),null,false,getChemicals(NO2)));
	}

	@Override
	public ItemStack getChemicals(int ID)
	{
		return gs(chemicals, 1, ID);
	}

	@Override
	public int registerChemicals(String name, String formula, EnumChemicalType type)
	{
		return ChemicalsRegistry.registerChemical(name, formula, type);
	}
	@Override
	public void registerChemicalRecipe(IChemiRecipe recipe)
	{
		RecipesRegistry.registerRecipe(recipe);
	}

	@SubscribeEvent
	public void onPlayerUpdate(TickEvent.PlayerTickEvent event)
	{
		EntityPlayer p = event.player;
		if(p.worldObj.isRemote)
		{
			return;
		}
		if(test)
		{
			ForgeDirection dir = getLeftSide(ForgeDirection.EAST);
			chatToPlayer(p, getSideName(dir));
			test = false;
		}
	}

	public static ItemStack gs(Object obj)
	{
		if(obj != null)
		{
			if(obj instanceof Item)
			{
				return new ItemStack((Item)obj);
			}
			if(obj instanceof Block)
			{
				return new ItemStack((Block)obj);
			}
			if(obj instanceof ItemStack)
			{
				return (ItemStack)obj;
			}
		}
		return null;
	}
	public static ItemStack gs(Object obj,int size)
	{
		if(obj == null)
		{
			return null;
		}
		ItemStack stack = gs(obj);
		stack.stackSize = size;
		return stack;
	}
	public static ItemStack gs(Object obj,int size,int damage)
	{
		if(obj == null)
		{
			return null;
		}
		ItemStack stack = gs(obj,size);
		stack.setItemDamage(damage);
		return stack;
	}
	public static void addRecipe(ItemStack dest,Object... objs)
	{
		GameRegistry.addRecipe(dest, objs);
	}
	public static void addSRecipe(ItemStack dest,Object... objs)
	{
		GameRegistry.addShapelessRecipe(dest, objs);
	}
}
