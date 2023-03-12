package com.wexalian.jmagnet.test;

import com.wexalian.jmagnet.JMagnet;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.MagnetInfo.Resolution;
import com.wexalian.jmagnet.api.Magnet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JMagnetTest extends TestBase {
    
    // @BeforeAll
    // static void init() {
    //     JMagnet.init();
    // }
    
    @Test
    void testParseMagnet() {
        Magnet magnet = JMagnet.parse(TheFlash9x05.MAGNET_URI, MagnetInfo.of(TheFlash9x05.MAGNET_PROVIDER));
        assertMagnet(TheFlash9x05.TEST_MAGNET, magnet);
        
        printMagnet(magnet);
    }
    
    @Test
    void testMagnetResolution() {
        Magnet magnetHdtv = JMagnet.parse(TheFlash9x05.MAGNET_URI);
        Magnet magnet720p = JMagnet.parse(TheFlash9x05.MAGNET_URI_720P);
        Magnet magnet1080p = JMagnet.parse(TheFlash9x05.MAGNET_URI_1080P);
        
        assertEquals(magnetHdtv.getInfo().getResolution(), Resolution.HDTV);
        assertEquals(magnetHdtv.getInfo().getResolution(), Resolution._720P);
        
        assertEquals(magnet720p.getInfo().getResolution(), Resolution._720P);
        assertEquals(magnet1080p.getInfo().getResolution(), Resolution._1080P);
        
        assertNotEquals(magnet720p.getInfo().getResolution(), Resolution._1080P);
        assertNotEquals(magnet1080p.getInfo().getResolution(), Resolution.HDTV);
    }
}
