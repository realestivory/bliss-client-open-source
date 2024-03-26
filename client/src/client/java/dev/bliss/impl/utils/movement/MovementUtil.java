package dev.bliss.impl.utils.movement;

import dev.bliss.impl.utils.Utils;
import net.minecraft.entity.effect.StatusEffects;

public class MovementUtil implements Utils {
    public boolean isOnGround() {
        return mc.player.isOnGround();
    }
    public boolean isMoving() {
        return mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0;
    }
    public float getDirection(float rotationYaw) {
        float yaw = mc.player.getYaw();
        float strafe = 45;
        if (mc.player.input.movementForward < 0) {
            strafe = -45;
            yaw += 180;
        }
        if (mc.player.input.movementSideways > 0) {
            yaw -= strafe;
            if (mc.player.input.movementForward == 0) {
                yaw -= 45;
            }
        } else if (mc.player.input.movementSideways < 0) {
            yaw += strafe;
            if (mc.player.input.movementForward == 0) {
                yaw += 45;
            }
        }
        return yaw;
    }
    public static double getBPS() {
        double bps = (Math.hypot(mc.player.getX() - mc.player.prevX, mc.player.getZ() - mc.player.prevZ) * (20.0F / mc.getTickDelta())) * 20;
        return Math.round(bps * 100.0) / 100.0;
    }
    public static double getMoveSpeed() {
        double baseSpeed = mc.player.getMovementSpeed() * 2.873;
        if (mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) {
            baseSpeed /= 1.0 + 0.2 * (mc.player.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier() + 1);
        }
        if (mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            baseSpeed *= 1.0 + 0.2 * (mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1);
        }
        return baseSpeed;
    }
    public void strafe() {
        if (isMoving()) {
            double dir = Math.toRadians(getDirection(mc.player.getYaw()));
            mc.player.setVelocity(Math.cos(dir) * getMoveSpeed(), mc.player.getVelocity().y, -Math.sin(dir) * getMoveSpeed());
        }
    }
    public void strafe(double moveSpeed, float yaw, double strafe, double forward) {
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        mc.player.setVelocity(forward * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0F)), mc.player.getVelocity().y, forward * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0F)));
    }
}
