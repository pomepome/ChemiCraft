package pome.chemi.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import pome.chemi.ChemiCraft;

public class CreativeTabChemicraft extends CreativeTabs
{

	public CreativeTabChemicraft() {
		super("ChemiCraft");
	}

	@Override
	public Item getTabIconItem() {
		return ChemiCraft.chemicals;
	}

}
