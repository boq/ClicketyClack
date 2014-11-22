package openmods.clicky;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import openmods.clicky.config.IndicatorConfig.Horizontal;
import openmods.clicky.config.IndicatorConfig.Vertical;

public class IndicatorPosition {

    private final Minecraft minecraft;

    private final int dx;

    private final int dy;

    private final Vertical vertical;

    private final Horizontal horizontal;

    private int x;

    private int y;

    public IndicatorPosition(Minecraft minecraft, int dx, int dy, Vertical vertical, Horizontal horizontal) {
        this.minecraft = minecraft;
        this.dx = dx;
        this.dy = dy;
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public void update() {
        ScaledResolution res = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
        this.x = anchorX(res) + dx;
        this.y = anchorY(res) + dy;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    private int anchorX(ScaledResolution res) {
        switch (horizontal) {
            case RIGHT:
                return res.getScaledWidth();
            case MIDDLE:
                return res.getScaledWidth() / 2;
            case LEFT:
            default:
                return 0;
        }
    }

    private int anchorY(ScaledResolution res) {
        switch (vertical) {
            case BOTTOM:
                return res.getScaledHeight();
            case MIDDLE:
                return res.getScaledHeight() / 2;
            case TOP:
            default:
                return 0;
        }
    }
}
