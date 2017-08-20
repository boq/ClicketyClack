package openmods.clicky;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import openmods.clicky.config.ConfigValues;
import openmods.clicky.config.KeyFilterConfig;

public class ConfigGuiFactory implements IModGuiFactory {

    public static class ConfigScreen extends GuiConfig {

        public ConfigScreen(GuiScreen parent) {
            super(parent, createConfigElements(), ClicketyClack.MOD_ID, false, false, ClicketyClack.MOD_NAME);
        }

        private static List<IConfigElement> createConfigElements() {
            List<IConfigElement> result = Lists.newArrayList();

            final Configuration config = ClicketyClack.instance.config();
            ConfigCategory mouseCategory = config.getCategory(ConfigValues.CATEGORY_MOUSE);
            result.add(new ConfigElement(mouseCategory));

            ConfigCategory keyboardCategory = config.getCategory(ConfigValues.CATEGORY_KEYBOARD);
            result.add(new ConfigElement(keyboardCategory));

            result.add(KeyFilterConfig.createConfigurationCategory(config));

            return result;
        }
    }

    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return ImmutableSet.of();
    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ConfigScreen(parentScreen);
    }

}
