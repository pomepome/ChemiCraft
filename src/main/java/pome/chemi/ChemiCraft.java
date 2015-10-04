package pome.chemi;

import static pome.chemi.api.EnumChemicalType.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pome.chemi.api.EnumChemicalType;
import pome.chemi.api.IChemiCraft;
import pome.chemi.api.IChemiRecipe;
import pome.chemi.blocks.BlockAbsorber;
import pome.chemi.blocks.BlockOrePlatinum;
import pome.chemi.blocks.BlockOreSulfur;
import pome.chemi.blocks.BlockOxidizer;
import pome.chemi.creativetabs.CreativeTabChemiBlock;
import pome.chemi.creativetabs.CreativeTabChemicraft;
import pome.chemi.handlers.GuiHandler;
import pome.chemi.items.ItemChemicals;
import pome.chemi.items.ItemPlatinum;
import pome.chemi.items.ItemSulfur;
import pome.chemi.proxies.CommonProxy;
import pome.chemi.recipes.AbsorberRecipe;
import pome.chemi.recipes.OxidizerRecipe;
import pome.chemi.recipes.RecipesRegistry;
import pome.chemi.tiles.TileEntityAbsorber;
import pome.chemi.tiles.TileEntityOxidizer;
import pome.chemi.util.Util;
import pome.chemi.world.ChemiOreGenerator;

@Mod(modid="ChemiCraft",name="ChemiCraft",version="test6")
public class ChemiCraft implements IChemiCraft
{
	@Mod.Instance("ChemiCraft")
	public static ChemiCraft instance;

	@SidedProxy(serverSide="pome.chemi.proxies.CommonProxy",clientSide="pome.chemi.proxies.CommonProxy")
	public static CommonProxy proxy;

	private static boolean test = true;
	public static CreativeTabs TabChemical;
	public static CreativeTabs TabChemicalBlock;

	//Items
	public static Item chemicals;
	public static Item sulfur;
	public static Item platinum;

	//Blocks
	public static Block oxidizer;
	public static Block absorber;

	//ore Blocks
	public static Block oreSulfur;
	public static Block orePlatinum;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Util.init();
		TabChemical = new CreativeTabChemicraft();
		TabChemicalBlock = new CreativeTabChemiBlock();
		registerChemicals();
	}
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		chemicals = new ItemChemicals();
		sulfur = new ItemSulfur();
		platinum = new ItemPlatinum();

		oxidizer = new BlockOxidizer();
		absorber = new BlockAbsorber();

		oreSulfur = new BlockOreSulfur();
		orePlatinum = new BlockOrePlatinum();
		{
			GameRegistry.addSmelting(orePlatinum, new ItemStack(platinum), 80);
		}
		registerRecipes();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		FMLCommonHandler.instance().bus().register(this);
		GameRegistry.registerWorldGenerator(new ChemiOreGenerator(), 10);
		registerTileEntities();
	}
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityOxidizer.class, "Oxidizer");
		GameRegistry.registerTileEntity(TileEntityAbsorber.class, "Absorber");
	}
	public void registerChemicals()
	{
		registerChemicals("Vanadium pentoxide", "VO", "V2O5", SOLID);
		registerChemicals("Platinum", "Pt", "Pt", SOLID);
		registerChemicals("Sulfur", "Sul", "S", SOLID);
		registerChemicals("Sulfur dioxide", "SO2", "SO2", GAS);
		registerChemicals("Sulfur trioxide", "SO3", "SO3",GAS);
		registerChemicals("Hydrogen chloride", "HClGas", "HCl",GAS);
		registerChemicals("Nitrogen oxide", "NO", "NO", GAS);
		registerChemicals("Nitrogen dioxide", "NO2", "NO2",GAS);
		registerChemicals("Anmonia", "Anmonia", "NH3",GAS);
		registerChemicals("Concentrated sulfuric acid", "cSA", "H2SO4", FLUID);
		registerChemicals("Dilute sulfuric acid", "dSA","H2SO4", FLUID);
		registerChemicals("Concentrated hydrochloric acid", "cHA", "HCl", FLUID);
		registerChemicals("Dilute hydrochloric acid", "dHA", "HCl", FLUID);
		registerChemicals("Concentrated nitric acid", "cNA", "HNO3", FLUID);
		registerChemicals("Dilute nitric acid", "dNA", "HNO3", FLUID);
		registerChemicals("Water", "H2O", "H2O", FLUID);
	}

	public void registerRecipes()
	{
		registerChemicalRecipe(new OxidizerRecipe(getChemicals("Sul"),null,true,false,getChemicals("SO2")));
		registerChemicalRecipe(new OxidizerRecipe(getChemicals("SO2"),getChemicals("V2O5"),true,false,getChemicals("SO3")));
		registerChemicalRecipe(new OxidizerRecipe(getChemicals("NO"),null,false,false,getChemicals("NO2")));
		registerChemicalRecipe(new AbsorberRecipe(getChemicals("HClGas"),getChemicals("H2O"),false,getChemicals("cHA")));
		registerChemicalRecipe(new AbsorberRecipe(getChemicals("cHA"),getChemicals("H2O"),false,getChemicals("dHA")));
		registerChemicalRecipe(new AbsorberRecipe(changeAmount(getChemicals("NO2"), 3),getChemicals("H2O"),false,changeAmount(getChemicals("dNA") ,2),getChemicals("NO")));
		registerChemicalRecipe(new AbsorberRecipe(getChemicals("H2O"),getChemicals("H2O"),false,getChemicals("cHA")));
	}
	public ItemStack changeAmount(ItemStack stack, int amount)
	{
		if(stack == null)
		{
			return null;
		}
		ItemStack ret = stack.copy();
		ret.stackSize = amount;
		return ret;
	}
	@Override
	public ItemStack getChemicals(int ID)
	{
		return gs(chemicals, 1, ID);
	}
	@Override
	public ItemStack getChemicals(String internalName)
	{
		int id = ChemicalsRegistry.getChemicalId(internalName);
		return id < 0 ? null : getChemicals(id);
	}
	@Override
	public int registerChemicals(String display_name, String internal_name, String formula, EnumChemicalType type)
	{
		return ChemicalsRegistry.registerChemical(display_name, internal_name, formula, type);
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
