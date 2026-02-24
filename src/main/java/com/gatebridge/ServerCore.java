package com.gatebridge;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.*;

public class ServerCore {
    private static final String GATE_VERSION = "1.2.5";
    private static final String GATE_BINARY_NAME = isWindows() ? "gate.exe" : "gate";
    private static final String CONFIG_NAME = "gate.yml";
    
    private static final String[] GITHUB_MIRRORS = {
        "https://gh-proxy.org/",
        "https://v6.gh-proxy.org/",
        "https://cdn.gh-proxy.org/",
        "https://edgeone.gh-proxy.org/",
        "https://hk.gh-proxy.org/"
    };

    public static void main(String[] args) throws Exception {
        String dir = System.getProperty("user.dir");
        File gateBinary = new File(dir, GATE_BINARY_NAME);
        File gateConfig = new File(dir, CONFIG_NAME);

        System.out.println("[ServerCore] Initializing environment...");
        System.out.println("[ServerCore] Working Directory: " + dir);
        System.out.println("[ServerCore] Gate Version: " + GATE_VERSION);

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
            System.out.println("[ServerCore] Gate binary not found in resources, downloading from GitHub...");
            downloadGate(gateBinary);
        }

        if (!gateBinary.setExecutable(true, false)) {
            System.err.println("[ServerCore] Warning: Failed to set executable permission for 'gate'.");
        }
    }

    private static void downloadGate(File gateBinary) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch").toLowerCase();

        String platform;
        if (os.contains("win")) {
            platform = "windows";
        } else if (os.contains("mac")) {
            platform = "darwin";
        } else {
            platform = "linux";
        }

        if (arch.contains("64")) {
            platform += "-amd64";
        } else {
            platform += "-386";
        }

        String originalUrl = String.format(
            "https://github.com/minekube/gate/releases/download/v%s/gate-%s-%s.exe",
            GATE_VERSION, GATE_VERSION, platform
        );

        if (!os.contains("win")) {
            originalUrl = originalUrl.replace(".exe", "");
        }

        IOException lastException = null;
        
        for (String mirror : GITHUB_MIRRORS) {
            String downloadUrl = mirror + originalUrl;
            System.out.println("[ServerCore] Trying mirror: " + mirror);
            System.out.println("[ServerCore] Downloading Gate from: " + downloadUrl);
            
            try {
                URL url = new URL(downloadUrl);
                try (ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                     FileOutputStream fos = new FileOutputStream(gateBinary)) {
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                }
                System.out.println("[ServerCore] Gate downloaded successfully from " + mirror);
                return;
            } catch (Exception e) {
                System.err.println("[ServerCore] Failed to download from " + mirror + ": " + e.getMessage());
                lastException = new IOException("Failed to download from " + mirror, e);
                if (gateBinary.exists()) {
                    gateBinary.delete();
                }
            }
        }
        
        throw new IOException("Failed to download Gate from all mirrors", lastException);
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
