package com.wexalian.jmagnet.test;

import com.wexalian.jmagnet.JMagnet;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.api.Magnet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JMagnetTest extends TestBase {
    
    @BeforeAll
    static void init() {
        JMagnet.init();
    }
    
    @Test
    void test() {
        Magnet magnet = JMagnet.parse(TheFlash9x05.MAGNET_URI, MagnetInfo.of(TheFlash9x05.MAGNET_PROVIDER));
        
        assertMagnet(THE_FLASH_MAGNET, magnet);
        
        printMagnet(magnet);
    }
}
