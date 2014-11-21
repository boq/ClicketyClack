package openmods.clicky;

import openmods.clicky.GuiInputEvent.GuiMouseInputEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class OverlayRenderHandler {

    private final IconContainer icons = new IconContainer();

    private MouseIndicators mouseIndicators;

    public OverlayRenderHandler() {
        mouseIndicators = new MouseIndicators(icons, 16, 16, 16, 20);
    }

    public class FmlListener {
        @SubscribeEvent
        public void mouseEvent(GuiMouseInputEvent evt) {
            if (mouseIndicators != null)
                mouseIndicators.mouseEvent();
        }

        @SubscribeEvent
        public void mouseEvent(MouseInputEvent evt) {
            if (mouseIndicators != null)
                mouseIndicators.mouseEvent();
        }

        @SubscribeEvent
        public void onTick(ClientTickEvent evt) {
            if (mouseIndicators != null)
                mouseIndicators.tick();
        }

        @SubscribeEvent
        public void onRender(RenderTickEvent evt) {
            if (evt.phase == Phase.END) {
                GL11.glColor3f(1, 1, 1);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                if (mouseIndicators != null)
                    mouseIndicators.render(evt.renderTickTime);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }
    }

    public Object createFmlListener() {
        return new FmlListener();
    }

    public Object getStitchListener() {
        return icons;
    }

}
