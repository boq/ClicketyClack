package openmods.clicky.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigValues {

    public static final String CATEGORY_KEYBOARD = "keyboard";
    public static final String CATEGORY_MOUSE = "mouse";
    public static final String CATEGORY_KEY_FILTER = "key_filter";

    public static final String CONFIG_ENABLED = "enabled";

    public final MouseConfig mouse;
    public final KeyboardConfig keyboard;
    public final KeyFilterConfig keyFilter;

    public ConfigValues(Configuration config) {
        setupCategory(config, CATEGORY_MOUSE, "openmods.clicky.mouse");
        setupCategory(config, CATEGORY_KEYBOARD, "openmods.clicky.keyboard");
        setupCategory(config, CATEGORY_KEY_FILTER, "openmods.clicky.key_filter");

        this.mouse = new MouseConfig(config, CATEGORY_MOUSE, 4, 4, 16);
        this.keyboard = new KeyboardConfig(config, CATEGORY_KEYBOARD, 32, 8, 16);
        this.keyFilter = new KeyFilterConfig(config);
    }

    protected void setupCategory(Configuration config, final String name, final String languageKey) {
        config.getCategory(name).setLanguageKey(languageKey);
    }
}
