package pome.chemi.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

public class Util
{
	public static void chatToPlayer(EntityPlayer p,String message)
	{
		if(p.worldObj.isRemote)
		{
			return;
		}
		p.addChatMessage(new ChatComponentText(message));
	}
	public static boolean areStacksEqual(ItemStack src,ItemStack cmp)
	{
		if(src == null)
		{
			return true;
		}
		if(cmp == null)
		{
			return false;
		}
		return src.getItem() == cmp.getItem() && src.getItemDamage() == cmp.getItemDamage();
	}
	public static String translate(String source)
	{
		return StatCollector.translateToLocal(source);
	}
	public static int getRelativeOrientation(EntityLivingBase ent)
    {
    	int direction = 0;
		int facing = MathHelper.floor_double(ent.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (facing == 0)
		{
			direction = ForgeDirection.NORTH.ordinal();
		}
		else if (facing == 1)
		{
			direction = ForgeDirection.EAST.ordinal();
		}
		else if (facing == 2)
		{
			direction = ForgeDirection.SOUTH.ordinal();
		}
		else if (facing == 3)
		{
			direction = ForgeDirection.WEST.ordinal();
		}
		return direction;
    }
}
