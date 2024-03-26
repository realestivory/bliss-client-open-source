package dev.bliss.impl.modules.combat;

import dev.bliss.impl.events.player.PlayerPreMotionEvent;
import dev.bliss.impl.settings.ModeSetting;
import dev.bliss.api.module.enums.ModuleCategory;
import dev.bliss.api.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import radbus.Listen;

import java.util.ArrayList;

public final class KillAuraModule extends Module {
    private final ModeSetting<KillAuraModeEnum> mode = new ModeSetting<>("Mode", KillAuraModeEnum.SINGLE);

    public KillAuraModule() {
        super("KillAura", "Automatically attacks entities", ModuleCategory.COMBAT);
        this.addSettings(mode);
    }

    ArrayList<LivingEntity> targets = new ArrayList<>();

    @Listen
    public void onPreMotion(PlayerPreMotionEvent event) {
        if (mode.getValue() == KillAuraModeEnum.SINGLE) {
            LivingEntity target = null;
            for (LivingEntity entity : targets) {
                if (mc.player.distanceTo(entity) <= 5.0f) {
                    target = entity;
                    break;
                }
            }
            if (target != null) {
                onPlayerAttack(target);
            }
        }
    }

    public boolean isBot(LivingEntity entity){
        return entity.age >= 5 && !(mc.player.squaredDistanceTo(entity.getX(), mc.player.getY(), entity.getZ()) > 100 * 100) && mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()) != null;
    }

    private void SortTargets(){
        targets.clear();
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof LivingEntity) {
                // once we get MathUtils ill make this better but for now this will do
                if (mc.player.distanceTo(entity) <= 4.0f && entity != mc.player && isBot((LivingEntity) entity)  && entity.isAlive()) {
                    targets.add((LivingEntity) entity);
                }
            }
        }
    }

    private void onPlayerAttack(Entity target) {
        SortTargets();
        mc.player.swingHand(Hand.MAIN_HAND);
        mc.interactionManager.attackEntity(mc.player, target);
    }

    private enum KillAuraModeEnum {
        SINGLE,
        SWITCH
    }
}