package openmods.clicky.indicators;

import openmods.clicky.IconContainer.IconHolder;

public class DecayingIcon extends RenderableIcon implements Ticker {

    private final int decayTime;

    private boolean isDecaying;

    private int counter;

    public DecayingIcon(IconHolder icon, int decayTime) {
        super(icon);
        this.decayTime = decayTime;
    }

    public void resetDecay() {
        isDecaying = false;
        counter = decayTime;
    }

    public void startDecay() {
        isDecaying = true;
    }

    @Override
    public void tick() {
        if (isDecaying)
            counter--;
    }

    @Override
    protected boolean shouldRender() {
        return counter > 0;
    }

    @Override
    protected float alpha(float partialTick) {
        return (counter + partialTick) / decayTime;
    }
}
