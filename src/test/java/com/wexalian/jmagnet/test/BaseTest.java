package com.wexalian.jmagnet.test;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.parser.MagnetParser;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;

public class BaseTest {
    public static final Path PLUGIN_PATH = Path.of(System.getProperty("user.dir"), "..", "jmagnet-plugin-test/build/libs");
    
    public static final String TEST_MAGNET = "magnet:?xt=urn:btih:CCD685F5E1FC274CA019D42D01559B20778C4924&dn=Family%20Guy%20S21E01%201080p%20WEB%20H264-CAKES&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2920%2Fannounce&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=udp%3A%2F%2Ftracker.internetwarriors.net%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.pirateparty.gr%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.cyberia.is%3A6969%2Fannounce";
    public static final String TEST_MAGNET_NAME = "Family%20Guy%20S21E01%201080p%20WEB%20H264-CAKES";
    
    public static final MagnetInfo TEST_MAGNET_INFO = MagnetInfo.of("test");
    
    public static final boolean WRITE_FILES = true;
    public static final boolean OVERWRITE_FILES = false;
    
    public static final Path MAGNET_LINKS_FILE = Path.of("magnets/magnets.txt");
    public static final Path MAGNET_NAMES_FILE = Path.of("magnets/magnet_names.txt");
    public static final Path MAGNET_NAMES_FILE_SEASONS = Path.of("magnets/magnet_names_seasons.txt");
    public static final Path MAGNET_NAMES_FILE_EPISODES = Path.of("magnets/magnet_names_episodes.txt");
    public static final Path MAGNET_NAMES_FILE_UNKNOWN = Path.of("magnets/magnet_names_unknown.txt");
    
