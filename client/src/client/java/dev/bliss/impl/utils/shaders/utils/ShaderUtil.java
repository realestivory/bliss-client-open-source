package dev.bliss.impl.utils.shaders.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.util.Window;

import static dev.bliss.impl.utils.Utils.mc;
import static org.lwjgl.opengl.GL11.*;


@UtilityClass
public class ShaderUtil {

    public static final String VERTEX_SHADER = "#version 330 core\n" +
            "\n" +
            "layout(location = 0) in vec4 in_Position;\n" +
            "layout(location = 1) in vec2 in_TexCoords;\n" +
            "\n" +
            "out vec2 TexCoords;\n" +
            "\n" +
            "uniform mat4 modelViewProjectionMatrix;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    TexCoords = in_TexCoords;\n" +
            "    gl_Position = modelViewProjectionMatrix * in_Position;\n" +
            "}";

    public static final String BLUR_SHADER = "#version 330 core\n" +
            "\n" +
            "in vec2 TexCoords;\n" +
            "out vec4 FragColor;\n" +
            "\n" +
            "uniform sampler2D inputTexture;\n" +
            "uniform float screenWidth;\n" +
            "uniform float screenHeight;\n" +
            "uniform float blurRadius;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    vec2 texelSize = 1.0 / vec2(screenWidth, screenHeight);\n" +
            "\n" +
            "    // Improved blur calculation with weighted samples\n" +
            "    vec4 result = vec4(0.0);\n" +
            "    float totalWeight = 0.0;\n" +
            "\n" +
            "    for (float x = -blurRadius; x <= blurRadius; x += 1.0)\n" +
            "    {\n" +
            "        for (float y = -blurRadius; y <= blurRadius; y += 1.0)\n" +
            "        {\n" +
            "            vec2 offset = vec2(x, y) * texelSize;\n" +
            "            float weight = exp(-(x*x + y*y) / (2.0 * blurRadius * blurRadius));\n" +
            "            result += texture(inputTexture, TexCoords + offset) * weight;\n" +
            "            totalWeight += weight;\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    result /= totalWeight;\n" +
            "\n" +
            "    // Optional: Apply additional post-processing effects\n" +
            "    result = pow(result, vec4(1.2));  // Example: Increase brightness\n" +
            "\n" +
            "    FragColor = result;\n" +
            "}";
    public static final String BLOOM_SHADER = "#version 330 core\n" +
            "\n" +
            "in vec2 TexCoords;\n" +
            "out vec4 FragColor;\n" +
            "\n" +
            "uniform sampler2D inputTexture;\n" +
            "uniform float screenWidth;\n" +
            "uniform float screenHeight;\n" +
            "uniform float bloomThreshold;\n" +
            "uniform float bloomIntensity;\n" +
            "uniform float bloomRadius;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    vec2 texelSize = 1.0 / vec2(screenWidth, screenHeight);\n" +
            "\n" +
            "    // Extract bright parts (bloom) from the scene\n" +
            "    vec4 sceneColor = texture(inputTexture, TexCoords);\n" +
            "    vec3 bloomColor = max(sceneColor.rgb - bloomThreshold, vec3(0.0));\n" +
            "    bloomColor = pow(bloomColor, vec3(1.2));  // Optional: Adjust bloom color intensity\n" +
            "\n" +
            "    // Blur the bloom texture using a more optimized algorithm (separable blur)\n" +
            "    vec4 blurredBloom = vec4(0.0);\n" +
            "    float sigma = bloomRadius;\n" +
            "    for (float i = -5.0; i <= 5.0; i += 1.0)\n" +
            "    {\n" +
            "        float offset = i * texelSize.x;\n" +
            "        vec4 sample = texture(inputTexture, TexCoords + vec2(offset, 0.0)) * bloomIntensity;\n" +
            "        blurredBloom += sample * exp(-offset * offset / (2.0 * sigma * sigma));\n" +
            "    }\n" +
            "    blurredBloom /= 11.0;\n" +
            "\n" +
            "    // Combine the original scene and the blurred bloom using an additive blend\n" +
            "    FragColor = sceneColor + vec4(bloomColor, 0.0) + blurredBloom;\n" +
            "}";

    public static Framebuffer doFrameBuffer(final Framebuffer framebuffer) {
       return mc.getFramebuffer();
    }
    public void drawQuad(float x, float y, float width, float height) {
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glEnd();
    }

    public void drawFullScreenQuad() {
        Window window = MinecraftClient.getInstance().getWindow();
        float screenWidth = (float) window.getFramebufferWidth();
        float screenHeight = (float) window.getFramebufferHeight();

        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(0, 0);
        glVertex2f(0, screenHeight);
        glTexCoord2f(1, 0);
        glVertex2f(screenWidth, screenHeight);
        glTexCoord2f(1, 1);
        glVertex2f(screenWidth, 0);
        glEnd();
    }
}
