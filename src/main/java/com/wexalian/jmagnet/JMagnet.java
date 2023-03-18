package com.wexalian.jmagnet;

import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.config.JMagnetConfig;
import com.wexalian.jmagnet.parser.MagnetParser;
import com.wexalian.jmagnet.tracker.TrackerCache;

import java.io.IOException;
import java.nio.file.Path;

public class JMagnet {
    private static final Path DEFAULT_BASE_DIR = Path.of(System.getProperty("user.dir"));
    
    private static final Path CONFIG_FILE = Path.of("jmagnet.json");
    private static final Path TRACKER_CACHE_FILE = Path.of("trackers.cache");
    private static final Path DOWNLOAD_DIR = Path.of("downloads");
    
    private static Path jMagnetBaseDir;
    
    public static boolean init = false;
    
    public static void init() throws IOException {
        init(DEFAULT_BASE_DIR);
    }
    
    public static void init(Path baseDir) throws IOException {
        if (!init) {
            jMagnetBaseDir = baseDir;
            
            JMagnetConfig.load(jMagnetBaseDir.resolve(CONFIG_FILE));
            TrackerCache.load(jMagnetBaseDir.resolve(TRACKER_CACHE_FILE));
            
            init = true;
        }
        else throw new IllegalStateException("JMagnet can only be initialized once");
    }
    
    public static Magnet parse(String magnetUri) {
        if (init) {
            return MagnetParser.parse(magnetUri);
        }
        else throw new IllegalStateException("JMagnet has to be initialized first");
    }
    
    public static Magnet parse(String magnetUri, MagnetInfo defaultInfo) {
        if (init) {
            return MagnetParser.parse(magnetUri, defaultInfo);
        }
        else throw new IllegalStateException("JMagnet has to be initialized first");
    }
    
    public static void download(Magnet magnet) {
        if (init) {
        
        }
        else throw new IllegalStateException("JMagnet has to be initialized first");
    }
    
    public static void save() throws IOException {
        if (init) {
            JMagnetConfig.save();
            TrackerCache.save();
        }
        else throw new IllegalStateException("JMagnet has to be initialized first");
    }
}
