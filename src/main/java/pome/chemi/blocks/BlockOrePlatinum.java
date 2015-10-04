package pome.chemi.blocks;

public class BlockOrePlatinum extends BlockBase
{

	public BlockOrePlatinum()
	{
		super("platinum");
		this.setBlockTextureName("chemi:ore/platinum_ore").setHardness(20).setHarvestLevel("pickaxe", 2);
	}

}
