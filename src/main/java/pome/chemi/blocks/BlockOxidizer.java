package pome.chemi.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import pome.chemi.ChemiCraft;
import pome.chemi.api.Constants;
import pome.chemi.tiles.TileEntityOxidizer;
import pome.chemi.util.Util;

public class BlockOxidizer extends BlockWithDirection
{
	IIcon front, input, none, output;
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

	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(meta == 0)
		{
			if(side < 2)
			{
				return none;
			}
			if(side == 3)
			{
				return front;
			}
			return input;
		}
		if(side < 2)
		{
			return none;
		}
		else if (side == meta)
		{
			return front;
		}
		return getSideIcon(side,meta);
    }
	private IIcon getSideIcon(int side,int meta)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(meta);
		ForgeDirection dir2 = ForgeDirection.getOrientation(side);
		if(Util.getBackSide(dir) == dir2)
		{
			return output;
		}
		return input;
	}
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		front = reg.registerIcon("chemi:oxidizer/oxidizer_front");
		input = reg.registerIcon("chemi:oxidizer/oxidizer_input");
		output = reg.registerIcon("chemi:oxidizer/oxidizer_output");
		none = reg.registerIcon("chemi:oxidizer/oxidizer_none");
    }

}
