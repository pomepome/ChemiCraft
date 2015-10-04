package pome.chemi.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import pome.chemi.ChemiCraft;

public class CreativeTabChemiBlock extends CreativeTabs {

	public CreativeTabChemiBlock()
	{
		super("chemi.block");
	}

	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(ChemiCraft.oxidizer);
	}

}
