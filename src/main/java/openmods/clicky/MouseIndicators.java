package openmods.clicky;

import java.util.List;

import openmods.clicky.indicators.*;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;

public class MouseIndicators {
    private final IndicatorPosition position;
    private final int scale;

    private final StaticIcon background;

    private final List<EventIcon> indicators;

    private boolean visible = true;

    public MouseIndicators(IconContainer icons, IndicatorPosition position, int scale, int decayTime, int wheelDuration, int wheelThreshold) {
        this.position = position;
        this.scale = scale;

        ImmutableList.Builder<EventIcon> indicators = ImmutableList.builder();

        background = new StaticIcon(icons.getHolder("clicketyclack:mouse_background"));

        indicators.add(new MouseWheelEvent(icons.getHolder("clicketyclack:mouse_up"), decayTime, wheelThreshold, wheelDuration) {
            @Override
            protected boolean shouldTrigger(int delta, int threshold) {
                return delta > threshold;
            }
        });

        indicators.add(new MouseWheelEvent(icons.getHolder("clicketyclack:mouse_down"), decayTime, wheelThreshold, wheelDuration) {
            @Override
            protected boolean shouldTrigger(int delta, int threshold) {
                return delta < -threshold;
            }
        });

        indicators.add(new MouseButtonEvent(icons.getHolder("clicketyclack:mouse_left"), decayTime, 0));
        indicators.add(new MouseButtonEvent(icons.getHolder("clicketyclack:mouse_right"), decayTime, 1));
        indicators.add(new MouseButtonEvent(icons.getHolder("clicketyclack:mouse_middle"), decayTime, 2));

        this.indicators = indicators.build();
    }

    public void toggle() {
        visible = !visible;
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
        if (!visible)
            return;

        RenderUtils.bindDefaultItemsTexture();
        position.update();

        GL11.glPushMatrix();
        GL11.glTranslated(position.x(), position.y(), 0);
        GL11.glScaled(scale, scale, 1);

        background.render(partialTick);

        for (Renderable layer : indicators)
            layer.render(partialTick);

        GL11.glPopMatrix();
    }
}
