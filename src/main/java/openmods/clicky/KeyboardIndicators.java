package openmods.clicky;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import openmods.clicky.IconContainer.IconHolder;
import openmods.clicky.indicators.KeyIcon;
import openmods.clicky.indicators.SingleKeyIcon;
import openmods.clicky.indicators.WideKeyIcon;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class KeyboardIndicators {
    private final IndicatorPosition position;
    private final int scale;
    private final int fadeTime;

    private final IconHolder keyLeft;
    private final IconHolder keyMiddle;
    private final IconHolder keyRight;
    private final IconHolder keySingle;

    private final Map<Integer, KeyIcon> activeIcons = Maps.newHashMap();
    private final List<KeyIcon> icons = Lists.newLinkedList();

    private boolean visible = true;

    private int unstuckCounter;

    private static final Map<Integer, String> CUSTOM_NAMES;
    private static final Map<Integer, Integer> EXTRA_SIZE;

    private static final String GRAY = "\u00A77";

    static {
        ImmutableMap.Builder<Integer, String> builder = ImmutableMap.builder();
        builder.put(Keyboard.KEY_SPACE, "\u2423");

        builder.put(Keyboard.KEY_UP, "\u2191");
        builder.put(Keyboard.KEY_DOWN, "\u2193");
        builder.put(Keyboard.KEY_LEFT, "\u2190");
        builder.put(Keyboard.KEY_RIGHT, "\u2192");

        builder.put(Keyboard.KEY_ESCAPE, "ESC");
        builder.put(Keyboard.KEY_MINUS, "-");
        builder.put(Keyboard.KEY_EQUALS, "=");
        builder.put(Keyboard.KEY_TAB, "\u21E5");
        builder.put(Keyboard.KEY_LBRACKET, "[");
        builder.put(Keyboard.KEY_RBRACKET, "]");
        builder.put(Keyboard.KEY_RETURN, "\u23CE");
        builder.put(Keyboard.KEY_SEMICOLON, ";");
        builder.put(Keyboard.KEY_APOSTROPHE, "'");
        builder.put(Keyboard.KEY_GRAVE, "~");
        builder.put(Keyboard.KEY_BACKSLASH, "\\");
        builder.put(Keyboard.KEY_COMMA, ",");
        builder.put(Keyboard.KEY_PERIOD, ".");
        builder.put(Keyboard.KEY_SLASH, "/");
        builder.put(Keyboard.KEY_MULTIPLY, "*");

        builder.put(Keyboard.KEY_SCROLL, "SCROLL LOCK");
        builder.put(Keyboard.KEY_CAPITAL, "CAPS LOCK");
        builder.put(Keyboard.KEY_NUMLOCK, "NUM LOCK");

        builder.put(Keyboard.KEY_BACK, "\u232B");

        builder.put(Keyboard.KEY_NUMPAD0, GRAY + "0");
        builder.put(Keyboard.KEY_NUMPAD1, GRAY + "1");
        builder.put(Keyboard.KEY_NUMPAD2, GRAY + "2");
        builder.put(Keyboard.KEY_NUMPAD3, GRAY + "3");
        builder.put(Keyboard.KEY_NUMPAD4, GRAY + "4");
        builder.put(Keyboard.KEY_NUMPAD5, GRAY + "5");
        builder.put(Keyboard.KEY_NUMPAD6, GRAY + "6");
        builder.put(Keyboard.KEY_NUMPAD7, GRAY + "7");
        builder.put(Keyboard.KEY_NUMPAD8, GRAY + "8");
        builder.put(Keyboard.KEY_NUMPAD9, GRAY + "9");

        builder.put(Keyboard.KEY_SUBTRACT, GRAY + "-");
        builder.put(Keyboard.KEY_ADD, GRAY + "+");
        builder.put(Keyboard.KEY_DECIMAL, GRAY + ".");
        builder.put(Keyboard.KEY_DIVIDE, GRAY + "/");
        builder.put(Keyboard.KEY_NUMPADENTER, "\u23CE");

        builder.put(Keyboard.KEY_SYSRQ, "\u2603");
        builder.put(Keyboard.KEY_NONE, "\u2623");

        builder.put(Keyboard.KEY_APPS, "\u25A4");
        builder.put(Keyboard.KEY_FUNCTION, "\u2318");
        builder.put(Keyboard.KEY_LMETA, "\u2756");

        builder.put(Keyboard.KEY_PAUSE, "PAUSE");
        builder.put(Keyboard.KEY_LCONTROL, "CTRL");
        builder.put(Keyboard.KEY_RCONTROL, GRAY + "CTRL");
        builder.put(Keyboard.KEY_LSHIFT, "\u21E7");
        builder.put(Keyboard.KEY_RSHIFT, GRAY + "\u21E7");
        builder.put(Keyboard.KEY_LMENU, "ALT");
        builder.put(Keyboard.KEY_RMENU, GRAY + "ALT");
        builder.put(Keyboard.KEY_PRIOR, "PGUP");
        builder.put(Keyboard.KEY_NEXT, "PGDN");
        builder.put(Keyboard.KEY_HOME, "HOME");
        builder.put(Keyboard.KEY_END, "END");
        builder.put(Keyboard.KEY_INSERT, "INS");
        builder.put(Keyboard.KEY_DELETE, "\u2326");

        CUSTOM_NAMES = builder.build();
    }

    static {
        ImmutableMap.Builder<Integer, Integer> builder = ImmutableMap.builder();
        builder.put(Keyboard.KEY_ESCAPE, 2);
        builder.put(Keyboard.KEY_SCROLL, 5);
        builder.put(Keyboard.KEY_CAPITAL, 4);
        builder.put(Keyboard.KEY_NUMLOCK, 4);
        builder.put(Keyboard.KEY_PAUSE, 2);
        builder.put(Keyboard.KEY_LCONTROL, 2);
        builder.put(Keyboard.KEY_RCONTROL, 2);
        builder.put(Keyboard.KEY_LMENU, 2);
        builder.put(Keyboard.KEY_RMENU, 2);
        builder.put(Keyboard.KEY_PRIOR, 2);
        builder.put(Keyboard.KEY_NEXT, 2);
        builder.put(Keyboard.KEY_HOME, 2);
        builder.put(Keyboard.KEY_END, 2);
        builder.put(Keyboard.KEY_F10, 2);
        builder.put(Keyboard.KEY_F11, 2);
        builder.put(Keyboard.KEY_F12, 2);
        builder.put(Keyboard.KEY_INSERT, 2);
        EXTRA_SIZE = builder.build();
    }

    public KeyboardIndicators(IconContainer icons, IndicatorPosition position, int scale, int decayTime) {
        this.position = position;
        this.scale = scale;
        this.fadeTime = decayTime;

        keyLeft = icons.getHolder("clicketyclack:key_left");
        keyMiddle = icons.getHolder("clicketyclack:key_middle");
        keyRight = icons.getHolder("clicketyclack:key_right");
        keySingle = icons.getHolder("clicketyclack:key_single");
    }

    private KeyIcon createIcon(String name, int key) {
        Integer size = EXTRA_SIZE.get(key);
        if (size != null) {
            if (size == 2)
                return new WideKeyIcon(name, RenderUtils.getFontRenderer(), fadeTime, keyLeft, keyRight);
            else if (size > 2) {
                IconHolder[] result = new IconHolder[size];
                result[0] = keyLeft;
                for (int i = 1; i < size - 1; i++)
                    result[i] = keyMiddle;
                result[size - 1] = keyRight;
                return new WideKeyIcon(name, RenderUtils.getFontRenderer(), fadeTime, result);
            }
        }

        return new SingleKeyIcon(keySingle, name, RenderUtils.getFontRenderer(), fadeTime);
    }

    public void keyEvent() {
        final int key = Keyboard.getEventKey();
        final boolean isDown = Keyboard.getEventKeyState();

        if (isDown) {
            String name = CUSTOM_NAMES.get(key);

            if (name == null)
                name = Keyboard.getKeyName(key);

            KeyIcon icon = createIcon(name, key);
            icon.resetDecay();
            activeIcons.put(key, icon);
            icons.add(icon);
        } else {
            KeyIcon icon = activeIcons.remove(key);
            if (icon != null)
                icon.startDecay();
        }
    }

    public void toggle() {
        visible = !visible;

        if (!visible) {
            activeIcons.clear();
            icons.clear();
        }
    }

    public void tick() {
        Iterator<KeyIcon> icons = this.icons.iterator();

        if (unstuckCounter-- < 0) {
            unstuck();
            unstuckCounter = 20;
        }

        while (icons.hasNext()) {
            KeyIcon icon = icons.next();
            icon.tick();

            if (icon.hasDecayed())
                icons.remove();
        }
    }

    private void unstuck() {
        // Key can get stuck when window is resized or full screen is activated
        Iterator<Map.Entry<Integer, KeyIcon>> it = activeIcons.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, KeyIcon> e = it.next();
            if (!Keyboard.isKeyDown(e.getKey())) {
                e.getValue().startDecay();
                it.remove();
            }
        }
    }

    public void render(float renderTick) {
        if (!visible)
            return;

        RenderUtils.bindDefaultItemsTexture();
        position.update();

        GL11.glPushMatrix();
        GL11.glTranslated(position.x(), position.y(), 0);
        GL11.glScaled(scale, scale, 1);

        for (KeyIcon icon : icons) {
            icon.render(renderTick);
            GL11.glTranslated(icon.getWidth(), 0, 0);
        }

        GL11.glPopMatrix();
    }

}
