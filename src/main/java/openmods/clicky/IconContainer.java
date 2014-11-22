package openmods.clicky;

import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class IconContainer {

    public static class IconHolder {
        private final String iconName;
        private IIcon icon;

        private IconHolder(String iconName) {
            this.iconName = iconName;
        }

        private void register(IIconRegister registry) {
            this.icon = registry.registerIcon(iconName);
        }

        public IIcon get() {
            return icon;
        }
    }

    private final Map<String, IconHolder> icons = Maps.newHashMap();

    public IconHolder getHolder(String iconName) {
        IconHolder holder = icons.get(iconName);

        if (holder == null) {
            holder = new IconHolder(iconName);
            icons.put(iconName, holder);
        }

        return holder;
    }

    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre evt) {
        final TextureMap map = evt.map;
        if (map.getTextureType() == 1) {
            for (IconHolder layer : icons.values())
                layer.register(map);
        }
    }
}
