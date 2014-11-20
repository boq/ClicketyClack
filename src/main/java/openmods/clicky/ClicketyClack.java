package openmods.clicky;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "clicketyclack", name = "ClicketyClack", version = "$VERSION$")
public class ClicketyClack {

    public interface Proxy {
        public void preInit();
    }

    public static class ServerProxy implements Proxy {
        @Override
        public void preInit() {}
    }

    public static class ClientProxy implements Proxy {
        @Override
        public void preInit() {
            final OverlayRenderHandler handler = new OverlayRenderHandler();
            MinecraftForge.EVENT_BUS.register(handler.createForgeListener());
            FMLCommonHandler.instance().bus().register(handler.createFmlListener());
        }
    }

    @SidedProxy(serverSide = "openmods.clicky.ClicketyClack$ServerProxy", clientSide = "openmods.clicky.ClicketyClack$ClientProxy")
    public static Proxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        proxy.preInit();
    }
}
