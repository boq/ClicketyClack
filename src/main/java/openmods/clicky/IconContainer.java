package openmods.clicky;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class IconContainer {

    public static class IconHolder {
        private final ResourceLocation iconLocation;
        private TextureAtlasSprite icon;

        private IconHolder(ResourceLocation iconLocation) {
            this.iconLocation = iconLocation;
        }

        private void register(TextureMap registry) {
            this.icon = registry.registerSprite(iconLocation);
        }

        public TextureAtlasSprite get() {
            return icon;
        }
    }

    private final Map<ResourceLocation, IconHolder> icons = Maps.newHashMap();

    public IconHolder getHolder(ResourceLocation iconName) {
        IconHolder holder = icons.get(iconName);

        if (holder == null) {
            holder = new IconHolder(iconName);
            icons.put(iconName, holder);
        }

        return holder;
    }

    public void registerIcons(TextureMap textureMap) {
        for (IconHolder layer : icons.values())
            layer.register(textureMap);
    }
}
