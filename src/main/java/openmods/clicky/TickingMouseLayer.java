package openmods.clicky;

public abstract class TickingMouseLayer extends MouseLayer {
    private int count;

    public TickingMouseLayer(String iconName) {
        super(iconName);
    }

    protected abstract boolean checkTrigger();

    @Override
    public void checkInput() {
        if (checkTrigger())
            count = Config.mouseEventVisibilityTime;
    }

    @Override
    protected boolean shouldRender() {
        return count > 0;
    }

    @Override
    public void tick() {
        count--;
    }
}
