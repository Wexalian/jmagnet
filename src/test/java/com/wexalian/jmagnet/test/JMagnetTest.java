package com.wexalian.jmagnet.test;

import com.wexalian.jmagnet.JMagnet;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.api.Magnet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JMagnetTest extends BaseTest {
    
    @BeforeAll
    static void init() {
        JMagnet.init();
    }
    
    @Test
    void test() {
        Magnet magnet = JMagnet.parse(MAGNET_URI_THE_FLASH_9X05);
        
        assertMagnet(magnet);
        assertMagnetInfo(magnet.getInfo());
    }
    
    private static void assertMagnet(Magnet magnet) {
        assertNotNull(magnet, "magnet is null");
        assertEquals("urn:btih:C965FB0AE42A4624D714BC1F81013C62D31CEB59", magnet.getUrn(), "magnet urn doesnt match");
        assertEquals("The.Flash.2014.S09E05.HDTV.x264-TORRENTGALAXY", magnet.getName(), "magnet name doesnt match");
        assertEquals(8, magnet.getTrackers().size(), "magnet trackers size doesnt match");
        for (int i = 0; i < 8; i++) {
            assertNotNull(magnet.getTrackers().get(i).uri(), "magnet tracker " + i + " has no uri");
        }
    }
    
    private void assertMagnetInfo(MagnetInfo info) {
        assertNotNull(info.getProvider(), "magnet provider is null");
        
        assertEquals("The Flash 2014 S09E05 HDTV x264 TORRENTGALAXY", info.getFormattedName(), "magnet formatted name doesnt match");
        assertCategory(MagnetInfo.Category.Common.ALL, info.getCategory());
        assertCategory(MagnetInfo.Category.Common.VIDEO, info.getCategory());
        assertCategory(MagnetInfo.Category.Common.TV_SHOWS, info.getCategory());
        
        assertEquals(9, info.getSeason());
        assertEquals(5, info.getEpisode());
    }
    
    private void assertCategory(MagnetInfo.Category expected, MagnetInfo.Category actual) {
        assertTrue(actual.isIn(expected), "category " + actual + " is not in " + expected);
    }
}
