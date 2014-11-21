package openmods.clicky.indicators;

import openmods.clicky.IconContainer.IconHolder;

public abstract class DecayingInputIcon extends DecayingIcon implements EventIcon {

    public DecayingInputIcon(IconHolder icon, int decayTime) {
        super(icon, decayTime);
    }

}
