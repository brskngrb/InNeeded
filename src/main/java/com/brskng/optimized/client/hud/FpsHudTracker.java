package com.brskng.optimized.client.hud;

import java.util.Arrays;

public final class FpsHudTracker {
    private static final int SAMPLE_SIZE = 240;
    private static final long[] frameTimesNs = new long[SAMPLE_SIZE];
    private static int index = 0;
    private static int count = 0;
    private static long lastFrameTimeNs = 0L;
    private static double currentFps = 0.0;
    private static double averageFps = 0.0;
    private static double onePercentLowFps = 0.0;

    public static void frame() {
        long now = System.nanoTime();
        if (lastFrameTimeNs == 0L) { lastFrameTimeNs = now; return; }
        long delta = now - lastFrameTimeNs;
        lastFrameTimeNs = now;
        if (delta <= 0L) return;
        frameTimesNs[index] = delta;
        index = (index + 1) % SAMPLE_SIZE;
        if (count < SAMPLE_SIZE) count++;
        currentFps = 1_000_000_000.0 / delta;
        recomputeStats();
    }

    private static void recomputeStats() {
        if (count <= 0) { averageFps = 0.0; onePercentLowFps = 0.0; return; }
        long total = 0L;
        for (int i = 0; i < count; i++) total += frameTimesNs[i];
        averageFps = 1_000_000_000.0 / (total / (double) count);
        long[] copy = Arrays.copyOf(frameTimesNs, count);
        Arrays.sort(copy);
        int percentileIndex = (int) Math.floor(count * 0.99);
        percentileIndex = Math.max(0, Math.min(percentileIndex, count - 1));
        long onePercentFrameNs = copy[percentileIndex];
        onePercentLowFps = onePercentFrameNs <= 0L ? 0.0 : 1_000_000_000.0 / onePercentFrameNs;
    }

    public static int currentFps() { return (int) Math.round(currentFps); }
    public static int averageFps() { return (int) Math.round(averageFps); }
    public static int onePercentLowFps() { return (int) Math.round(onePercentLowFps); }
    private FpsHudTracker() {}
}
