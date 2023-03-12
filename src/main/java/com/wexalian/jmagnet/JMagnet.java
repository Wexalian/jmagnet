package com.wexalian.jmagnet;

import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.parser.MagnetParser;

import java.nio.file.Path;

public class JMagnet {
    private static final Path DEFAULT_BASE_DIR = Path.of(System.getProperty("user.dir"));
    
    private static final Path CONFIG_FILE = Path.of("jmagnet.json");
    private static final Path DOWNLOAD_DIR = Path.of("downloads");
    
    private static Path jMagnetBaseDir;
    
    public static boolean init = false;
    
    public static void init() {
        init(DEFAULT_BASE_DIR);
    }
    
    public static void init(Path baseDir) {
        if (!init) {
            jMagnetBaseDir = baseDir;
            
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
}
