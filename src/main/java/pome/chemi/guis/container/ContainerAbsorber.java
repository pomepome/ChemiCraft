package pome.chemi.guis.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import pome.chemi.guis.slot.SlotChemical;
import pome.chemi.guis.slot.SlotResult;
import pome.chemi.items.ItemChemicals;
import pome.chemi.tiles.TileEntityAbsorber;

public class ContainerAbsorber extends Container
{
	TileEntityAbsorber tile;

	int lastProcess;

	public ContainerAbsorber(InventoryPlayer p,TileEntityAbsorber tile)
	{
		this.tile = tile;
        this.addSlotToContainer(new SlotChemical(tile, 0, 56, 17));
        this.addSlotToContainer(new SlotChemical(tile, 1, 56, 53));
        this.addSlotToContainer(new SlotResult(tile, 2, 106, 17));
        this.addSlotToContainer(new SlotResult(tile, 3, 137, 17));
        this.addSlotToContainer(new SlotResult(tile, 4, 106, 52));
        this.addSlotToContainer(new SlotResult(tile, 5, 137, 52));
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(p, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(p, i, 8 + i * 18, 142));
        }
	}
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	@Override
	public void addCraftingToCrafters(ICrafting crafting)
    {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, tile.processTime);
    }
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		Slot slot = this.getSlot(slotIndex);

		if (slot == null || !slot.getHasStack())
		{
			return null;
		}

		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();

		if (slotIndex > 5&&slotIndex <= 32)
		{
			//player main inventory shift-clicked
			if(stack.getItem() instanceof ItemChemicals)
			{
				if (!this.mergeItemStack(stack, 0,2, false))
				{
					return null;
				}
			}
			else
			{
				if (!this.mergeItemStack(stack,33,41, false))
				{
					return null;
				}
			}
		}
		else if(slotIndex > 32)
		{
			//hotbar shift-clicked
			if(stack.getItem() instanceof ItemChemicals)
			{
				if (!this.mergeItemStack(stack, 0,2, false))
				{
					return null;
				}
			}
			else
			{
				if (!this.mergeItemStack(stack,6,32, false))
				{
					return null;
				}
			}
		}
		else
		{
			if (!this.mergeItemStack(stack,33,this.inventorySlots.size(), false))
			{
				return null;
			}
		}
		if (stack.stackSize == 0)
		{
			slot.putStack(null);
		}

		else slot.onSlotChanged();
		slot.onPickupFromSlot(player, stack);
		return newStack;
	}
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastProcess != this.tile.processTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tile.processTime);
            }
        }

        this.lastProcess = this.tile.processTime;
    }
	@Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int ID, int value)
    {
        if (ID == 0)
        {
            this.tile.processTime = value;
        }
    }
}
