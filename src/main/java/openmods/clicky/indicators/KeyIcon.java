package openmods.clicky.indicators;

import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.opengl.GL11;

public abstract class KeyIcon extends DecayingIcon {

    private static final float TEXT_SCALE = 1.0f / 16.0f;

    private static final float TEXT_DELTA = (16 - 7) / 16.0f / 2.0f;

    private final String contents;

    private final FontRenderer fontRenderer;

    private final float textWidth;

    private final Timer blinkTimer;

    public KeyIcon(String contents, FontRenderer fontRenderer, int decayTime, int blinkTime) {
        super(decayTime);
        this.contents = contents;
        this.fontRenderer = fontRenderer;
        this.textWidth = (fontRenderer.getStringWidth(contents) - 1) / 16.0f;
        this.blinkTimer = new Timer(blinkTime);
    }

    public abstract float getWidth();

    @Override
    protected final void renderIcon(float partialTick, float alpha) {
        float blink = getBlinkProgress(partialTick);
        renderIcon(partialTick, alpha, blink);
    }

    protected abstract void renderIcon(float partialTick, float alpha, float blink);

    protected void renderText(float blink, float alpha) {
        float middle = (getWidth() - textWidth) / 2.0f;

        GL11.glPushMatrix();
        GL11.glTranslatef(middle, TEXT_DELTA, 100f);
        GL11.glScalef(TEXT_SCALE, TEXT_SCALE, 0);
        final int redByte = (int)(blink * 255) & 0xFF;
        final int alphaByte = (int)(alpha * 255) & 0xFF;
        int color = alphaByte << 24 | redByte << 16;

        fontRenderer.drawString(contents, 0, 0, color);
        GL11.glPopMatrix();
    }

    public void blink() {
        blinkTimer.resetAndStart();
    }

    @Override
    public void tick() {
        super.tick();
        blinkTimer.tick();
    }

    private float getBlinkProgress(float partialTick) {
        return blinkTimer.isTicking() && !blinkTimer.isFinished() ? blinkTimer.getProgress(partialTick) : 0;
    }
}
