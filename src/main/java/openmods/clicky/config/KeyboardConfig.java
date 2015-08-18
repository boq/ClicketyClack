package openmods.clicky.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class KeyboardConfig extends IndicatorConfig {

    public final int blinkTime;

    public KeyboardConfig(Configuration config, String category, int defaultX, int defaultY, int defaultSize) {
        super(config, category, defaultX, defaultY, defaultSize);

        {
            Property prop = config.get(category, "blinkTime", 2, "Event blink time (in ticks) after key repeat");
            prop.setLanguageKey("openmods.clicky.blink_time");
            prop.setMinValue(0);
            blinkTime = Math.max(0, prop.getInt());
        }
    }

}
