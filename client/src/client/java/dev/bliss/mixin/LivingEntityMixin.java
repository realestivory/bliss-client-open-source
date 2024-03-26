package dev.bliss.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At(value = "HEAD"), method = "getStackInHand", cancellable = true)
    private void getStackInHand(Hand hand, CallbackInfoReturnable<ItemStack> cir) {
        if (hand == null) { // have to test modifying Hand enum (for watchdog null no slow)
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }
}
