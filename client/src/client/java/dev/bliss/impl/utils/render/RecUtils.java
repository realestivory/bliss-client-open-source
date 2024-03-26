package dev.bliss.impl.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.bliss.impl.utils.Utils;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.opengl.GL11;

public class RecUtils implements Utils {
    public static void drawRect(float x, float y, float width, float height, int color) {
        float f = (float)(color >> 24 & 255) / 255.0F;
        float f1 = (float)(color >> 16 & 255) / 255.0F;
        float f2 = (float)(color >> 8 & 255) / 255.0F;
        float f3 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x, y + height, 0.0D).color(f1, f2, f3, f).next();
        bufferBuilder.vertex(x + width, y + height, 0.0D).color(f1, f2, f3, f).next();
        bufferBuilder.vertex(x + width, y, 0.0D).color(f1, f2, f3, f).next();
        bufferBuilder.vertex(x, y, 0.0D).color(f1, f2, f3, f).next();
        tessellator.draw();
    }
    public static void drawRectWithOutline(float x, float y, float width, float height, float lineWidth, int color, int color2) {
        drawRect(x, y, width, lineWidth, color2);
        drawRect(x, y, lineWidth, height, color2);
        drawRect(x + width, y, lineWidth, height, color2);
        drawRect(x, y + height, width + lineWidth, lineWidth, color2);
        drawRect(x, y, width, height, color);
    }
    public static void draw2DPolygon(float x, float y, float radius, int sides, int color) {
        if (sides < 3) {
            return;
        }
        float a = (float)(color >> 24 & 255) / 255.0F;
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x, y, 0.0D).color(r, g, b, a).next();
        for(int i = 0; i <= sides; ++i) {
            double angle = 6.283185307179586D * (double)i / (double)sides;
            double x2 = x + (double)radius * Math.cos(angle);
            double y2 = y + (double)radius * Math.sin(angle);
            bufferBuilder.vertex(x2, y2, 0.0D).color(r, g, b, a).next();
        }
        tessellator.draw();
    }
    public static void drawRoundedRect(float x, float y, float width, float height, float radius, int color) {

        RenderSystem.enableBlend();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        double x1 = x + width;
        double y1 = y + height;
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);

        x *= 2;
        y *= 2;
        x1 *= 2;
        y1 *= 2;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glBegin(GL11.GL_POLYGON);

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + +(Math.sin((i * Math.PI / 180)) * (radius * -1)), y + radius + (Math.cos((i * Math.PI / 180)) * (radius * -1)));
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + (Math.sin((i * Math.PI / 180)) * (radius * -1)), y1 - radius + (Math.cos((i * Math.PI / 180)) * (radius * -1)));
        }

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + (Math.sin((i * Math.PI / 180)) * radius), y1 - radius + (Math.cos((i * Math.PI / 180)) * radius));
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + (Math.sin((i * Math.PI / 180)) * radius), y + radius + (Math.cos((i * Math.PI / 180)) * radius));
        }

        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glScaled(2, 2, 2);

        GL11.glPopAttrib();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        RenderSystem.disableBlend();

    }
    public static void drawOutlinedRoundedRect(double x, double y, double width, double height, double radius, float linewidth, int color) {
        RenderSystem.enableBlend();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        double x1 = x + width;
        double y1 = y + height;
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);

        x *= 2;
        y *= 2;
        x1 *= 2;
        y1 *= 2;
        GL11.glLineWidth(linewidth);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(2);

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + +(Math.sin((i * Math.PI / 180)) * (radius * -1)), y + radius + (Math.cos((i * Math.PI / 180)) * (radius * -1)));
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + (Math.sin((i * Math.PI / 180)) * (radius * -1)), y1 - radius + (Math.cos((i * Math.PI / 180)) * (radius * -1)));
        }

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + (Math.sin((i * Math.PI / 180)) * radius), y1 - radius + (Math.cos((i * Math.PI / 180)) * radius));
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + (Math.sin((i * Math.PI / 180)) * radius), y + radius + (Math.cos((i * Math.PI / 180)) * radius));
        }

        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glScaled(2, 2, 2);

        GL11.glPopAttrib();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        RenderSystem.disableBlend();
    }
}