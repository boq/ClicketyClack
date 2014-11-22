package openmods.clicky.indicators;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IIcon;
import openmods.clicky.IconContainer.IconHolder;
import openmods.clicky.RenderUtils;

public class SingleKeyIcon extends KeyIcon {

    private final IconHolder icon;

    public SingleKeyIcon(IconHolder icon, String contents, FontRenderer fontRenderer, int decayTime) {
        super(contents, fontRenderer, decayTime);
        this.icon = icon;
    }

    @Override
    public float getWidth() {
        return 1;
    }

    @Override
    protected void renderIcon(float alpha) {
        IIcon icon = this.icon.get();
        if (icon != null) {
            RenderUtils.renderIcon(icon, alpha);
            renderText(alpha);
            RenderUtils.bindDefaultItemsTexture();
        }
    }

}
