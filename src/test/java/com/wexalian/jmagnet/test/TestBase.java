package com.wexalian.jmagnet.test;

import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.MagnetInfo.Resolution;
import com.wexalian.jmagnet.MagnetMap;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.api.Tracker;
import com.wexalian.jmagnet.parser.MagnetParser;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.wexalian.jmagnet.MagnetInfo.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestBase {
    
    @BeforeAll
    static void init() throws IOException {
        MagnetData.init();
    }
    
    protected static void assertMagnet(Magnet expected, Magnet actual) {
        assertNotNull(actual);
        
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getTrackers(), actual.getTrackers());
        
        for (Magnet.Parameter value : Magnet.Parameter.values()) {
            assertEquals(expected.getValue(value), actual.getValue(value), "magnet parameter doesnt match");
        }
        
        assertMagnetInfo(expected.getInfo(), actual.getInfo());
    }
    
    protected static void assertMagnetInfo(MagnetInfo expected, MagnetInfo actual) {
        assertEquals(expected.getProvider(), actual.getProvider(), "magnet provider doesnt match");
        assertEquals(expected.getFormattedName(), actual.getFormattedName(), "magnet formatted name doesnt match");
        assertEquals(expected.getCategory(), actual.getCategory(), "magnet category doesnt match");
        assertEquals(expected.getResolution(), actual.getResolution(), "magnet resolution doesnt match");
        
        assertEquals(expected.isSeason(), actual.isSeason(), "magnet is season doesnt match");
        assertEquals(expected.isEpisode(), actual.isEpisode(), "magnet is episode  doesnt match");
        
        assertEquals(expected.getSeason(), actual.getSeason(), "magnet season doesnt match");
        assertEquals(expected.getEpisode(), actual.getEpisode(), "magnet episode doesnt match");
        
        assertEquals(expected.getPeers(), actual.getPeers(), "magnet peers doesnt match");
        assertEquals(expected.getSeeds(), actual.getSeeds(), "magnet seeds doesnt match");
    }
    
    protected static void printMagnet(Magnet magnet) {
        StringBuilder text = new StringBuilder();
        
        text.append("magnet:").append("\n");
        text.append("\turn=").append(magnet.getUrn()).append("\n");
        text.append("\tname=").append(magnet.getName()).append("\n");
        text.append("\ttrackers=").append(magnet.getTrackers().size()).append("\n");
        
        for (Tracker tracker : magnet.getTrackers()) {
            text.append("\t\turi=").append(tracker.uri()).append("\n");
        }
        
        text.append("\tprovider=").append(magnet.getInfo().getProvider()).append("\n");
        text.append("\tformatted=").append(magnet.getInfo().getFormattedName()).append("\n");
        text.append("\tcategory=").append(magnet.getInfo().getCategory()).append("\n");
        text.append("\tresolution=").append(magnet.getInfo().getResolution().getName()).append("\n");
        text.append("\tpeers=").append(magnet.getInfo().getPeers()).append("\n");
        text.append("\tseeds=").append(magnet.getInfo().getSeeds()).append("\n");
        text.append("\tisSeason=").append(magnet.getInfo().isSeason()).append("\n");
        text.append("\tisEpisode=").append(magnet.getInfo().isEpisode()).append("\n");
        text.append("\tseason=").append(magnet.getInfo().getSeason()).append("\n");
        text.append("\tepisode=").append(magnet.getInfo().getEpisode()).append("\n");
        
        System.out.println(text);
    }
    
    protected static class MagnetData {
        protected static final Path MAGNET_DIR = Path.of("magnets");
        
        protected static final Path MAGNET_FILE_LARS = MAGNET_DIR.resolve("magnets_lars.txt");
        protected static final Path MAGNET_FILE_COLIN = MAGNET_DIR.resolve("magnets_colin.txt");
        protected static final Path MAGNET_FILE_PARSED = MAGNET_DIR.resolve("magnet_names_parsed.txt");
        
        protected static List<String> MAGNET_LINKS_LARS;
        protected static List<String> MAGNET_LINKS_COLIN;
        protected static List<String> MAGNET_LINKS_PARSED;
        
        protected static void init() throws IOException {
            MAGNET_LINKS_LARS = read(MAGNET_FILE_LARS);
            MAGNET_LINKS_COLIN = read(MAGNET_FILE_COLIN);
            
            MAGNET_LINKS_PARSED = read(MAGNET_FILE_PARSED);
            // MAGNET_LINKS_SEASONS = read(MAGNET_FILE_SEASONS);
            // MAGNET_LINKS_EPISODES = read(MAGNET_FILE_EPISODES);
            // MAGNET_LINKS_UNKNOWN = read(MAGNET_FILE_UNKNOWN);
            
            if (MAGNET_LINKS_PARSED.isEmpty()) {
                
                for (String magnetUri : MAGNET_LINKS_LARS) {
                    MagnetParser.NameParseResult result = MagnetParser.parseName(magnetUri);
                    
                    MAGNET_LINKS_PARSED.add(result.formattedName());
                }
                Files.write(MAGNET_FILE_LARS, MAGNET_LINKS_LARS);
            }
        }
        
        protected static List<String> read(Path path) throws IOException {
            if (Files.exists(path)) {
                return Files.readAllLines(path);
            }
            return List.of();
        }
    }
    
    protected static class TheFlash9x05 {
        protected static final String MAGNET_URI_HDTV = "magnet:?xt=urn:btih:C965FB0AE42A4624D714BC1F81013C62D31CEB59&dn=The%20Flash%202014%20S09E05%20HDTV%20x264-TORRENTGALAXY&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2920%2Fannounce&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=udp%3A%2F%2Ftracker.internetwarriors.net%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.pirateparty.gr%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.cyberia.is%3A6969%2Fannounce";
        protected static final String MAGNET_URI_720P = "magnet:?xt=urn:btih:536E0456E8C1C196FD7799773C5D22F7B79FF07C&dn=The.Flash.2014.S09E05.720p.HDTV.x265-MiNX[TGx]&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2920%2Fannounce&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=udp%3A%2F%2Ftracker.internetwarriors.net%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.pirateparty.gr%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.cyberia.is%3A6969%2Fannounce";
        protected static final String MAGNET_URI_1080P = "magnet:?xt=urn:btih:5B64AC7BA15FC8969110959A37E7B082B760C173&dn=The%20Flash%202014%20S09E05%201080p%20HDTV%20x264-ATOMOS&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2920%2Fannounce&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=udp%3A%2F%2Ftracker.internetwarriors.net%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.pirateparty.gr%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.cyberia.is%3A6969%2Fannounce";
        
        protected static final String MAGNET_PROVIDER = "JMagnetTest";
        
        protected static final String MAGNET_URI = MAGNET_URI_HDTV;
        
        protected static final String MAGNET_FORMATTED_NAME = "The Flash 2014 S09E05 HDTV x264 TORRENTGALAXY";
        protected static final Category MAGNET_CATEGORY = Category.TV_SHOWS;
        protected static final Resolution MAGNET_RESOLUTION = Resolution.HDTV;
        
        protected static final int MAGNET_PEERS = -1;
        protected static final int MAGNET_SEEDS = -1;
        protected static final int MAGNET_SEASON = 9;
        protected static final int MAGNET_EPISODE = 5;
        
        protected static final MagnetInfo TEST_MAGNET_INFO = MagnetInfo.builder(MAGNET_PROVIDER)
                                                                       .setFormattedName(MAGNET_FORMATTED_NAME)
                                                                       .setCategory(MAGNET_CATEGORY)
                                                                       .setResolution(MAGNET_RESOLUTION)
                                                                       .setPeers(MAGNET_PEERS)
                                                                       .setSeeds(MAGNET_SEEDS)
                                                                       .setSeason(MAGNET_SEASON)
                                                                       .setEpisode(MAGNET_EPISODE)
                                                                       .build();
        
        protected static final Magnet TEST_MAGNET = new Magnet(MagnetMap.build(m -> {
            m.addParameter(Magnet.Parameter.DISPLAY_NAME, "The%20Flash%202014%20S09E05%20HDTV%20x264-TORRENTGALAXY");
            m.addParameter(Magnet.Parameter.EXACT_TOPIC, "urn:btih:C965FB0AE42A4624D714BC1F81013C62D31CEB59");
            
            m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce");
            m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2F9.rarbg.to%3A2920%2Fannounce");
            m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.opentrackr.org%3A1337");
            m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.internetwarriors.net%3A1337%2Fannounce");
            m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce");
            m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce");
            m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.pirateparty.gr%3A6969%2Fannounce");
            m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.cyberia.is%3A6969%2Fannounce");
        }), TEST_MAGNET_INFO);
    }
}
