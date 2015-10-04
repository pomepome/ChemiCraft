package pome.chemi.util;

import static net.minecraftforge.common.util.ForgeDirection.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Util
{
	private static Map<ForgeDirection,ForgeDirection> leftList;
	public static void init()
	{
		leftList = new HashMap<ForgeDirection,ForgeDirection>();
		leftList.put(EAST, NORTH);
		leftList.put(NORTH, WEST);
		leftList.put(WEST, SOUTH);
		leftList.put(SOUTH, EAST);
		leftList.put(UNKNOWN, UNKNOWN);
	}
	public static void chatToPlayer(EntityPlayer p,String message)
	{
		if(p.worldObj.isRemote)
		{
			return;
		}
		p.addChatMessage(new ChatComponentText(message));
	}
	public static ItemStack[] copyStacks(ItemStack... stacks)
	{
		ItemStack[] ret = new ItemStack[stacks.length];
		for(int i = 0;i < ret.length;i++)
		{
			ret[i] = copy(stacks[i]);
		}
		return ret;
	}
	public static ItemStack copy(ItemStack stack)
	{
		return stack == null ? null : stack.copy();
	}
	public static ItemStack[] copyStacks(IInventory inv)
	{
		ItemStack[] ret = new ItemStack[inv.getSizeInventory()];
		for(int i = 0;i < ret.length;i++)
		{
			ret[i] = copy(inv.getStackInSlot(i));
		}
		return ret;
	}
	public static boolean rightForRecipe(ItemStack stackSrc,ItemStack stackCmp)
	{
		return areStacksEqual(stackSrc, stackCmp) && stackCmp.stackSize >= stackSrc.stackSize;
	}
	public static boolean areStacksEqual(ItemStack stackA,ItemStack stackB)
	{
		if(stackA == null || stackB == null)
		{
			return false;
		}
		return stackA.getItem() == stackB.getItem() && stackA.getItemDamage() == stackB.getItemDamage();
	}
	public static boolean areStacksEqualRecipe(ItemStack src,ItemStack cmp)
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
	public static ItemStack pushStackInInv(IInventory inv, ItemStack stack, int low_limit)
	{
		for (int i = low_limit; i < inv.getSizeInventory(); i++)
		{
			ItemStack invStack = inv.getStackInSlot(i);

			if (invStack == null)
			{
				inv.setInventorySlotContents(i, stack);
				return null;
			}

			if (areStacksEqual(stack, invStack) && invStack.stackSize < invStack.getMaxStackSize())
			{
				int remaining = invStack.getMaxStackSize() - invStack.stackSize;

				if (remaining >= stack.stackSize)
				{
					invStack.stackSize += stack.stackSize;
					inv.setInventorySlotContents(i, invStack);
					inv.markDirty();
					return null;
				}

				invStack.stackSize += remaining;
				inv.setInventorySlotContents(i, invStack);
				stack.stackSize -= remaining;
			}
		}

		return stack.copy();
	}
	public static ItemStack pushStackInInv(IInventory inv, ItemStack stack)
	{
		int limit;

		if (inv instanceof InventoryPlayer)
		{
			limit = 36;
		}
		else
		{
			limit = inv.getSizeInventory();
		}

		for (int i = 0; i < limit; i++)
		{
			ItemStack invStack = inv.getStackInSlot(i);

			if (invStack == null)
			{
				inv.setInventorySlotContents(i, stack);
				return null;
			}

			if (areStacksEqual(stack, invStack) && invStack.stackSize < invStack.getMaxStackSize())
			{
				int remaining = invStack.getMaxStackSize() - invStack.stackSize;

				if (remaining >= stack.stackSize)
				{
					invStack.stackSize += stack.stackSize;
					inv.setInventorySlotContents(i, invStack);
					return null;
				}

				invStack.stackSize += remaining;
				inv.setInventorySlotContents(i, invStack);
				stack.stackSize -= remaining;
			}
		}

		return stack.copy();
	}
	public static boolean pushStacksInInv(ItemStack[] inv,boolean actuallyPush,ItemStack... stacks)
	{
		int stackLimit = 64;
		if(actuallyPush)
		{
			if(!pushStacksInInv(inv,false,stacks))
			{
				return false;
			}
			for(ItemStack stack : stacks)
			{
				pushStackInInv(inv,stack);
			}
			return true;
		}
		else
		{
			ItemStack[] stackInv = copyStacks(inv);
			for(ItemStack stack : stacks)
			{
				if(pushStackInInv(stackInv,stack) != null)
				{
					return false;
				}
			}
			return true;
		}
	}
	public static boolean pushStacksInInv(IInventory inv,boolean actuallyPush,int low_limit,ItemStack... stacks)
	{
		int stackLimit = inv.getInventoryStackLimit();
		if(actuallyPush)
		{
			if(!pushStacksInInv(inv,false,low_limit,stacks))
			{
				return false;
			}
			for(ItemStack stack : stacks)
			{
				pushStackInInv(inv,stack);
			}
			inv.markDirty();
			return true;
		}
		else
		{
			ItemStack[] stackInv = copyStacks(inv);
			for(ItemStack stack : stacks)
			{
				if(pushStackInInv(stackInv,stack) != null)
				{
					return false;
				}
			}
			return true;
		}
	}
	public static void releaseItems(TileEntity tile,boolean releaseBlock)
	{
		if(tile instanceof IInventory)
		{
			int x = tile.xCoord;
			int y = tile.yCoord;
			int z = tile.zCoord;
			IInventory inv = (IInventory)tile;
			for(int i = 0;i < inv.getSizeInventory();i++)
			{
				ItemStack stack = inv.getStackInSlot(i);
				if(stack != null)
				{
					spawnEntityItem(tile.getWorldObj(), stack, x + 0.5, y + 0.5, z + 0.5);
					inv.setInventorySlotContents(i, null);
				}
			}
			inv.markDirty();
			if(releaseBlock)
			{
				spawnEntityItem(tile.getWorldObj(), new ItemStack(tile.blockType), x + 0.5, y + 0.5, z + 0.5);
			}
		}
	}
	public static void spawnEntityItem(World world, ItemStack stack, double x, double y, double z)
	{
    	float jump = ((float) world.rand.nextGaussian() * 0.05F + 0.2F);
    	spawnEntityItem(world, stack, x, y, z, jump);
	}
    public static void spawnEntityItem(World world, ItemStack stack, double x, double y, double z,float jump)
	{
    	float f = world.rand.nextFloat() * 0.8F + 0.1F;
		float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
		EntityItem entityitem;

		for (float f2 = world.rand.nextFloat() * 0.8F + 0.1F; stack.stackSize > 0; world.spawnEntityInWorld(entityitem))
		{
			int j1 = world.rand.nextInt(21) + 10;

			if (j1 > stack.stackSize)
				j1 = stack.stackSize;

			stack.stackSize -= j1;
			entityitem = new EntityItem(world, (double)((float) x + f), (double)((float) y + f1), (double)((float) z + f2), new ItemStack(stack.getItem(), j1, stack.getItemDamage()));
			float f3 = 0.05F;
			entityitem.motionX = (double)((float) world.rand.nextGaussian() * f3);
			entityitem.motionY = jump;
			entityitem.motionZ = (double)((float) world.rand.nextGaussian() * f3);

			if (stack.hasTagCompound())
			{
				entityitem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
			}
		}
	}
	public static ItemStack pushStackInInv(ItemStack[] inventory,ItemStack stack)
	{
		for (int i = 0; i < inventory.length; i++)
		{
			ItemStack invStack = inventory[i];

			if (invStack == null)
			{
				inventory[i] = stack;
				return null;
			}

			if (areStacksEqual(stack, invStack) && invStack.stackSize < invStack.getMaxStackSize())
			{
				int remaining = invStack.getMaxStackSize() - invStack.stackSize;

				if (remaining >= stack.stackSize)
				{
					invStack.stackSize += stack.stackSize;
					inventory[i] = invStack;
					return null;
				}

				invStack.stackSize += remaining;
				inventory[i] = invStack;
				stack.stackSize -= remaining;
			}
		}

		return stack.copy();
	}
	public static int getRandomInt(Random rand,int low_limit,int high_limit)
	{
		return low_limit + rand.nextInt(high_limit - low_limit + 1);
	}
	public static String getSideName(ForgeDirection dir)
	{
		switch(dir)
		{
			case NORTH : return "NORTH";
			case EAST  : return "EAST";
			case WEST  : return "WEST";
			case SOUTH : return "SOUTH";
			default    : return "UNKNOWN";
		}
	}
	public static ForgeDirection getLeftSide(ForgeDirection side)
	{
		return leftList.get(side);
	}
	public static ForgeDirection getBackSide(ForgeDirection side)
	{
		return getLeftSide(leftList.get(side));
	}
	public static ForgeDirection getRightSide(ForgeDirection side)
	{
		return getLeftSide(getLeftSide(leftList.get(side)));
	}
}
