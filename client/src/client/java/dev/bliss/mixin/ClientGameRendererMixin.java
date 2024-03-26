package dev.bliss.mixin;

import dev.bliss.client.Client;
import dev.bliss.impl.events.render.RenderScreen;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class ClientGameRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE",
    target = "Lnet/minecraft/client/gui/DrawContext;draw()V",
    shift = At.Shift.AFTER))
    private void hookRenderEvent(CallbackInfo ci) {
        Client.INSTANCE.getEventBus().publish(new RenderScreen());
    }
}
