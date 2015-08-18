package openmods.clicky.indicators;

class Timer {
    private final int duration;

    private int counter;

    private boolean isTicking;

    public Timer(int duration) {
        this.duration = duration;
    }

    public void reset() {
        isTicking = false;
        counter = duration;
    }

    public void start() {
        isTicking = true;
    }

    public void resetAndStart() {
        isTicking = true;
        counter = duration;
    }

    public void tick() {
        if (isTicking)
            counter--;
    }

    public boolean isFinished() {
        return isTicking && counter <= 0;
    }

    public boolean isTicking() {
        return isTicking;
    }

    public float getProgress(float partialTick) {
        return Math.min(1, (counter + partialTick) / duration);
    }
}
