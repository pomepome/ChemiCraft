package pome.chemi;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

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
