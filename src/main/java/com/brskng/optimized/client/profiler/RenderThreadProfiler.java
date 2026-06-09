package com.brskng.optimized.client.profiler;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public final class RenderThreadProfiler {
    private static volatile Thread renderThread;
    private static final AtomicBoolean RUNNING = new AtomicBoolean(false);
    private static final List<StackTraceElement[]> SAMPLES = new ArrayList<>();

    private static long startedAtMs = 0L;
    private static int intervalMs = 10;
    private static int requestedSeconds = 60;

    public static void captureRenderThread(Thread thread) {
        if (thread != null) {
            renderThread = thread;
        }
    }

    public static boolean isRunning() {
        return RUNNING.get();
    }

    public static void start(int seconds, int interval) {
        if (RUNNING.get()) {
            message("Render thread profiler is already running.");
            return;
        }

        Thread target = renderThread;
        if (target == null) {
            message("Render thread is not captured yet. Open a world and try again.");
            return;
        }

        requestedSeconds = Math.max(1, seconds);
        intervalMs = Math.max(1, interval);
        startedAtMs = System.currentTimeMillis();

        synchronized (SAMPLES) {
            SAMPLES.clear();
        }

        RUNNING.set(true);

        Thread sampler = new Thread(() -> runSampler(target), "brskngs-optimized-render-thread-profiler");
        sampler.setDaemon(true);
        sampler.start();

        message("Started render thread profiler for " + requestedSeconds + "s, interval " + intervalMs + "ms.");
    }

    public static void stop() {
        if (!RUNNING.get()) {
            message("Render thread profiler is not running.");
            return;
        }

        RUNNING.set(false);
        message("Stopping render thread profiler...");
    }

    public static void status() {
        if (!RUNNING.get()) {
            message("Render thread profiler is not running.");
            return;
        }

        long elapsed = (System.currentTimeMillis() - startedAtMs) / 1000L;
        int sampleCount;

        synchronized (SAMPLES) {
            sampleCount = SAMPLES.size();
        }

        message("Render profiler running: " + elapsed + "s / " + requestedSeconds + "s, samples=" + sampleCount);
    }

    private static void runSampler(Thread target) {
        long endAt = System.currentTimeMillis() + requestedSeconds * 1000L;

        while (RUNNING.get() && System.currentTimeMillis() < endAt) {
            StackTraceElement[] trace = target.getStackTrace();

            if (trace != null && trace.length > 0) {
                synchronized (SAMPLES) {
                    SAMPLES.add(trace);
                }
            }

            try {
                Thread.sleep(intervalMs);
            } catch (InterruptedException ignored) {
                break;
            }
        }

        RUNNING.set(false);

        try {
            Path output = writeReport();
            message("Render thread profile saved: " + output.toAbsolutePath());
        } catch (IOException e) {
            message("Failed to save render thread profile: " + e.getMessage());
        }
    }

    private static Path writeReport() throws IOException {
        Minecraft mc = Minecraft.getInstance();

        Path dir = mc.gameDirectory.toPath()
                .resolve("brskngs-optimized")
                .resolve("render-profiles");

        Files.createDirectories(dir);

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        Path file = dir.resolve("render-thread-" + time + ".txt");

        List<StackTraceElement[]> samples;

        synchronized (SAMPLES) {
            samples = new ArrayList<>(SAMPLES);
        }

        Map<String, Integer> frameCounts = new HashMap<>();
        Map<String, Integer> traceCounts = new HashMap<>();

        for (StackTraceElement[] trace : samples) {
            StringBuilder traceKey = new StringBuilder();

            for (int i = 0; i < Math.min(trace.length, 48); i++) {
                StackTraceElement element = trace[i];
                String frame = element.getClassName() + "." + element.getMethodName();
                frameCounts.merge(frame, 1, Integer::sum);

                traceKey.append(frame)
                        .append("(")
                        .append(element.getFileName())
                        .append(":")
                        .append(element.getLineNumber())
                        .append(")\n");
            }

            traceCounts.merge(traceKey.toString(), 1, Integer::sum);
        }

        StringBuilder out = new StringBuilder();

        out.append("brskng's optimized Render Thread Profile\n");
        out.append("=======================================\n\n");
        out.append("Duration seconds: ").append(requestedSeconds).append("\n");
        out.append("Interval ms: ").append(intervalMs).append("\n");
        out.append("Samples: ").append(samples.size()).append("\n");
        out.append("Generated: ").append(time).append("\n\n");

        out.append("Top stack frames\n");
        out.append("----------------\n");

        frameCounts.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(80)
                .forEach(entry -> out.append(String.format("%8d  %s%n", entry.getValue(), entry.getKey())));

        out.append("\n\nTop full traces\n");
        out.append("---------------\n");

        traceCounts.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(20)
                .forEach(entry -> {
                    out.append("\n--- trace count: ")
                            .append(entry.getValue())
                            .append(" ---\n");
                    out.append(entry.getKey());
                });

        Files.writeString(file, out.toString(), StandardCharsets.UTF_8);
        return file;
    }

    private static void message(String text) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null) {
            mc.player.displayClientMessage(Component.literal("[brskng's optimized] " + text), false);
        }
    }

    private RenderThreadProfiler() {}
}
