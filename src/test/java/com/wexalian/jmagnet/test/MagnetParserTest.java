package com.wexalian.jmagnet.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.wexalian.jmagnet.test.BaseTest.MagnetListData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MagnetParserTest extends BaseTest {
    @BeforeAll
    static void initData() throws IOException {
        MagnetListData.init();
    }
    
    @Test
    void testMagnets() {
        assertEquals(9857, MAGNET_LINKS_LARS.size());
        assertEquals(5, MAGNET_LINKS_COLIN.size());
        
        assertEquals(9862, MAGNET_NAMES_PARSED.size());
        
        assertEquals(1305, MAGNET_NAMES_PARSED_SEASON.size());
        assertEquals(6303, MAGNET_NAMES_PARSED_EPISODE.size());
        assertEquals(2254, MAGNET_NAMES_PARSED_OTHER.size());
    }
}
