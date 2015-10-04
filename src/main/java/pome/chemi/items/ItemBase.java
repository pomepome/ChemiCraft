package pome.chemi.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import pome.chemi.ChemiCraft;

public class ItemBase extends Item
{
	public ItemBase(String name)
	{
		this.setUnlocalizedName(name).setCreativeTab(ChemiCraft.TabChemical).setTextureName("chemi:" + name);
		GameRegistry.registerItem(this, name);
	}
}
