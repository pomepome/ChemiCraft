package pome.chemi.blocks;

import java.util.Random;

import net.minecraft.item.Item;
import pome.chemi.ChemiCraft;
import pome.chemi.util.Util;

public class BlockOreSulfur extends BlockBase
{
	public BlockOreSulfur()
	{
		super("chemi.sulfur_ore");
		this.setBlockTextureName("chemi:ore/sulfur_ore").setHardness(10);
	}
	public int quantityDropped(Random rand)
    {
		return Util.getRandomInt(rand, 2, 4);
    }
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		return ChemiCraft.sulfur;
    }
}
