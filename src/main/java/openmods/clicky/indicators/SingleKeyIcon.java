package openmods.clicky.indicators;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import openmods.clicky.IconContainer.IconHolder;
import openmods.clicky.RenderUtils;

public class SingleKeyIcon extends KeyIcon {

    private final IconHolder icon;

    public SingleKeyIcon(IconHolder icon, String contents, FontRenderer fontRenderer, int decayTime, int blinkTime) {
        super(contents, fontRenderer, decayTime, blinkTime);
        this.icon = icon;
    }

    @Override
    public float getWidth() {
        return 1;
    }

    @Override
    protected void renderIcon(float partialTick, float alpha, float blink) {
        TextureAtlasSprite icon = this.icon.get();
        if (icon != null) {
            RenderUtils.renderIcon(icon, 1, 1, 1, alpha);
            renderText(blink, alpha);
            RenderUtils.bindDefaultItemsTexture();
        }
    }

}
