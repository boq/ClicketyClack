package openmods.clicky.indicators;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import openmods.clicky.IconContainer.IconHolder;
import openmods.clicky.RenderUtils;
import org.lwjgl.opengl.GL11;

public class WideKeyIcon extends KeyIcon {

    private final IconHolder[] holders;

    public WideKeyIcon(String contents, FontRenderer fontRenderer, int decayTime, int blinkTime, IconHolder... holders) {
        super(contents, fontRenderer, decayTime, blinkTime);
        this.holders = holders;
    }

    @Override
    public float getWidth() {
        return holders.length;
    }

    @Override
    protected void renderIcon(float partialTick, float alpha, float blink) {
        GL11.glPushMatrix();
        for (IconHolder holder : holders) {
            TextureAtlasSprite icon = holder.get();
            if (icon != null)
                RenderUtils.renderIcon(icon, 1, 1, 1, alpha);
            GL11.glTranslatef(1, 0, 0);
        }
        GL11.glPopMatrix();

        renderText(blink, alpha);
        RenderUtils.bindDefaultItemsTexture();

    }

}
