package openmods.clicky.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigValues {

    public static final String CATEGORY_KEYBOARD = "keyboard";
    public static final String CATEGORY_MOUSE = "mouse";

    public static final String CONFIG_ENABLED = "enabled";

    public final MouseConfig mouse;
    public final KeyboardConfig keyboard;

    public ConfigValues(Configuration config) {
        config.getCategory(CATEGORY_MOUSE).setLanguageKey("openmods.clicky.mouse");
        this.mouse = new MouseConfig(config, CATEGORY_MOUSE, 4, 4, 16);

        config.getCategory(CATEGORY_KEYBOARD).setLanguageKey("openmods.clicky.keyboard");
        this.keyboard = new KeyboardConfig(config, CATEGORY_KEYBOARD, 32, 8, 16);
    }
}
