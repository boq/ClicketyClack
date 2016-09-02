package openmods.clicky;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import openmods.clicky.indicators.EventIcon;
import openmods.clicky.indicators.MouseButtonEvent;
import openmods.clicky.indicators.MouseWheelEvent;
import openmods.clicky.indicators.Renderable;
import openmods.clicky.indicators.StaticIcon;
import openmods.clicky.indicators.Ticker;

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

        background = new StaticIcon(icons.getHolder(ClicketyClack.location("mouse_background")));

        indicators.add(new MouseWheelEvent(icons.getHolder(ClicketyClack.location("mouse_up")), decayTime, wheelThreshold, wheelDuration) {
            @Override
            protected boolean shouldTrigger(int delta, int threshold) {
                return delta > threshold;
            }
        });

        indicators.add(new MouseWheelEvent(icons.getHolder(ClicketyClack.location("mouse_down")), decayTime, wheelThreshold, wheelDuration) {
            @Override
            protected boolean shouldTrigger(int delta, int threshold) {
                return delta < -threshold;
            }
        });

        indicators.add(new MouseButtonEvent(icons.getHolder(ClicketyClack.location("mouse_left")), decayTime, 0));
        indicators.add(new MouseButtonEvent(icons.getHolder(ClicketyClack.location("mouse_right")), decayTime, 1));
        indicators.add(new MouseButtonEvent(icons.getHolder(ClicketyClack.location("mouse_middle")), decayTime, 2));

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

        GlStateManager.pushMatrix();
        GlStateManager.translate(position.x(), position.y(), 0);
        GlStateManager.scale(scale, scale, 1);

        background.render(partialTick);

        for (Renderable layer : indicators)
            layer.render(partialTick);

        GlStateManager.popMatrix();
    }
}