    public static final List<String> COLINS_TEST_MAGNETS = List.of(
        "magnet:?xt=urn:btih:69ec715399fc8130106a6cf86dca6d37cdfb193d&dn=1883.S01.Repack.ITA.ENG.1080p.BluRay.x264-MeM.GP&tr=udp%3a%2f%2ftracker.leechers-paradise.org%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.internetwarriors.net%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.opentrackr.org%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.coppersurfer.tk%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.pirateparty.gr%3a6969%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2730%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2710%2fannounce&tr=udp%3a%2f%2fbt.xxx-tracker.com%3a2710%2fannounce&tr=udp%3a%2f%2ftracker.cyberia.is%3a6969%2fannounce&tr=udp%3a%2f%2fretracker.lanta-net.ru%3a2710%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2770%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2730%2fannounce&tr=udp%3a%2f%2feddie4.nl%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.mg64.net%3a6969%2fannounce&tr=udp%3a%2f%2fopen.demonii.si%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.zer0day.to%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.tiny-vps.com%3a6969%2fannounce&tr=udp%3a%2f%2fipv6.tracker.harry.lu%3a80%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2740%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2770%2fannounce&tr=udp%3a%2f%2fdenis.stalker.upeer.me%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.port443.xyz%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.moeking.me%3a6969%2fannounce&tr=udp%3a%2f%2fexodus.desync.com%3a6969%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2740%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2720%2fannounce&tr=udp%3a%2f%2ftracker.justseed.it%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.torrent.eu.org%3a451%2fannounce&tr=udp%3a%2f%2fipv4.tracker.harry.lu%3a80%2fannounce&tr=udp%3a%2f%2ftracker.open-internet.nl%3a6969%2fannounce&tr=udp%3a%2f%2ftorrentclub.tech%3a6969%2fannounce&tr=udp%3a%2f%2fopen.stealth.si%3a80%2fannounce&tr=http%3a%2f%2ftracker.tfile.co%3a80%2fannounce",
        "magnet:?xt=urn:btih:aea265ff7c50845eff0f49e4f2ce4c50e78a2a10&dn=McFarland%2c%20USA%20(2015)%20(1080p%20BluRay%20x265%20HEVC%2010bit%20AAC%205.1%20Tigole)&tr=udp%3a%2f%2ftracker.coppersurfer.tk%3a80%2fannounce&tr=udp%3a%2f%2ftracker.pirateparty.gr%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.internetwarriors.net%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.opentrackr.org%3a1337%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2730%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2740%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2720%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2710%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2740%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2770%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2710%2fannounce&tr=udp%3a%2f%2ftracker.torrent.eu.org%3a451&tr=udp%3a%2f%2ftracker.port443.xyz%3a6969%2fannounce&tr=udp%3a%2f%2fIPv6.open-internet.nl%3a6969%2fannounce&tr=udp%3a%2f%2fp4p.arenabg.ch%3a1337%2fannounce&tr=udp%3a%2f%2fdenis.stalker.upeer.me%3a1337%2fannounce&tr=udp%3a%2f%2fshadowshq.yi.org%3a6969%2fannounce&tr=udp%3a%2f%2feddie4.nl%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.mg64.net%3a6969%2fannounce&tr=udp%3a%2f%2finferno.demonoid.pw%3a3418%2fannounce&tr=udp%3a%2f%2ftracker.cyberia.is%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.zer0day.to%3a1337%2fannounce&tr=udp%3a%2f%2fasnet.pw%3a2710%2fannounce&tr=udp%3a%2f%2fipv6.tracker.harry.lu%3a80%2fannounce&tr=udp%3a%2f%2fglotorrents.pw%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.torrent.eu.org%3a451%2fannounce&tr=udp%3a%2f%2fopen.demonii.si%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.qt.is%3a6969%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2770%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2730%2fannounce&tr=udp%3a%2f%2ftracker.ds.is%3a6969%2fannounce&tr=udp%3a%2f%2fexodus.desync.com%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.tiny-vps.com%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.justseed.it%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.coppersurfer.tk%3a6969%2fannounce&tr=udp%3a%2f%2fthetracker.org%3a80%2fannounce&tr=udp%3a%2f%2ftracker.vanitycore.co%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.cypherpunks.ru%3a6969%2fannounce&tr=udp%3a%2f%2fipv4.tracker.harry.lu%3a80%2fannounce&tr=udp%3a%2f%2ftracker.open-internet.nl%3a6969%2fannounce&tr=udp%3a%2f%2fpublic.popcorn-tracker.org%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.0o.is%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.trackerfix.com%3a80%2fannounce&tr=udp%3a%2f%2fbt.xxx-tracker.com%3a2710%2fannounce&tr=udp%3a%2f%2fopen.stealth.si%3a80%2fannounce&tr=http%3a%2f%2fshare.camoe.cn%3a8080%2fannounce&tr=https%3a%2f%2fopen.acgnxtracker.com%3a443%2fannounce&tr=http%3a%2f%2ftracker.tfile.co%3a80%2fannounce&tr=http%3a%2f%2fretracker.spb.ru%3a80%2fannounce&tr=http%3a%2f%2fopen.acgnxtracker.com%2fannounce&tr=http%3a%2f%2fbt.acg.gg%3a1578%2fannounce&tr=http%3a%2f%2ftracker3.itzmx.com%3a8080%2fannounce&tr=udp%3a%2f%2ftracker.acg.gg%3a2710%2fannounce&tr=udp%3a%2f%2ftracker.leechers-paradise.org%3a6969%2fannounce&tr=udp%3a%2f%2fretracker.lanta-net.ru%3a2710%2fannounce&tr=udp%3a%2f%2fdenis.stalker.upeer.me%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.moeking.me%3a6969%2fannounce&tr=udp%3a%2f%2ftorrentclub.tech%3a6969%2fannounce",
        "magnet:?xt=urn:btih:b5fba7c6263f3f23a10df97964d57e66a91015af&dn=Divergent.2014.1080p.BluRay.x264.DTS-X.7.1-SWTYBLZ&tr=http%3a%2f%2ftracker.trackerfix.com%3a80%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2820&tr=udp%3a%2f%2f9.rarbg.to%3a2820",
        "magnet:?xt=urn:btih:8766b2d8afff68e7d7cad44efb602bd891755546&dn=Police%20Academy%201984%20(1080p%20Bluray%20x265%20HEVC%2010bit%20AAC%201.0%20Tigole)&tr=udp%3a%2f%2ftracker.coppersurfer.tk%3a80%2fannounce&tr=udp%3a%2f%2ftracker.pirateparty.gr%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.internetwarriors.net%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.opentrackr.org%3a1337%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2730%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2740%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2720%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2710%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2740%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2770%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2710%2fannounce&tr=udp%3a%2f%2ftracker.leechers-paradise.org%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.torrent.eu.org%3a451&tr=udp%3a%2f%2fIPv6.open-internet.nl%3a6969%2fannounce&tr=udp%3a%2f%2fp4p.arenabg.ch%3a1337%2fannounce&tr=udp%3a%2f%2fdenis.stalker.upeer.me%3a1337%2fannounce&tr=udp%3a%2f%2fshadowshq.yi.org%3a6969%2fannounce&tr=udp%3a%2f%2feddie4.nl%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.mg64.net%3a6969%2fannounce&tr=udp%3a%2f%2finferno.demonoid.pw%3a3418%2fannounce&tr=udp%3a%2f%2ftracker.cyberia.is%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.zer0day.to%3a1337%2fannounce&tr=udp%3a%2f%2fasnet.pw%3a2710%2fannounce&tr=udp%3a%2f%2fipv6.tracker.harry.lu%3a80%2fannounce&tr=udp%3a%2f%2fglotorrents.pw%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.torrent.eu.org%3a451%2fannounce&tr=udp%3a%2f%2ftracker.port443.xyz%3a6969%2fannounce&tr=udp%3a%2f%2fopen.demonii.si%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.qt.is%3a6969%2fannounce&tr=udp%3a%2f%2f9.rarbg.to%3a2770%2fannounce&tr=udp%3a%2f%2f9.rarbg.me%3a2730%2fannounce&tr=udp%3a%2f%2ftracker.ds.is%3a6969%2fannounce&tr=udp%3a%2f%2fexodus.desync.com%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.tiny-vps.com%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.justseed.it%3a1337%2fannounce&tr=udp%3a%2f%2ftracker.coppersurfer.tk%3a6969%2fannounce&tr=udp%3a%2f%2fthetracker.org%3a80%2fannounce&tr=udp%3a%2f%2ftracker.vanitycore.co%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.cypherpunks.ru%3a6969%2fannounce&tr=udp%3a%2f%2fipv4.tracker.harry.lu%3a80%2fannounce&tr=udp%3a%2f%2ftracker.open-internet.nl%3a6969%2fannounce&tr=udp%3a%2f%2fpublic.popcorn-tracker.org%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.0o.is%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.trackerfix.com%3a80%2fannounce&tr=udp%3a%2f%2fbt.xxx-tracker.com%3a2710%2fannounce&tr=udp%3a%2f%2fopen.stealth.si%3a80%2fannounce&tr=http%3a%2f%2fshare.camoe.cn%3a8080%2fannounce&tr=https%3a%2f%2fopen.acgnxtracker.com%3a443%2fannounce&tr=http%3a%2f%2ftracker.tfile.co%3a80%2fannounce&tr=http%3a%2f%2fretracker.spb.ru%3a80%2fannounce&tr=http%3a%2f%2fopen.acgnxtracker.com%2fannounce&tr=http%3a%2f%2fbt.acg.gg%3a1578%2fannounce&tr=http%3a%2f%2ftracker3.itzmx.com%3a8080%2fannounce&tr=udp%3a%2f%2ftracker.acg.gg%3a2710%2fannounce&tr=udp%3a%2f%2fretracker.lanta-net.ru%3a2710%2fannounce&tr=udp%3a%2f%2fdenis.stalker.upeer.me%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.moeking.me%3a6969%2fannounce&tr=udp%3a%2f%2ftorrentclub.tech%3a6969%2fannounce",
        "magnet:?xt=urn:btih:771d3cfac55a6f0503723ae9fd010748dc783312&dn=Too%20Late.mp3&tr=udp%3a%2f%2ftracker.opentrackr.org%3a1337%2fannounce&tr=http%3a%2f%2fp4p.arenabg.com%3a1337%2fannounce&tr=udp%3a%2f%2f9.rarbg.com%3a2810%2fannounce&tr=udp%3a%2f%2ftracker.openbittorrent.com%3a6969%2fannounce&tr=udp%3a%2f%2fwww.torrent.eu.org%3a451%2fannounce&tr=udp%3a%2f%2ftracker.torrent.eu.org%3a451%2fannounce&tr=udp%3a%2f%2fretracker.lanta-net.ru%3a2710%2fannounce&tr=udp%3a%2f%2fopen.stealth.si%3a80%2fannounce&tr=udp%3a%2f%2fexodus.desync.com%3a6969%2fannounce&tr=http%3a%2f%2fopenbittorrent.com%3a80%2fannounce&tr=udp%3a%2f%2fopentracker.i2p.rocks%3a6969%2fannounce&tr=udp%3a%2f%2fopentor.org%3a2710%2fannounce&tr=udp%3a%2f%2fipv4.tracker.harry.lu%3a80%2fannounce&tr=udp%3a%2f%2ftracker.uw0.xyz%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.moeking.me%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.dler.org%3a6969%2fannounce&tr=udp%3a%2f%2fexplodie.org%3a6969%2fannounce&tr=udp%3a%2f%2fbt2.archive.org%3a6969%2fannounce&tr=udp%3a%2f%2fbt1.archive.org%3a6969%2fannounce&tr=https%3a%2f%2ftrakx.herokuapp.com%3a443%2fannounce");
    
