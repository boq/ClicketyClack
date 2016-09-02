package openmods.clicky.indicators;

import openmods.clicky.IconContainer.IconHolder;
import org.lwjgl.input.Mouse;

public class MouseButtonEvent extends SingleDecayingIcon implements EventIcon {

    private final int button;

    private int unstuckCounter;

    public MouseButtonEvent(IconHolder icon, int decayTime, int button) {
        super(icon, decayTime);
        this.button = button;
    }

    @Override
    public void tick() {
        super.tick();

        // This happens when GUI calls Mouse.next() in unexpected place
        // Know cases: cpw.mods.fml.client.GuiScrollingList
        if (unstuckCounter-- < 0) {
            if (!Mouse.isButtonDown(button))
                startDecay();

            unstuckCounter = 20;
        }
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
