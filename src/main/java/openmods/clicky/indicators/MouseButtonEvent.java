package openmods.clicky.indicators;

import openmods.clicky.IconContainer.IconHolder;

import org.lwjgl.input.Mouse;

public class MouseButtonEvent extends DecayingIcon implements EventIcon {

    private final int button;

    public MouseButtonEvent(IconHolder icon, int decayTime, int button) {
        super(icon, decayTime);
        this.button = button;
    }

    @Override
    public void handleInput() {
        if (Mouse.getEventButton() == button) {
            if (Mouse.getEventButtonState())
                resetDecay();
            else
                startDecay();
        }
    }

}
