package com.wexalian.jmagnet.config;

import com.wexalian.config.ConfigHandler;

import java.io.IOException;
import java.nio.file.Path;

public class JMagnetConfig {
    private static final ConfigHandler CONFIG = ConfigHandler.create("jmagnet");
    
    // public static final ListConfigProperty<String> EPISODE_PATTERNS = CONFIG.createListProperty("episode_patterns", new TypeToken<>() {});
    // public static final ListConfigProperty<String> SEASON_PATTERNS = CONFIG.createListProperty("season_patterns", new TypeToken<>() {});
    
    public static void load(Path path) throws IOException {
        CONFIG.load(path);
    }
    
    public static void save() throws IOException {
        CONFIG.save();
    }
}
