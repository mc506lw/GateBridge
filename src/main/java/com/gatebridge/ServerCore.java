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

        setupGateBinary(gateBinary);
        setupConfigFile(gateConfig);

        System.out.println("[ServerCore] Starting Gate binary...");
        startGate(gateBinary, gateConfig);
    }

    private static void setupGateBinary(File gateBinary) throws IOException {
        if (gateBinary.exists()) {
            System.out.println("[ServerCore] Gate binary already exists: " + gateBinary.getPath());
            return;
        }

        System.out.println("[ServerCore] Gate binary not found, attempting to extract from resources...");
        boolean extracted = extractResource("/" + GATE_BINARY_NAME, gateBinary.getPath());

        if (!extracted) {
            throw new FileNotFoundException(
                    "Gate binary not found in resources. " +
                            "Please place " + GATE_BINARY_NAME
                            + " in src/main/resources/ directory and rebuild the JAR.");
        }

        if (!gateBinary.setExecutable(true, false)) {
            System.err.println("[ServerCore] Warning: Failed to set executable permission for 'gate'.");
        }
    }

    private static void setupConfigFile(File gateConfig) throws IOException {
        if (!gateConfig.exists()) {
            System.out.println("[ServerCore] Extracting default config: " + CONFIG_NAME);
            boolean extracted = extractResource("/" + CONFIG_NAME, gateConfig.getPath());
            if (!extracted) {
                throw new FileNotFoundException("Cannot find config resource: " + CONFIG_NAME);
            }
        } else {
            System.out.println("[ServerCore] Config file exists, skipping extraction.");
        }
    }

    private static void startGate(File gateBinary, File gateConfig) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(gateBinary.getPath(), "-c", gateConfig.getPath());
        pb.directory(new File(System.getProperty("user.dir")));
        pb.inheritIO();

        Process process = pb.start();
        int exitCode = process.waitFor();

        System.out.println("[ServerCore] Gate process terminated with code: " + exitCode);
    }

    private static boolean extractResource(String resourcePath, String destinationPath) throws IOException {
        try (InputStream in = ServerCore.class.getResourceAsStream(resourcePath);
                OutputStream out = new FileOutputStream(destinationPath)) {

            if (in == null) {
                return false;
            }

            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            return true;
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
