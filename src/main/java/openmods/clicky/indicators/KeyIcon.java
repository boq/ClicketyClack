package openmods.clicky.indicators;

import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.opengl.GL11;

public abstract class KeyIcon extends DecayingIcon {

    private static final float TEXT_SCALE = 1.0f / 16.0f;

    private static final float TEXT_DELTA = (16 - 7) / 16.0f / 2.0f;

    private final String contents;

    private final FontRenderer fontRenderer;

    private final float textWidth;

    public KeyIcon(String contents, FontRenderer fontRenderer, int decayTime) {
        super(decayTime);
        this.contents = contents;
        this.fontRenderer = fontRenderer;
        this.textWidth = (fontRenderer.getStringWidth(contents) - 1) / 16.0f;
    }

    public abstract float getWidth();

    protected void renderText(float alpha) {
        float middle = (getWidth() - textWidth) / 2.0f;

        GL11.glPushMatrix();
        GL11.glTranslatef(middle, TEXT_DELTA, 100f);
        GL11.glScalef(TEXT_SCALE, TEXT_SCALE, 0);
        int color = ((int)(alpha * 255)) << 24;
        fontRenderer.drawString(contents, 0, 0, color);
        GL11.glPopMatrix();
    }
}
