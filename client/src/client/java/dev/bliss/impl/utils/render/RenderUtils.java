package dev.bliss.impl.utils.render;

import dev.bliss.impl.utils.Utils;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class RenderUtils implements Utils {
    public static void drawImage(int x, int y, int width, int height, String path) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Identifier image = new Identifier(path);
        mc.getTextureManager().bindTexture(image);
    }
}
