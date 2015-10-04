package pome.chemi.guis;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import pome.chemi.guis.container.ContainerAbsorber;
import pome.chemi.tiles.TileEntityAbsorber;

public class GuiAbsorber extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation("chemi","textures/gui/absorber.png");

	TileEntityAbsorber tile;

	public GuiAbsorber(InventoryPlayer p,TileEntityAbsorber tileOxidizer)
	{
		super(new ContainerAbsorber(p,tileOxidizer));
		tile = tileOxidizer;
	}

	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
		String s = StatCollector.translateToLocal("container.absorber");
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        if(tile.canProcess())
        {
        	this.drawTexturedModalRect(k + 56, l + 36 + 12 - 13, 176, 12 - 13, 14, 13 + 1);
        	int i1 = this.tile.getProcessScaled(24);
            this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        }
        String s = tile.getProcessPercent() + "%";
        fontRendererObj.drawString(s, k + 80, l + 50, 42170752);
	}
}
