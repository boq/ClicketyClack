package openmods.clicky.indicators;

import net.minecraft.util.IIcon;
import openmods.clicky.IconContainer.IconHolder;

import org.lwjgl.opengl.GL11;

public abstract class RenderableIcon implements Renderable {
    private final IconHolder icon;

    public RenderableIcon(IconHolder icon) {
        this.icon = icon;
    }

    protected abstract boolean shouldRender();

    protected abstract float alpha(float partialTick);

    @Override
    public void render(float partialTick) {
        if (!shouldRender())
            return;

        final IIcon icon = this.icon.get();
        if (icon == null)
            return;

        GL11.glColor4f(1, 1, 1, alpha(partialTick));
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(icon.getMinU(), icon.getMinV());
        GL11.glVertex2i(0, 0);

        GL11.glTexCoord2f(icon.getMinU(), icon.getMaxV());
        GL11.glVertex2i(0, 1);

        GL11.glTexCoord2f(icon.getMaxU(), icon.getMaxV());
        GL11.glVertex2i(1, 1);

        GL11.glTexCoord2f(icon.getMaxU(), icon.getMinV());
        GL11.glVertex2i(1, 0);

        GL11.glEnd();
    }
}
