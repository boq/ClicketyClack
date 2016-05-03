package openmods.clicky.indicators;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import openmods.clicky.IconContainer.IconHolder;
import openmods.clicky.RenderUtils;

public class StaticIcon implements Renderable {

    private final IconHolder icon;

    public StaticIcon(IconHolder icon) {
        this.icon = icon;
    }

    @Override
    public void render(float partialTick) {
        final TextureAtlasSprite icon = this.icon.get();
        if (icon == null)
            return;

        RenderUtils.renderIcon(icon, 1, 1, 1, 1);
    }

}
