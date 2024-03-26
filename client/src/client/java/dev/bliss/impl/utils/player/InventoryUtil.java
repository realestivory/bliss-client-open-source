package dev.bliss.impl.utils.player;

import dev.bliss.impl.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.slot.SlotActionType;

public class InventoryUtil implements Utils {
    public static void drop(int slot) {
        MinecraftClient.getInstance().interactionManager.clickSlot(0, slot, 1, SlotActionType.THROW, MinecraftClient.getInstance().player);
    }

    public static void swap(int slot, int armorType) {
        int armorSlot = 5 + armorType;
        MinecraftClient.getInstance().interactionManager.clickSlot(0, slot, 0, SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);
    }
    public static void click(int slot, int mouseButton, boolean shiftClick) {
        MinecraftClient.getInstance().interactionManager.clickSlot(MinecraftClient.getInstance().player.playerScreenHandler.syncId, slot, mouseButton, shiftClick ? SlotActionType.QUICK_MOVE : SlotActionType.PICKUP, MinecraftClient.getInstance().player);
    }
}