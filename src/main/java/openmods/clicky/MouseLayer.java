package openmods.clicky;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

public abstract class MouseLayer {

    private final String iconName;

    private IIcon icon;

    public MouseLayer(String iconName) {
        this.iconName = iconName;
    }

    public void registerIcon(IIconRegister registry) {
        this.icon = registry.registerIcon(iconName);
    }

    protected abstract boolean shouldRender();

    public void checkInput() {}

    public void tick() {}

    public void render() {
        if (shouldRender() && icon != null) {
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
}
