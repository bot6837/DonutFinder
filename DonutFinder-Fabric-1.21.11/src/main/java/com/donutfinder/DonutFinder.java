package com.donutfinder;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class DonutFinder implements ClientModInitializer {
    private static boolean enabled = true;
    private static boolean previousF8 = false;
    private static int ticks = 0;
    private static final Set<String> logged = new HashSet<>();

    @Override public void onInitializeClient() { }

    public static void onTick(MinecraftClient client) {
        if (client.player == null || client.world == null || client.currentScreen != null) return;
        long handle = client.getWindow().getHandle();
        boolean f8 = GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_F8) == GLFW.GLFW_PRESS;
        if (f8 && !previousF8) {
            enabled = !enabled;
            client.player.sendMessage(Text.literal("[DonutFinder] Scanning " + (enabled ? "ON" : "OFF")), false);
        }
        previousF8 = f8;
        if (!enabled || ++ticks % 100 != 0) return;
        int cx = client.player.getBlockX() >> 4;
        int cz = client.player.getBlockZ() >> 4;
        for (int x = cx - 4; x <= cx + 4; x++) {
            for (int z = cz - 4; z <= cz + 4; z++) {
                WorldChunk chunk = client.world.getChunkManager().getWorldChunk(x, z);
                if (chunk == null) continue;
                int storage = 0;
                BlockPos first = null;
                for (BlockEntity entity : chunk.getBlockEntities().values()) {
                    if (entity instanceof ChestBlockEntity || entity instanceof BarrelBlockEntity || entity instanceof ShulkerBoxBlockEntity) {
                        storage++;
                        if (first == null) first = entity.getPos();
                    }
                }
                if (storage < 3 || first == null) continue;
                String key = client.world.getRegistryKey().getValue() + ":" + x + ":" + z;
                if (!logged.add(key)) continue;
                String entry = Instant.now() + " " + key + " storage=" + storage + " near=" + first.toShortString();
                client.player.sendMessage(Text.literal("[DonutFinder] Storage cluster (" + storage + ") near " + first.toShortString()), false);
                try {
                    Path file = client.runDirectory.toPath().resolve("config").resolve("donutfinder-locations.txt");
                    Files.createDirectories(file.getParent());
                    Files.writeString(file, entry + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    client.player.sendMessage(Text.literal("[DonutFinder] Could not save coordinates: " + e.getMessage()), false);
                }
            }
        }
    }
}
