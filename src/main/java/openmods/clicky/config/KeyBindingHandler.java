package openmods.clicky.config;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import openmods.clicky.OverlayRenderHandler;
import org.lwjgl.input.Keyboard;

public class KeyBindingHandler {

    private final KeyBinding mouseIndicator;

    private final KeyBinding keyboardIndicator;

    private final OverlayRenderHandler handler;

    private boolean mouseState;

    private boolean keyboardState;

    public KeyBindingHandler(OverlayRenderHandler handler) {
        this.handler = handler;
        mouseIndicator = new KeyBinding("openmods.clicky.toggle_mouse", Keyboard.KEY_M, "openmods.clicky.binding");
        ClientRegistry.registerKeyBinding(mouseIndicator);

        keyboardIndicator = new KeyBinding("openmods.clicky.toggle_keyboard", Keyboard.KEY_K, "openmods.clicky.binding");
        ClientRegistry.registerKeyBinding(keyboardIndicator);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent evt) {
        if (mouseIndicator.isPressed()) {
            if (!mouseState) {
                handler.toggleMouse();
                mouseState = true;
            }
        } else
            mouseState = false;

        if (keyboardIndicator.isPressed()) {
            if (!keyboardState) {
                handler.toggleKeyboard();
                keyboardState = true;
            }
        } else
            keyboardState = false;
    }

}
