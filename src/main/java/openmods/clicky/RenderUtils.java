package openmods.clicky;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

    public static void bindTextureToClient(ResourceLocation texture) {
        if (texture != null) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (mc != null) {
                mc.renderEngine.bindTexture(texture);
            }
        }
    }

    public static FontRenderer getFontRenderer() {
        return Minecraft.getMinecraft().fontRenderer;
    }

    public static void bindDefaultItemsTexture() {
        bindTextureToClient(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }

    public static void renderIcon(TextureAtlasSprite icon, float red, float green, float blue, float alpha) {
        GlStateManager.color(red, green, blue, alpha);
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
