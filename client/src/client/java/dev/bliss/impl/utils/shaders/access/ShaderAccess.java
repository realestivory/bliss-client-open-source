package dev.bliss.impl.utils.shaders.access;



import dev.bliss.impl.utils.shaders.BloomShader;
import dev.bliss.impl.utils.shaders.BlurShader;

import java.util.Arrays;
import java.util.List;

public class ShaderAccess {
    private static ShaderAccess shaderAccess;

    private final BlurShader blurShader = new BlurShader();
    private final BloomShader bloomShader = new BloomShader();


    private ShaderAccess() {}

    public static ShaderAccess getInstance() {
        if (shaderAccess == null) {
            shaderAccess = new ShaderAccess();
        }
        return shaderAccess;
    }

    public static ShaderAccess getShaderAccess() {
        return getInstance();
    }

    public void run(String shaderType, Runnable... runnables) {
        List<Runnable> runnableList = Arrays.asList(runnables);
        try {
            switch (shaderType) {
                case "blur":
                    //blurShader.renderBlur(runnableList);
                    break;
                case "bloom":
                    //bloomShader.renderBloom(runnableList);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid shader type: " + shaderType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runBlur(Runnable runnable) {
        //blurShader.renderBlur(Arrays.asList(runnable));
    }

    // Add this method
    public void run(Runnable runnable) {
        runnable.run();
    }
}