    public static List<String> TEST_MAGNET_LINKS;
    public static List<String> TEST_MAGNET_NAMES;
    
    private static boolean init = false;
    
    @BeforeAll
    static void init() {
        if (!init) {
            initMagnetData();
            initPluginLoader();
            init = true;
        }
    }
    
    private static void initMagnetData() {
        try {
            if (Files.exists(MAGNET_LINKS_FILE)) {
                TEST_MAGNET_LINKS = Files.readAllLines(MAGNET_LINKS_FILE);
            }
            // else throw new IllegalStateException("Magnet file doesn't exist: " + MAGNET_LINKS_FILE.toAbsolutePath());
            
            if (Files.exists(MAGNET_NAMES_FILE)) {
                TEST_MAGNET_NAMES = Files.readAllLines(MAGNET_NAMES_FILE);
            }
            
            if (TEST_MAGNET_NAMES == null || TEST_MAGNET_NAMES.isEmpty()) {
                if (TEST_MAGNET_LINKS != null && !TEST_MAGNET_LINKS.isEmpty()) {
                    TEST_MAGNET_NAMES = TEST_MAGNET_LINKS.stream()
                                                         .map(MagnetParser::parse)
                                                         .map(Magnet::getName)
                                                         .map(MagnetParser::parseName)
                                                         .map(MagnetParser.NameParseResult::formattedName)
                                                         .toList();
                    writeLinesToFile(MAGNET_NAMES_FILE, TEST_MAGNET_NAMES);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static void initPluginLoader() {
        PluginLoader.init(ServiceLoader::load);
        PluginLoader.loadPlugins(PLUGIN_PATH);
    }
    
    protected void write(Path path, List<String> lines) {
        BaseTest.writeLinesToFile(path, lines);
    }
    
    private static void writeLinesToFile(Path path, List<String> lines) {
        try {
            if (WRITE_FILES && (OVERWRITE_FILES || Files.notExists(path))) {
                Files.write(path, lines);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}