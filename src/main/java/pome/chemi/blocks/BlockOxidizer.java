package pome.chemi.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pome.chemi.ChemiCraft;
import pome.chemi.api.Constants;
import pome.chemi.tiles.TileEntityOxidizer;

public class BlockOxidizer extends BlockWithDirection
{
	public BlockOxidizer()
	{
		super("oxidizer");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && !player.isSneaking())
		{
			player.openGui(ChemiCraft.instance,Constants.G_OXIDIZER, world, x, y, z);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityOxidizer();
	}

}
