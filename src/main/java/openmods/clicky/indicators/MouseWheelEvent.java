package openmods.clicky.indicators;

import openmods.clicky.IconContainer.IconHolder;
import org.lwjgl.input.Mouse;

public abstract class MouseWheelEvent extends SingleDecayingIcon implements EventIcon {

    private final int threshold;

    private final int duration;

    private int activeCounter;

    public MouseWheelEvent(IconHolder icon, int decayTime, int threshold, int duration) {
        super(icon, decayTime);
        this.threshold = threshold;
        this.duration = duration;
    }

    protected abstract boolean shouldTrigger(int delta, int threshold);

    @Override
    public void handleInput() {
        int delta = Mouse.getEventDWheel();
        if (shouldTrigger(delta, threshold)) {
            resetDecay();
            activeCounter = duration;
        }
    }

    @Override
    public void tick() {
        if (activeCounter-- < 0)
            startDecay();

        super.tick();
    }

}
