package openmods.clicky;

import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import openmods.clicky.config.ConfigValues;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

@SuppressWarnings({ "rawtypes" })
public class ConfigGuiFactory implements IModGuiFactory {

    public static class ConfigScreen extends GuiConfig {

        public ConfigScreen(GuiScreen parent) {
            super(parent, createConfigElements(), ClicketyClack.MOD_ID, false, false, ClicketyClack.MOD_NAME);
        }

        private static List<IConfigElement> createConfigElements() {
            List<IConfigElement> result = Lists.newArrayList();

            ConfigCategory mouseCategory = ClicketyClack.instance.config().getCategory(ConfigValues.CATEGORY_MOUSE);
            result.add(new ConfigElement(mouseCategory));

            ConfigCategory keyboardCategory = ClicketyClack.instance.config().getCategory(ConfigValues.CATEGORY_KEYBOARD);
            result.add(new ConfigElement(keyboardCategory));

            return result;
        }
    }

    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ConfigScreen.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return ImmutableSet.of();
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

}
