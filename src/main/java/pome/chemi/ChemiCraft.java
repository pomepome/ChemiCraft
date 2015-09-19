package pome.chemi;

import static pome.chemi.api.EnumChemicalType.*;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pome.chemi.api.ChemicalsRegistry;
import pome.chemi.api.IChemiCraft;
import pome.chemi.items.ItemChemicals;

@Mod(modid="ChemiCraft",name="ChemiCraft",version="test1")
public class ChemiCraft implements IChemiCraft
{
	@Mod.Instance
	public static ChemiCraft instance;

	public static CreativeTabs TabChemical;

	public static Item chemicals;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		TabChemical = new CreativeTabChemicraft();
		registerChemicals();
	}
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		chemicals = new ItemChemicals();
	}

	public void registerChemicals()
	{
		ChemicalsRegistry.registerChemical("Vanadium(V) oxide", SOLID);
		ChemicalsRegistry.registerChemical("Sulfur dioxide", GAS);
		ChemicalsRegistry.registerChemical("Sulfur trioxide", GAS);
		ChemicalsRegistry.registerChemical("Concentrated sulfuric acid",FLUID);
		ChemicalsRegistry.registerChemical("Dilute sulfuric acid",FLUID);
	}

	@Override
	public ItemStack getChemicals(int ID)
	{
		return new ItemStack(chemicals, ID);
	}
}
