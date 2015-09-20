package pome.chemi.items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import pome.chemi.ChemiCraft;
import pome.chemi.ChemicalsRegistry;
import pome.chemi.api.EnumChemicalType;

public class ItemChemicals extends Item
{
	IIcon Fluid,Solid,Gas;
	public ItemChemicals()
	{
		super();
		GameRegistry.registerItem(this,"chemicals");
		this.setMaxDamage(0).setHasSubtypes(true).setCreativeTab(ChemiCraft.TabChemical).setUnlocalizedName("chemicals");
	}

	private static EnumChemicalType getType(ItemStack stack)
	{
		return ChemicalsRegistry.getType(stack.getItemDamage());
	}
	private static String getName(ItemStack stack)
	{
		return ChemicalsRegistry.getName(stack.getItemDamage());
	}
	private static String getFormula(ItemStack stack)
	{
		return ChemicalsRegistry.getFormula(stack.getItemDamage());
	}

	public String getUnlocalizedName(ItemStack stack)
    {
		EnumChemicalType type = getType(stack);
		if(type == null)
		{
			return "item.chemical_null";
		}
		switch(type)
		{
			case FLUID : return "item.chemical_fluid";
			case SOLID : return "item.chemical_solid";
			case GAS : return "item.chemical_gas";
		}
		return "item.chemical_unknown";
    }
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		for(int i = 0;i < ChemicalsRegistry.getRegisterdChemicalsCount();i++)
		{
			list.add(new ItemStack(item,1,i));
		}
    }
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
		Fluid = register.registerIcon("bucket_water");
		Solid = register.registerIcon("sugar");
		Gas = register.registerIcon("potion_bottle_empty");
    }
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
		EnumChemicalType type = ChemicalsRegistry.getType(damage);
		if(type != null)
		{
			switch(type)
			{
				case FLUID : return Fluid;
				case SOLID: return Solid;
				case GAS: return Gas;
			}
		}
		return null;
    }
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			player.addChatMessage(new ChatComponentText(ChemicalsRegistry.getName(stack.getItemDamage())));
		}
		return stack;
	}
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		String typeString = "";
		EnumChemicalType type = getType(stack);
		switch(type)
		{
			case FLUID : typeString = EnumChatFormatting.AQUA + "Fluid"; break;
			case SOLID : typeString = EnumChatFormatting.YELLOW + "Solid"; break;
			case GAS   : typeString = EnumChatFormatting.GOLD + "Gas"; break;
		}
		list.add("Type: "+typeString);
		list.add("Formula: "+ getFormula(stack));
	}
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return getName(stack);
	}
}
