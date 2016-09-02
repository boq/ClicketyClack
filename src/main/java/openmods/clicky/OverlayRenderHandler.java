package openmods.clicky;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import openmods.clicky.config.ConfigValues;
import openmods.clicky.config.IndicatorConfig;
import openmods.clicky.config.KeyboardConfig;
import openmods.clicky.config.MouseConfig;
import org.lwjgl.opengl.GL11;

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

    @SubscribeEvent
    public void mouseEvent(InputEvent.MouseInputEvent evt) {
        if (mouseIndicators != null)
            mouseIndicators.mouseEvent();
    }

    @SubscribeEvent
    public void mouseEvent(GuiScreenEvent.MouseInputEvent.Pre evt) {
        if (mouseIndicators != null)
            mouseIndicators.mouseEvent();
    }

    @SubscribeEvent
    public void keyEvent(InputEvent.KeyInputEvent evt) {
        if (keyboardIndicators != null)
            keyboardIndicators.keyEvent();
    }

    @SubscribeEvent
    public void keyEvent(GuiScreenEvent.KeyboardInputEvent.Pre evt) {
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
    public void registerIcons(TextureStitchEvent.Pre evt) {
        icons.registerIcons(evt.getMap());
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

    public void toggleMouse() {
        if (mouseIndicators != null)
            mouseIndicators.toggle();
    }

    public void toggleKeyboard() {
        if (keyboardIndicators != null)
            keyboardIndicators.toggle();
    }

}
