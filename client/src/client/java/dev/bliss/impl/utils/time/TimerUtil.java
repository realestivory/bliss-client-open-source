package dev.bliss.impl.utils.time;

import dev.bliss.impl.utils.Utils;

public class TimerUtil implements Utils {
    private long lastNS = System.nanoTime();

    public void reset() {
        lastNS = System.nanoTime();
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        long elapsedNS = System.nanoTime() - lastNS;
        if (elapsedNS > time * 1_000_000) {
            if (reset) reset();
            return true;
        }
        return false;
    }

    public boolean hasTimeElapsed(long time) {
        return System.nanoTime() - lastNS > time * 1_000_000;
    }

    public boolean hasTimeElapsed(double time) {
        return hasTimeElapsed((long) time);
    }

    public long getTime() {
        return (System.nanoTime() - lastNS) / 1_000_000;
    }

    public void setTime(long time) {
        lastNS = time * 1_000_000;
    }
}
