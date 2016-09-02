package openmods.clicky.config;

import com.google.common.collect.Lists;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import java.util.Arrays;
import java.util.List;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;
import openmods.clicky.IKeyFilter;
import org.lwjgl.input.Keyboard;

public class KeyFilterConfig implements IKeyFilter {

    public static final String KEY_KEYS = "keys";

    private static class FlagArrayElement extends DummyConfigElement {

        private final Property property;
        private final int keyCode;
        private final TIntSet config;

        public FlagArrayElement(Property property, TIntSet config, int keyCode, String keyName) {
            super(keyName, config.contains(keyCode), ConfigGuiType.BOOLEAN, "");
            this.property = property;
            this.config = config;
            this.keyCode = keyCode;
        }

        @Override
        public void set(Object value) {
            super.set(value);

            if (value == Boolean.TRUE)
                config.add(keyCode);
            else
                config.remove(keyCode);

            final int[] filter = config.toArray();
            Arrays.sort(filter);
            property.set(filter);
        }
    }

    private static Property getProperty(Configuration config) {
        return config.get(ConfigValues.CATEGORY_KEY_FILTER, KEY_KEYS, new int[0], "List of key codes to be ignored (see http://minecraft.gamepedia.com/Key_codes for list)");
    }

    public static IConfigElement createConfigurationCategory(Configuration config) {
        final Property property = getProperty(config);

        final TIntSet values = new TIntHashSet(property.getIntList());

        final List<IConfigElement> filterList = Lists.newArrayList();

        for (int keyCode = 0; keyCode < Keyboard.KEYBOARD_SIZE; keyCode++) {
            final String keyName = Keyboard.getKeyName(keyCode);
            if (keyName != null)
                filterList.add(new FlagArrayElement(property, values, keyCode, keyName));
        }

        final ConfigCategory category = config.getCategory(ConfigValues.CATEGORY_KEY_FILTER);
        return new DummyCategoryElement(category.getName(), category.getLanguagekey(), filterList);
    }

    private final TIntHashSet filteredKeys;

    public KeyFilterConfig(Configuration config) {
        final Property property = getProperty(config);

        filteredKeys = new TIntHashSet(property.getIntList());
    }

    @Override
    public boolean isKeyFiltered(int keyCode) {
        return filteredKeys.contains(keyCode);
    }
}
