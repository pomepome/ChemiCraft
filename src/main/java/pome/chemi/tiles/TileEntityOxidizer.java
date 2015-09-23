package pome.chemi.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import pome.chemi.api.EnumRecipeType;
import pome.chemi.recipes.RecipesRegistry;

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
		inventory = new ItemStack[3];
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound subNBT = list.getCompoundTagAt(i);
			byte slot = subNBT.getByte("Slot");

			if (slot >= 0 && slot < 3)
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
		for (int i = 0; i < 3; i++)
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
		return 3;
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
		ItemStack dest = RecipesRegistry.getDestFromStacks(EnumRecipeType.OXIDIZER, inventory[0], inventory[1]);
		if(dest != null)
		{
			int i = 0;
			if(inventory[2] != null)
			{
				if(inventory[2].getItem() != dest.getItem())
				{
					return false;
				}
				i = inventory[2].stackSize;
			}
			if(dest.stackSize + i <= 64)
			{
				return true;
			}
		}
		return false;
	}
	public void process()
	{
		if(!canProcess())
		{
			return;
		}
		ItemStack dest = RecipesRegistry.getDestFromStacks(EnumRecipeType.OXIDIZER, inventory[0], inventory[1]);
		inventory[0].stackSize -= 1;
		if(inventory[0].stackSize <= 0)
		{
			inventory[0] = null;
		}
		if(inventory[2] == null)
		{
			inventory[2] = dest.copy();
		}
		else
		{
			inventory[2].stackSize += dest.stackSize;
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
			if(processTime == 200)
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
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int p_102007_3_)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
	}

}
