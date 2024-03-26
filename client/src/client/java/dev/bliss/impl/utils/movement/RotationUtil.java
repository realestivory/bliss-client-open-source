package dev.bliss.impl.utils.movement;

import dev.bliss.impl.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;


public class RotationUtil implements Utils {
     public static float[] getRotations(double x, double y, double z) {
          double diffX = x - mc.player.getX();
          double diffY = y - mc.player.getY();
          double diffZ = z - mc.player.getZ();
          double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
          float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
          float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
          return new float[]{mc.player.getYaw() + MathHelper.wrapDegrees(yaw - mc.player.getYaw()), mc.player.getPitch() + MathHelper.wrapDegrees(pitch - mc.player.getPitch())};
     }
     public static float[] getRotations(Entity entity) {
          return getRotations(entity.getX(), entity.getY(), entity.getZ());
     }
     public static void faceYawAndPitch(float yaw, float pitch) {
          mc.player.setYaw(yaw);
          mc.player.setPitch(pitch);
     }
     public static void faceEntity(Entity entity) {
          float[] rotations = getRotations(entity);
          faceYawAndPitch(rotations[0], rotations[1]);
     }
     public static void facePos(double x, double y, double z) {
          float[] rotations = getRotations(x, y, z);
          faceYawAndPitch(rotations[0], rotations[1]);
     }
     public static void faceBlock(BlockPos pos) {
          facePos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
     }
     public static void faceVec3d(Vec3d vec3d) {
          facePos(vec3d.getX(), vec3d.getY(), vec3d.getZ());
     }
     public static void faceEntityPacket(Entity entity) {
          float[] rotations = getRotations(entity);
          mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], rotations[1], mc.player.isOnGround()));
     }
     public static void facePosPacket(double x, double y, double z) {
          float[] rotations = getRotations(x, y, z);
          mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], rotations[1], mc.player.isOnGround()));
     }
}