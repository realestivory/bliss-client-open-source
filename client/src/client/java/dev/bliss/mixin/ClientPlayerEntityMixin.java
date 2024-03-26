package dev.bliss.mixin;

import dev.bliss.client.Client;
import dev.bliss.impl.events.player.PlayerSlowdownEvent;
import dev.bliss.impl.events.player.PlayerPreMotionEvent;
import dev.bliss.impl.modules.movement.NoSlowDownModule;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
	@Shadow public abstract boolean isUsingItem();

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"), method = "tickMovement")
	private boolean onSlowdown(ClientPlayerEntity instance) {
		PlayerSlowdownEvent slowdownEvent = new PlayerSlowdownEvent();
		Client.INSTANCE.getEventBus().publish(slowdownEvent);
		return !slowdownEvent.isCancelled() && isUsingItem();
	}

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"), method = "canStartSprinting")
	private boolean canStartSprinting(ClientPlayerEntity instance) { // so you can start sprinting while using no slow
		return !Client.INSTANCE.getModuleRegistry().get(NoSlowDownModule.class).isEnabled();
	}

	@Inject(at = @At("HEAD"), method = "sendMovementPackets", cancellable = true)
	private void sendMovementPackets(CallbackInfo ci) {
		ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
		PlayerPreMotionEvent preMotionEvent = new PlayerPreMotionEvent(player.getX(),player.getY(),player.getZ(), player.getYaw(),player.getPitch(), player.isOnGround());
		Client.INSTANCE.getEventBus().publish(preMotionEvent); // have to add ability to change positions in this event
		if (preMotionEvent.isCancelled()) {
			ci.cancel();
		}
	}
}