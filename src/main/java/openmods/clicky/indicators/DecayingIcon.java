package openmods.clicky.indicators;

public abstract class DecayingIcon extends DecayingTicker implements Renderable {

    public DecayingIcon(int decayTime) {
        super(decayTime);
    }

    protected abstract void renderIcon(float alpha);

    @Override
    public final void render(float partialTick) {
        if (hasDecayed())
            return;

        float alpha = getProgress(partialTick);
        renderIcon(alpha);
    }

}
