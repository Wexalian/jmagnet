package com.wexalian.jmagnet.test;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.common.unchecked.Unchecked;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.parser.MagnetParser;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class BaseTest {
    protected static final Path PLUGIN_PATH = Path.of(System.getProperty("user.dir"), "plugins");
    
    protected static final String TEST_MAGNET = "magnet:?xt=urn:btih:CCD685F5E1FC274CA019D42D01559B20778C4924&dn=Family%20Guy%20S21E01%201080p%20WEB%20H264-CAKES&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2920%2Fannounce&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=udp%3A%2F%2Ftracker.internetwarriors.net%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.pirateparty.gr%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.cyberia.is%3A6969%2Fannounce";
    protected static final String TEST_MAGNET_NAME = "Family%20Guy%20S21E01%201080p%20WEB%20H264-CAKES";
    
    protected static final MagnetInfo TEST_MAGNET_INFO = MagnetInfo.of("test");
    
    static final boolean WRITE_FILES = true;
    static final boolean OVERWRITE_FILES = false;
    
    static final Path FILE_MAGNET_LINKS_LARS = Path.of("magnets/magnets_lars.txt");
    static final Path FILE_MAGNET_LINKS_COLIN = Path.of("magnets/magnets_colin.txt");
    
    static final Path FILE_MAGNET_NAMES_PARSED = Path.of("magnets/magnet_names_parsed.txt");
    static final Path FILE_MAGNET_NAMES_SEASONS = Path.of("magnets/magnet_names_seasons.txt");
    static final Path FILE_MAGNET_NAMES_EPISODES = Path.of("magnets/magnet_names_episodes.txt");
    static final Path FILE_MAGNET_NAMES_UNKNOWN = Path.of("magnets/magnet_names_unknown.txt");
    
    protected static List<String> LIST_MAGNET_LINKS_LARS;
    protected static List<String> LIST_MAGNET_LINKS_COLIN;
    protected final static List<String> LIST_MAGNET_NAMES_PARSED = new ArrayList<>();
    
    private static boolean init = false;
    
    @BeforeAll
    static void init() {
        if (!init) {
            loadMagnetDataFiles();
            initPluginLoader();
            init = true;
        }
    }
    
    private static void loadMagnetDataFiles() {
        try {
            LIST_MAGNET_LINKS_LARS = read(FILE_MAGNET_LINKS_LARS);
            LIST_MAGNET_LINKS_COLIN = read(FILE_MAGNET_LINKS_COLIN);
            
            LIST_MAGNET_NAMES_PARSED.addAll(read(FILE_MAGNET_NAMES_PARSED));
            
            if ((OVERWRITE_FILES || LIST_MAGNET_NAMES_PARSED.isEmpty()) && !LIST_MAGNET_LINKS_LARS.isEmpty()) {
                
                
                for (String magnetUri : LIST_MAGNET_LINKS_LARS) {
                    MagnetParser.NameParseResult result = MagnetParser.parseName(magnetUri);
                    LIST_MAGNET_NAMES_PARSED.add(result.formattedName());
                }
                write(FILE_MAGNET_NAMES_PARSED, LIST_MAGNET_NAMES_PARSED);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Error loading magnet data in BaseTest", e);
        }
    }
    
    private static void initPluginLoader() {
        PluginLoader.init(ServiceLoader::load);
        PluginLoader.loadPlugins(PLUGIN_PATH);
    }
    
    public static void write(Path path, List<String> lines) {
        if (WRITE_FILES && (OVERWRITE_FILES || Files.notExists(path))) {
            Unchecked.apply(path, lines, Files::write);
        }
    }
    
    public static List<String> read(Path path) throws IOException {
        return Files.exists(path) ? Files.readAllLines(path) : List.of();
    }
}
