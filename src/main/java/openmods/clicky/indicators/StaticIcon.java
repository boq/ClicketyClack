package openmods.clicky.indicators;

import openmods.clicky.IconContainer.IconHolder;

public class StaticIcon extends RenderableIcon {

    public StaticIcon(IconHolder icon) {
        super(icon);
    }

    @Override
    protected boolean shouldRender() {
        return true;
    }

    @Override
    protected float alpha(float partialTick) {
        return 1;
    }

}
