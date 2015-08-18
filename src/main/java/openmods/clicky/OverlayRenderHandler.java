package openmods.clicky;

import net.minecraft.client.Minecraft;
import openmods.clicky.GuiInputEvent.GuiKeyInputEvent;
import openmods.clicky.GuiInputEvent.GuiMouseInputEvent;
import openmods.clicky.config.*;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class OverlayRenderHandler {

    private final IconContainer icons = new IconContainer();

    private MouseIndicators mouseIndicators;

    private KeyboardIndicators keyboardIndicators;

    public OverlayRenderHandler(ConfigValues config) {
        init(config);
    }

    private static IndicatorPosition createPosition(Minecraft minecraft, IndicatorConfig config) {
        return new IndicatorPosition(minecraft, config.dx, config.dy, config.vertical, config.horizontal);
    }

    public void init(ConfigValues values) {
        Minecraft mc = Minecraft.getMinecraft();

        final MouseConfig mouse = values.mouse;
        mouseIndicators = mouse.visible ? new MouseIndicators(icons, createPosition(mc, mouse), mouse.size, mouse.fadeTime, mouse.wheelEventDuration, mouse.wheelThreshold) : null;

        final KeyboardConfig keyboard = values.keyboard;
        final IKeyFilter filter = values.keyFilter;
        keyboardIndicators = keyboard.visible ? new KeyboardIndicators(icons, createPosition(mc, keyboard), filter, keyboard.size, keyboard.fadeTime, keyboard.blinkTime) : null;
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
        public void keyEvent(KeyInputEvent evt) {
            if (keyboardIndicators != null)
                keyboardIndicators.keyEvent();
        }

        @SubscribeEvent
        public void keyEvent(GuiKeyInputEvent evt) {
            if (keyboardIndicators != null)
                keyboardIndicators.keyEvent();
        }

        @SubscribeEvent
        public void onTick(ClientTickEvent evt) {
            if (mouseIndicators != null)
                mouseIndicators.tick();

            if (keyboardIndicators != null)
                keyboardIndicators.tick();
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

                if (keyboardIndicators != null)
                    keyboardIndicators.render(evt.renderTickTime);
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

    public void toggleMouse() {
        if (mouseIndicators != null)
            mouseIndicators.toggle();
    }

    public void toggleKeyboard() {
        if (keyboardIndicators != null)
            keyboardIndicators.toggle();
    }

}
