package dev.bliss.impl.utils.render.font;

import dev.bliss.Bliss;
import net.minecraft.client.util.math.MatrixStack;

public class FontManager {
    private MatrixStack matrix = new MatrixStack();
    public FontRenderer fontRenderer = new FontRenderer(Bliss.class.getClassLoader().getResourceAsStream("assets/bliss/font/productsans.ttf"), 18);

    public MatrixStack getMatrixStack() {
        return this.matrix;
    }

    public void setMatrixStack(MatrixStack matrixStack) {
        this.matrix = matrixStack;
    }

}