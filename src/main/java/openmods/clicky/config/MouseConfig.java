package openmods.clicky.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class MouseConfig extends IndicatorConfig {

    public final int wheelThreshold;

    public final int wheelEventDuration;

    public MouseConfig(Configuration config, String category, int defaultX, int defaultY, int defaultSize) {
        super(config, category, defaultX, defaultY, defaultSize);

        {
            Property prop = config.get(category, "wheelThreshold", 10, "Threshold of wheel movement neede to trigger display");
            prop.setLanguageKey("openmods.clicky.wheel_threshold");
            prop.setMinValue(1);
            wheelThreshold = Math.max(1, prop.getInt());
        }

        {
            Property prop = config.get(category, "wheelEventDuration", 10, "Number of tick before wheel event starts decaying");
            prop.setLanguageKey("openmods.clicky.wheel_event_duration");
            prop.setMinValue(0);
            wheelEventDuration = Math.max(1, prop.getInt());
        }
    }

}
