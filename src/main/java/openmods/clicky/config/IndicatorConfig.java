package openmods.clicky.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class IndicatorConfig {

    private static String[] enumToString(Enum<?>[] values) {
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++)
            result[i] = values[i].name();

        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>> T getPropValue(Property prop, T defaultValue) {
        final T[] values = (T[])defaultValue.getClass().getEnumConstants();

        prop.setValidValues(enumToString(values));
        String value = prop.getString();

        try {
            return (T)Enum.valueOf(defaultValue.getClass(), value);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public enum Horizontal {
        LEFT, MIDDLE, RIGHT
    }

    public enum Vertical {
        TOP, MIDDLE, BOTTOM
    }

    public final boolean visible;

    public final int dx;

    public final int dy;

    public final int size;

    public final int fadeTime;

    public final Horizontal horizontal;

    public final Vertical vertical;

    public IndicatorConfig(Configuration config, String category, int defaultX, int defaultY, int defaultSize) {

        {
            Property prop = config.get(category, "visible", true, "Is mouse state indicator visible");
            prop.setLanguageKey("openmods.clicky.visible");
            visible = prop.getBoolean();
        }

        {
            Property prop = config.get(category, "vertical", Vertical.TOP.name(), "Vertical anchor point");
            prop.setLanguageKey("openmods.clicky.vertical");
            vertical = getPropValue(prop, Vertical.TOP);
        }

        {
            Property prop = config.get(category, "horizontal", Horizontal.LEFT.name(), "Horizontal anchor point");
            prop.setLanguageKey("openmods.clicky.horizontal");
            horizontal = getPropValue(prop, Horizontal.LEFT);
        }

        {
            Property prop = config.get(category, "dx", defaultX, "Horizontal distance to anchor point");
            prop.setLanguageKey("openmods.clicky.dx");
            dx = prop.getInt();
        }

        {
            Property prop = config.get(category, "dy", defaultY, "Vertical distance to anchor point");
            prop.setLanguageKey("openmods.clicky.dy");
            dy = prop.getInt();
        }

        {
            Property prop = config.get(category, "size", defaultSize, "Size of mouse state indicator");
            prop.setLanguageKey("openmods.clicky.size");
            prop.setMinValue(1);
            size = Math.max(1, prop.getInt());
        }

        {
            Property prop = config.get(category, "fadeTime", 10, "Event fade time (in ticks)");
            prop.setLanguageKey("openmods.clicky.fade_time");
            prop.setMinValue(0);
            fadeTime = Math.max(0, prop.getInt());
        }
    }

}
