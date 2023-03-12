package com.wexalian.jmagnet.test;

import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.MagnetMap;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.api.Tracker;

import static com.wexalian.jmagnet.MagnetInfo.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestBase {
    private static final String MAGNET_PRINT_TEMPLATE = """
                                                        magnet:
                                                        \turn={urn}
                                                        \tname={name}
                                                        \ttrackers={count}
                                                        \t{trackers}
                                                        \tprovider={provider}
                                                        \tformatted={formatted}
                                                        \tcategory={category}
                                                        \tpeers={peers}
                                                        \tseeds={peers}
                                                        \tisSeason={isSeason}
                                                        \tisEpisode={isEpisode}
                                                        \tseason={season}
                                                        \tepisode={episode}
                                                        """;
    private static final String MAGNET_PRINT_TRACKER_TEMPLATE = "uri={uri}";
    
    private static final MagnetInfo THE_FLASH_MAGNET_INFO = MagnetInfo.builder(TheFlash9x05.MAGNET_PROVIDER)
                                                                      .setFormattedName(TheFlash9x05.MAGNET_FORMATTED_NAME)
                                                                      .setCategory(TheFlash9x05.MAGNET_CATEGORY)
                                                                      .setPeers(TheFlash9x05.MAGNET_PEERS)
                                                                      .setSeeds(TheFlash9x05.MAGNET_SEEDS)
                                                                      .setSeason(TheFlash9x05.MAGNET_SEASON)
                                                                      .setEpisode(TheFlash9x05.MAGNET_EPISODE)
                                                                      .build();
    protected static final Magnet THE_FLASH_MAGNET = new Magnet(MagnetMap.build(m -> {
        m.addParameter(Magnet.Parameter.DISPLAY_NAME, "The.Flash.2014.S09E05.HDTV.x264-TORRENTGALAXY");
        m.addParameter(Magnet.Parameter.EXACT_TOPIC, "urn:btih:C965FB0AE42A4624D714BC1F81013C62D31CEB59");
        
        m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce");
        m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2F9.rarbg.to%3A2920%2Fannounce");
        m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.opentrackr.org%3A1337");
        m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.internetwarriors.net%3A1337%2Fannounce");
        m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce");
        m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce");
        m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.pirateparty.gr%3A6969%2Fannounce");
        m.addParameter(Magnet.Parameter.TRACKERS, "udp%3A%2F%2Ftracker.cyberia.is%3A6969%2Fannounce");
    }), THE_FLASH_MAGNET_INFO);
    
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
        assertEquals(expected.getCategory(), actual.getCategory(), "magnet formatted name doesnt match");
        
        assertEquals(expected.isSeason(), actual.isSeason(), "magnet is season doesnt match");
        assertEquals(expected.isEpisode(), actual.isEpisode(), "magnet is episode  doesnt match");
        
        assertEquals(expected.getSeason(), actual.getSeason());
        assertEquals(expected.getEpisode(), actual.getEpisode());
        
        assertEquals(expected.getPeers(), actual.getPeers());
        assertEquals(expected.getSeeds(), actual.getSeeds());
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
        text.append("\tpeers=").append(magnet.getInfo().getPeers()).append("\n");
        text.append("\tseeds=").append(magnet.getInfo().getSeeds()).append("\n");
        text.append("\tisSeason=").append(magnet.getInfo().isSeason()).append("\n");
        text.append("\tisEpisode=").append(magnet.getInfo().isEpisode()).append("\n");
        text.append("\tseason=").append(magnet.getInfo().getSeason()).append("\n");
        text.append("\tepisode=").append(magnet.getInfo().getEpisode()).append("\n");
    
        System.out.println(text);
    }
    
    protected static class TheFlash9x05 {
        protected static final String MAGNET_URI = "magnet:?xt=urn:btih:C965FB0AE42A4624D714BC1F81013C62D31CEB59&dn=The.Flash.2014.S09E05.HDTV.x264-TORRENTGALAXY&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2920%2Fannounce&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=udp%3A%2F%2Ftracker.internetwarriors.net%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.pirateparty.gr%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.cyberia.is%3A6969%2Fannounce";
        protected static final String MAGNET_PROVIDER = "JMagnetTest";
        
        protected static final Category MAGNET_CATEGORY = Category.TV_SHOWS;
        protected static final String MAGNET_FORMATTED_NAME = "The Flash 2014 S09E05 HDTV x264 TORRENTGALAXY";
        
        protected static final int MAGNET_PEERS = -1;
        protected static final int MAGNET_SEEDS = -1;
        protected static final int MAGNET_SEASON = 9;
        protected static final int MAGNET_EPISODE = 5;
    }
}
