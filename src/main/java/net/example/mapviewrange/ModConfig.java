package net.example.mapviewrange;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * config/mapviewrange.json に保存する設定。
 * radiusMultiplierは1.0〜4.0の範囲でクランプする。
 */
public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("mapviewrange.json");

    public static final double MIN_MULTIPLIER = 1.0;
    public static final double MAX_MULTIPLIER = 4.0;
    public static final double DEFAULT_MULTIPLIER = 2.0;

    public double radiusMultiplier = DEFAULT_MULTIPLIER;

    public static ModConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                ModConfig loaded = GSON.fromJson(reader, ModConfig.class);
                if (loaded != null) {
                    loaded.radiusMultiplier = clamp(loaded.radiusMultiplier);
                    return loaded;
                }
            } catch (IOException ignored) {
                // 読み込み失敗時はデフォルト値にフォールバックする
            }
        }
        ModConfig config = new ModConfig();
        config.save();
        return config;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException ignored) {
            // プロトタイプのため保存失敗は握りつぶす
        }
    }

    public static double clamp(double value) {
        return Math.max(MIN_MULTIPLIER, Math.min(MAX_MULTIPLIER, value));
    }
}
