package pome.chemi.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pome.chemi.ChemiCraft;
import pome.chemi.util.Util;

public abstract class BlockWithDirection extends BlockContainer
{
	public BlockWithDirection(String name)
	{
		super(Material.ground);
		this.setBlockName(name).setCreativeTab(ChemiCraft.TabChemical);
		GameRegistry.registerBlock(this, name);
	}
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int facing = Util.getRelativeOrientation(entity);
		w.setBlockMetadataWithNotify(x, y, z, facing, 2);
	}
}
