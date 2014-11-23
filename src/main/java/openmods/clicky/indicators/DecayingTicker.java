package openmods.clicky.indicators;

public class DecayingTicker implements Ticker {

    private final int decayTime;

    private boolean isDecaying;

    private int counter;

    public DecayingTicker(int decayTime) {
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

    public boolean hasDecayed() {
        return isDecaying && counter <= 0;
    }

    public float getProgress(float partialTick) {
        return Math.min(1, (counter + partialTick) / decayTime);
    }
}
