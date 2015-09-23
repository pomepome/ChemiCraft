package pome.chemi.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pome.chemi.api.Constants;
import pome.chemi.guis.GuiOxidizer;
import pome.chemi.guis.container.ContainerOxidizer;
import pome.chemi.tiles.TileEntityOxidizer;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Constants.G_OXIDIZER)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityOxidizer)
			{
				return new ContainerOxidizer(player.inventory, (TileEntityOxidizer)tile);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Constants.G_OXIDIZER)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityOxidizer)
			{
				return new GuiOxidizer(player.inventory, (TileEntityOxidizer)tile);
			}
		}
		return null;
	}

}
