package openmods.clicky;

import java.io.File;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import openmods.clicky.config.ConfigValues;
import openmods.clicky.config.KeyBindingHandler;

@Mod(modid = ClicketyClack.MOD_ID, name = ClicketyClack.MOD_NAME, version = "$VERSION$", guiFactory = "openmods.clicky.ConfigGuiFactory")
public class ClicketyClack {

    public static final String MOD_NAME = "ClicketyClack";
    public static final String MOD_ID = "clicketyclack";

    @Instance
    public static ClicketyClack instance;

    private Configuration config;
    private OverlayRenderHandler handler;

    public static ResourceLocation location(String resource) {
        return new ResourceLocation(MOD_ID, resource);
    }

    public interface Proxy {
        public void preInit(File configFile);
    }

    public static class ServerProxy implements Proxy {
        @Override
        public void preInit(File configFile) {}
    }

    public static class ConfigListener {
        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent evt) {
            final Configuration config = instance.config;
            ConfigValues values = new ConfigValues(config);
            if (config.hasChanged())
                config.save();

            instance.handler.init(values);
        }
    }

    public static class ClientProxy implements Proxy {
        @Override
        public void preInit(File configFile) {
            final Configuration config = instance.config = new Configuration(configFile);
            ConfigValues values = new ConfigValues(config);
            if (config.hasChanged())
                config.save();

            final OverlayRenderHandler handler = instance.handler = new OverlayRenderHandler(values);
            MinecraftForge.EVENT_BUS.register(handler);
            MinecraftForge.EVENT_BUS.register(new ConfigListener());

            MinecraftForge.EVENT_BUS.register(new KeyBindingHandler(handler));
        }
    }

    public Configuration config() {
        return config;
    }

    @SidedProxy(serverSide = "openmods.clicky.ClicketyClack$ServerProxy", clientSide = "openmods.clicky.ClicketyClack$ClientProxy")
    public static Proxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        proxy.preInit(evt.getSuggestedConfigurationFile());
    }
}
