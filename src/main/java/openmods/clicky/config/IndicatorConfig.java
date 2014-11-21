package openmods.clicky.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class IndicatorConfig {

    public final boolean visible;

    public final int x;

    public final int y;

    public final int size;

    public final int fadeTime;

    public IndicatorConfig(Configuration config, String category) {

        {
            Property prop = config.get(category, "visible", true, "Is mouse state indicator visible");
            prop.setLanguageKey("openmods.clicky.visible");
            visible = prop.getBoolean();
        }

        {
            Property prop = config.get(category, "x", 4, "X coordinate of mouse state indicator");
            prop.setLanguageKey("openmods.clicky.x");
            x = prop.getInt();
        }

        {
            Property prop = config.get(category, "y", 4, "Y coordinate of mouse state indicator");
            prop.setLanguageKey("openmods.clicky.y");
            y = prop.getInt();
        }

        {
            Property prop = config.get(category, "size", 16, "Size of mouse state indicator");
            prop.setLanguageKey("openmods.clicky.size");
            size = prop.getInt();
        }

        {
            Property prop = config.get(category, "fadeTime", 10, "Event fade time (in ticks)");
            prop.setLanguageKey("openmods.clicky.fadeTime");
            fadeTime = prop.getInt();
        }
    }

}
