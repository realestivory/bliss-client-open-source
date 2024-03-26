/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.bliss.mixin;

import dev.bliss.client.Client;

import dev.bliss.impl.events.render.RenderInGameHudEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("RETURN"))
    private void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        RenderInGameHudEvent event = new RenderInGameHudEvent(context);
        Client.INSTANCE.getEventBus().publish(event);

    }
}
