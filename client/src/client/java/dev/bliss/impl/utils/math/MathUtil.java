package dev.bliss.impl.utils.math;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    public static float getRandomFloat(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }
    public static double getRandomDouble(double min, double max) {
        SecureRandom random = new SecureRandom();
        return random.nextDouble() * (max - min) + min;
    }
    public static int wrapAngleToDirection(final float yaw, final int zones) {
        int angle = Math.floorMod((int) (yaw + 360 / (2 * zones) + 0.5), 360);
        return angle / (360 / zones);
    }
    public static int getRandomInt(int max, int min) {
        return random.nextInt(min, max + 1);
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static float lerp(float point1, float point2, float alpha) {
        return point1 + alpha * (point2 - point1);
    }

    public static float map(float value, float start1, float stop1, float start2, float stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }
}
