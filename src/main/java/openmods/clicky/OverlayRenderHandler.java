package openmods.clicky;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class OverlayRenderHandler {

    public class ForgeListener {
        @SubscribeEvent
        public void registerIcons(TextureStitchEvent.Pre evt) {
            final TextureMap map = evt.map;
            if (map.getTextureType() == 1) {
                for (MouseLayer layer : mouseLayers)
                    layer.registerIcon(map);
            }
        }

        @SubscribeEvent
        public void renderOverlay(RenderGameOverlayEvent.Post evt) {
            if (!Config.mouseEventVisible)
                return;

            if (evt.type == ElementType.HOTBAR) {
                GL11.glColor3f(1, 1, 1);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                bindDefaultItemsTexture();
                GL11.glPushMatrix();
                GL11.glTranslated(Config.mouseEventX, Config.mouseEventY, 0);
                GL11.glScaled(Config.mouseEventScale, Config.mouseEventScale, 1);

                for (MouseLayer layer : mouseLayers)
                    layer.render();

                GL11.glPopMatrix();
            }
        }
    }

    public class FmlListener {
        @SubscribeEvent
        public void mouseEvent(MouseInputEvent evt) {
            for (MouseLayer layer : mouseLayers)
                layer.checkInput();
        }

        @SubscribeEvent
        public void onTick(ClientTickEvent evt) {
            if (evt.phase == Phase.START)
                for (MouseLayer layer : mouseLayers)
                    layer.tick();
        }
    }

    private final List<MouseLayer> mouseLayers = Lists.newArrayList();

    {
        mouseLayers.add(new MouseLayer("clicketyclack:mouse_background") {
            @Override
            protected boolean shouldRender() {
                return true;
            }
        });

        mouseLayers.add(new TickingMouseLayer("clicketyclack:mouse_left") {
            @Override
            protected boolean checkTrigger() {
                return Mouse.getEventButton() == 0;
            }
        });

        mouseLayers.add(new TickingMouseLayer("clicketyclack:mouse_right") {
            @Override
            protected boolean checkTrigger() {
                return Mouse.getEventButton() == 1;
            }
        });

        mouseLayers.add(new TickingMouseLayer("clicketyclack:mouse_middle") {
            @Override
            protected boolean checkTrigger() {
                return Mouse.getEventButton() == 2;
            }
        });

        mouseLayers.add(new TickingMouseLayer("clicketyclack:mouse_up") {
            @Override
            protected boolean checkTrigger() {
                return Mouse.getEventDWheel() > 0;
            }
        });

        mouseLayers.add(new TickingMouseLayer("clicketyclack:mouse_down") {
            @Override
            protected boolean checkTrigger() {
                return Mouse.getEventDWheel() < 0;
            }
        });
    }

    public Object createFmlListener() {
        return new FmlListener();
    }

    public Object createForgeListener() {
        return new ForgeListener();
    }

    public static void bindTextureToClient(ResourceLocation texture) {
        if (texture != null) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (mc != null) {
                mc.renderEngine.bindTexture(texture);
            }
        }
    }

    public static void bindDefaultItemsTexture() {
        bindTextureToClient(TextureMap.locationItemsTexture);
    }
}
