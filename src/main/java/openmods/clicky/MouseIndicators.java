package openmods.clicky;

import java.util.List;

import openmods.clicky.indicators.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;

public class MouseIndicators {
    private static final int WHEEL_THRESHOLD = 5;
    private final int x;
    private final int y;
    private final int scale;

    private final StaticIcon background;

    private final List<EventIcon> indicators;

    public MouseIndicators(IconContainer icons, int x, int y, int scale, int decayTime) {
        this.x = x;
        this.y = y;
        this.scale = scale;

        ImmutableList.Builder<EventIcon> indicators = ImmutableList.builder();

        background = new StaticIcon(icons.getHolder("clicketyclack:mouse_background"));

        indicators.add(new DecayingInputIcon(icons.getHolder("clicketyclack:mouse_up"), decayTime) {
            @Override
            public void handleInput() {
                if (Mouse.getEventDWheel() > WHEEL_THRESHOLD) {
                    resetDecay();
                    startDecay();
                }
            }
        });

        indicators.add(new DecayingInputIcon(icons.getHolder("clicketyclack:mouse_down"), decayTime) {
            @Override
            public void handleInput() {
                if (Mouse.getEventDWheel() < -WHEEL_THRESHOLD) {
                    resetDecay();
                    startDecay();
                }
            }
        });

        indicators.add(new MouseButtonEvent(icons.getHolder("clicketyclack:mouse_left"), decayTime, 0));
        indicators.add(new MouseButtonEvent(icons.getHolder("clicketyclack:mouse_right"), decayTime, 1));
        indicators.add(new MouseButtonEvent(icons.getHolder("clicketyclack:mouse_middle"), decayTime, 2));

        this.indicators = indicators.build();
    }

    public void tick() {
        for (Ticker ticker : indicators)
            ticker.tick();
    }

    public void mouseEvent() {
        for (EventIcon handler : indicators)
            handler.handleInput();
    }

    public void render(float partialTick) {
        Utils.bindDefaultItemsTexture();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0);
        GL11.glScaled(scale, scale, 1);

        background.render(partialTick);

        for (Renderable layer : indicators)
            layer.render(partialTick);

        GL11.glPopMatrix();
    }
}
