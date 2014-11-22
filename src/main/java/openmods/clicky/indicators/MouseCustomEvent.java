package openmods.clicky.indicators;

import openmods.clicky.IconContainer.IconHolder;

public abstract class MouseCustomEvent extends SingleDecayingIcon implements EventIcon {

    public MouseCustomEvent(IconHolder icon, int decayTime) {
        super(icon, decayTime);
    }

}
