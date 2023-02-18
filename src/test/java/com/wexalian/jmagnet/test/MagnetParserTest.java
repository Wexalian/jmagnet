package com.wexalian.jmagnet.test;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.api.Tracker;
import com.wexalian.jmagnet.api.provider.IMagnetProvider;
import com.wexalian.jmagnet.parser.MagnetParser;
import com.wexalian.jmagnet.tracker.TrackerCache;
import org.junit.jupiter.api.Test;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MagnetParserTest extends BaseTest{
    
    @Test
    void testParseMagnet() {
        Magnet magnet = Magnet.parse(TEST_MAGNET, TEST_MAGNET_INFO);
        
        System.out.println(magnet.getName());
    }
    
    @Test
    void testParseMagnets() {
        for (String magnetLink : LIST_MAGNET_LINKS_LARS) {
            Magnet.parse(magnetLink, MagnetInfo.of("test"));
        }
        
        for (String magnetLink : LIST_MAGNET_LINKS_COLIN) {
            Magnet.parse(magnetLink, MagnetInfo.of("test"));
        }
        
        System.out.println("total unique trackers: " + TrackerCache.getCachedTrackers());
        System.out.println("total magnets parsed: " + MagnetParser.PARSED);
        
        List<Tracker> trackers = new ArrayList<>(TrackerCache.getCachedTrackers());
        trackers.sort(Comparator.comparing(Tracker::uri));
        for (Tracker tracker : trackers) {
            System.out.println(URLDecoder.decode(tracker.uri(), StandardCharsets.UTF_8));
        }
    }
    
    @Test
    void testParseMagnetName() {
        MagnetParser.NameParseResult result = MagnetParser.parseName(TEST_MAGNET_NAME);
        
        assertTrue(result.isEpisode());
        assertFalse(result.isSeason());
        assertEquals(21, result.season());
        assertEquals(1, result.episode());
    }
    
    @Test
    void testParseMagnetNames() {
        List<String> seasons = new ArrayList<>();
        List<String> episodes = new ArrayList<>();
        List<String> unknown = new ArrayList<>();
        
        testParseNames(seasons, episodes, unknown);
        
        write(FILE_MAGNET_NAMES_SEASONS, seasons);
        write(FILE_MAGNET_NAMES_EPISODES, episodes);
        write(FILE_MAGNET_NAMES_UNKNOWN, unknown);
    }
    
    private static void testParseNames(List<String> seasons, List<String> episodes, List<String> unknown) {
        Iterable<IMagnetProvider> providers = PluginLoader.load(IMagnetProvider.class);
        
        for (IMagnetProvider provider : providers) {
            for (Magnet magnet : provider.recommended(0)) {
                MagnetInfo info = magnet.getInfo();
                String formattedName = info.getFormattedName();
                
                if (info.isSeason()) seasons.add(formattedName);
                else if (info.isEpisode()) episodes.add(formattedName);
                else unknown.add(formattedName);
            }
        }
        
        System.out.println("parse_name_results:");
        System.out.println("\tseasons: " + seasons.size());
        System.out.println("\tepisodes: " + episodes.size());
        System.out.println("\tunknown: " + unknown.size());
    }
}
