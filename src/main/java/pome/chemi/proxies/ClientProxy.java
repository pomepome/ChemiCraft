package pome.chemi.proxies;

import cpw.mods.fml.common.registry.GameRegistry;
import pome.chemi.tiles.TileEntityOxidizer;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityOxidizer.class, "Oxidizer");
	}
}
