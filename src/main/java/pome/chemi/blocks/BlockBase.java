package pome.chemi.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import pome.chemi.ChemiCraft;

public class BlockBase extends Block
{
	public BlockBase(String name)
	{
		super(Material.rock);
		this.setBlockName(name).setCreativeTab(ChemiCraft.TabChemicalBlock).setHarvestLevel("pickaxe", 1);
		GameRegistry.registerBlock(this, name);
	}

}
