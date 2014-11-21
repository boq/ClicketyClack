package openmods.clicky;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class Utils {
    public static void bindTextureToClient(ResourceLocation texture) {
        if (texture != null) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (mc != null) {
                mc.renderEngine.bindTexture(texture);
            }
        }
    }

    public static void bindDefaultItemsTexture() {
        bindTextureToClient(TextureMap.locationItemsTexture);
    }
}
