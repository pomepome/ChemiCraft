package pome.chemi.items;

import java.util.List;

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
import pome.chemi.ChemicalsRegistry;
import pome.chemi.api.EnumChemicalType;
import pome.chemi.util.Util;

public class ItemChemicals extends ItemBase
{
	IIcon Fluid,Solid,Gas;
	public ItemChemicals()
	{
		super("chemicals");
		this.setMaxDamage(0).setHasSubtypes(true);
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
		}
		return stack;
	}
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		String typeString = "";
		EnumChemicalType type = getType(stack);
		if(type != null)
		{
			switch(type)
			{
				case FLUID : typeString = EnumChatFormatting.AQUA + Util.translate("ch.fluid"); break;
				case SOLID : typeString = EnumChatFormatting.YELLOW + Util.translate("ch.solid"); break;
				case GAS   : typeString = EnumChatFormatting.GOLD + Util.translate("ch.gas"); break;
			}
		}
		list.add(Util.translate("ch.type")+": "+typeString);
		list.add(Util.translate("ch.formula")+": "+ getFormula(stack));
	}
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return Util.translate("ch."+getName(stack).toLowerCase().replace(" ", "_"));
	}
	public static void sendMessage(EntityPlayer p, String message)
	{
		p.addChatMessage(new ChatComponentText(message));
	}
}
