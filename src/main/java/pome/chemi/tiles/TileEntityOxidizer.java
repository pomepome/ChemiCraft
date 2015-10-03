package pome.chemi.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import pome.chemi.api.EnumRecipeType;
import pome.chemi.api.IChemiRecipe;
import pome.chemi.items.ItemChemicals;
import pome.chemi.recipes.RecipesRegistry;
import pome.chemi.util.Util;

public class TileEntityOxidizer extends TileEntity implements ISidedInventory
{
	ItemStack[] inventory;

	public int processTime;

	public TileEntityOxidizer()
	{
		inventory = new ItemStack[getSizeInventory()];
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		NBTTagList list = nbt.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound subNBT = list.getCompoundTagAt(i);
			byte slot = subNBT.getByte("Slot");

			if (slot >= 0 && slot < 6)
			{
				inventory[slot] = ItemStack.loadItemStackFromNBT(subNBT);
			}
		}
		processTime = nbt.getInteger("procTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("procTime", processTime);

		NBTTagList list = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); i++)
		{
			if (inventory[i] == null)
			{
				continue;
			}

			NBTTagCompound subNBT = new NBTTagCompound();
			subNBT.setByte("Slot", (byte) i);
			inventory[i].writeToNBT(subNBT);
			list.appendTag(subNBT);
		}

		nbt.setTag("Items", list);
	}
	@Override
	public int getSizeInventory()
	{
		return 6;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}

	@Override
    public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = inventory[slot];
		if(stack != null)
		{
			if(stack.stackSize <= amount)
			{
				inventory[slot] = null;
			}
			else
			{
				stack = stack.splitStack(amount);
				if(stack.stackSize == 0)
				{
					inventory[slot] = null;
				}
			}
		}
		return stack;
    }

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if(inventory[slot] != null)
		{
			ItemStack is = inventory[slot];
			inventory[slot] = null;
			return is;
		}
		return null;
	}

	public boolean canProcess()
	{
		IChemiRecipe recipe = RecipesRegistry.getRecipeFromStacks(EnumRecipeType.OXIDIZER, inventory[0], inventory[1]);
		if(recipe != null)
		{
			if(recipe.needFire() && worldObj.getBlock(xCoord, yCoord - 1, zCoord) != Blocks.fire)
			{
				return false;
			}
			return Util.pushStacksInInv(this, false, 2,recipe.getDests());
		}
		return false;
	}
	public void process()
	{
		if(!canProcess())
		{
			return;
		}
		IChemiRecipe recipe = RecipesRegistry.getRecipeFromStacks(EnumRecipeType.OXIDIZER, inventory[0], inventory[1]);
		if(recipe != null)
		{
			ItemStack oxidized = recipe.getDests()[0];
			inventory[0].stackSize -= oxidized.stackSize;
			if(inventory[0].stackSize <= 0)
			{
				inventory[0] = null;
			}
			ItemStack[] stackInv = new ItemStack[]{Util.copy(inventory[2]),Util.copy(inventory[3]),Util.copy(inventory[4]),Util.copy(inventory[5])};
			Util.pushStacksInInv(stackInv, true, recipe.getDests());
			for(int i = 0;i < stackInv.length;i++)
			{
				inventory[i + 2] = Util.copy(stackInv[i]);
			}
		}
		markDirty();
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[slot] = stack;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(worldObj.isRemote)
		{
			return;
		}
		if(canProcess())
		{
			processTime++;
			if(processTime == 50)
			{
				processTime = 0;
				process();
			}
		}
		else
		{
			processTime = 0;
		}
	}

	public int getProcessPercent()
    {
        return this.processTime / 2;
    }
	public int getProcessScaled(int size)
    {
        return this.processTime * size / 200;
    }

	@Override
	public String getInventoryName()
	{
		return "Oxidizer";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return stack.getItem() instanceof ItemChemicals;
	}

	private int getMetadata()
	{
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		ForgeDirection dirFront = ForgeDirection.getOrientation(getMetadata());
		ForgeDirection dirBack = Util.getBackSide(dirFront);
		if(dir.ordinal() == dirFront.ordinal() || dir == ForgeDirection.UP || dir == ForgeDirection.DOWN)
		{
			return new int[]{};
		}
		if(dir.ordinal() == dirBack.ordinal())
		{
			return new int[]{2,3,4,5};
		}
		return new int[]{0,1};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int p_102007_3_)
	{
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return true;
	}

}
