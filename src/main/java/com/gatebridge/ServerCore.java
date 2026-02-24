package com.gatebridge;

import java.io.*;
import java.nio.file.*;

public class ServerCore {
    private static final String GATE_BINARY_NAME = isWindows() ? "gate.exe" : "gate";
    private static final String CONFIG_NAME = "gate.yml";

    public static void main(String[] args) throws Exception {
        String dir = System.getProperty("user.dir");
        File gateBinary = new File(dir, GATE_BINARY_NAME);
        File gateConfig = new File(dir, CONFIG_NAME);

        System.out.println("[ServerCore] Initializing environment...");
        System.out.println("[ServerCore] Working Directory: " + dir);

        if (!gateBinary.exists()) {
            throw new FileNotFoundException(
                    "Gate binary not found: " + gateBinary.getPath() + "\n" +
                            "Please download Gate from https://github.com/minekube/gate/releases\n" +
                            "and place it in the working directory.");
        }

        if (!gateConfig.exists()) {
            throw new FileNotFoundException(
                    "Gate config not found: " + gateConfig.getPath() + "\n" +
                            "Please create a gate.yml configuration file in the working directory.");
        }

        System.out.println("[ServerCore] Starting Gate binary...");
        startGate(gateBinary, gateConfig);
    }

    private static void startGate(File gateBinary, File gateConfig) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(gateBinary.getPath(), "-c", gateConfig.getPath());
        pb.directory(new File(System.getProperty("user.dir")));
        pb.inheritIO();

        Process process = pb.start();
        int exitCode = process.waitFor();

        System.out.println("[ServerCore] Gate process terminated with code: " + exitCode);
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
