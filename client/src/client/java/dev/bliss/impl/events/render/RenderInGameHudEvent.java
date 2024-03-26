/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.bliss.impl.events.render;

import net.minecraft.client.gui.DrawContext;
import dev.bliss.api.event.Event;


public class RenderInGameHudEvent implements Event {
    private final DrawContext context;

    public RenderInGameHudEvent(DrawContext context) {
        this.context = context;
    }

    public DrawContext getContext() {
        return context;
    }
}
