package dev.bliss.impl.utils.shaders.exception;

public class ShaderLinkFailException extends IllegalStateException {

    public ShaderLinkFailException(int id) {
        super(String.format("Failed to link shader %d", id));
    }

}