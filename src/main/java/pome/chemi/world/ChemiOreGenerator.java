package pome.chemi.world;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import pome.chemi.ChemiCraft;

public class ChemiOreGenerator implements IWorldGenerator {

	public static int convertPos(int chunkPos)
	{
		return chunkPos << 4;
	}
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,IChunkProvider chunkProvider)
	{
		switch(world.provider.dimensionId)
		{
			case 0:
				generateOverworld(world, ChemiCraft.oreSulfur, random, convertPos(chunkX), convertPos(chunkZ),20,20,10);
				generateOverworld(world, ChemiCraft.orePlatinum, random, convertPos(chunkX), convertPos(chunkZ),10,15,2);
				break;
			default: return;
		}
	}
	private void generateOverworld(World w,Block b, Random rand, int chunkX,int chunkZ,int frequency,int max_height,int rarerity)
	{
		for(int i = 0;i < frequency;i++)
		{
			int baseX = chunkX + rand.nextInt(16);
			int baseY = rand.nextInt(max_height);
			int baseZ = chunkZ + rand.nextInt(16);
			new WorldGenMinable(b ,rarerity).generate(w, rand, baseX, baseY, baseZ);
		}
	}

}
