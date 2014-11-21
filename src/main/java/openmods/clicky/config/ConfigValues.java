package openmods.clicky.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigValues {

    public static final String CATEGORY_KEYBOARD = "keyboard";
    public static final String CATEGORY_MOUSE = "mouse";

    public final IndicatorConfig mouse;
    public final IndicatorConfig keyboard;

    public ConfigValues(Configuration config) {
        config.getCategory(CATEGORY_MOUSE).setLanguageKey("openmods.clicky.mouse");
        this.mouse = new IndicatorConfig(config, CATEGORY_MOUSE);

        config.getCategory(CATEGORY_KEYBOARD).setLanguageKey("openmods.clicky.keyboard");
        this.keyboard = new IndicatorConfig(config, CATEGORY_KEYBOARD);
    }
}
