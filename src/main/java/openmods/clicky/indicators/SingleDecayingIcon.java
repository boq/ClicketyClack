package openmods.clicky.indicators;

import net.minecraft.util.IIcon;
import openmods.clicky.IconContainer.IconHolder;
import openmods.clicky.RenderUtils;

public class SingleDecayingIcon extends DecayingIcon {

    private final IconHolder icon;

    public SingleDecayingIcon(IconHolder icon, int decayTime) {
        super(decayTime);
        this.icon = icon;
    }

    @Override
    protected void renderIcon(float partialTick, float alpha) {
        IIcon icon = this.icon.get();
        if (icon != null)
            RenderUtils.renderIcon(icon, 1, 1, 1, alpha);
    }

}
