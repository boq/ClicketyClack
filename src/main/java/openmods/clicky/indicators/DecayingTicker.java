package openmods.clicky.indicators;

public class DecayingTicker implements Ticker {

    private final Timer decayTimer;

    public DecayingTicker(int decayTime) {
        this.decayTimer = new Timer(decayTime);
    }

    public void resetDecay() {
        decayTimer.reset();
    }

    public void startDecay() {
        decayTimer.start();
    }

    @Override
    public void tick() {
        decayTimer.tick();
    }

    public boolean hasDecayed() {
        return decayTimer.isFinished();
    }

    public float getDecayProgress(float partialTick) {
        return decayTimer.getProgress(partialTick);
    }

}
