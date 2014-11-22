package openmods.clicky.indicators;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IIcon;
import openmods.clicky.IconContainer.IconHolder;
import openmods.clicky.RenderUtils;

import org.lwjgl.opengl.GL11;

public class WideKeyIcon extends KeyIcon {

    private final IconHolder[] holders;

    public WideKeyIcon(String contents, FontRenderer fontRenderer, int decayTime, IconHolder... holders) {
        super(contents, fontRenderer, decayTime);
        this.holders = holders;
    }

    @Override
    public float getWidth() {
        return holders.length;
    }

    @Override
    protected void renderIcon(float alpha) {
        GL11.glPushMatrix();
        for (IconHolder holder : holders) {
            IIcon icon = holder.get();
            if (icon != null)
                RenderUtils.renderIcon(icon, alpha);
            GL11.glTranslatef(1, 0, 0);
        }
        GL11.glPopMatrix();

        renderText(alpha);
        RenderUtils.bindDefaultItemsTexture();

    }

}
