package pome.chemi.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pome.chemi.Constants;
import pome.chemi.guis.GuiAbsorber;
import pome.chemi.guis.GuiOxidizer;
import pome.chemi.guis.container.ContainerAbsorber;
import pome.chemi.guis.container.ContainerOxidizer;
import pome.chemi.tiles.TileEntityAbsorber;
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
		if(ID == Constants.G_ABSORBER)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityAbsorber)
			{
				return new ContainerAbsorber(player.inventory, (TileEntityAbsorber)tile);
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
		if(ID == Constants.G_ABSORBER)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityAbsorber)
			{
				return new GuiAbsorber(player.inventory, (TileEntityAbsorber)tile);
			}
		}
		return null;
	}

}
