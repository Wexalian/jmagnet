package com.wexalian.jmagnet.test.old;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.common.stream.BiStream;
import com.wexalian.jmagnet.MagnetInfo.Category;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.api.provider.IMagnetProvider;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MagnetProviderTest/* extends TestBase*/ {
    private static final String IMDB_ID = "tt10234724";
    private static final String SLUG = "moon_knight";
    
    private static final String SEARCH = "moon knight";
    
    public static final Category SEARCH_CATEGORY = Category.TV_SHOWS;
    
    @Test
    void testRecent() {
        PluginLoader<IMagnetProvider> providers = PluginLoader.load(IMagnetProvider.class, ServiceLoader::load);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            List<Magnet> recent = provider.recent(SEARCH_CATEGORY, 1, -1);
            long time = System.currentTimeMillis() - start;
            
            printDebugStats(recent);
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + recent.size() + " recent magnets in " + time + " ms");
        }
    }
    
    @Test
    void testPopular() {
        PluginLoader<IMagnetProvider> providers = PluginLoader.load(IMagnetProvider.class, ServiceLoader::load);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            List<Magnet> popular = provider.popular(SEARCH_CATEGORY, 1, -1);
            long time = System.currentTimeMillis() - start;
            
            printDebugStats(popular);
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + popular.size() + " popular magnets in " + time + " ms");
        }
    }
    
    @Test
    void testShow() {
        PluginLoader<IMagnetProvider> providers = PluginLoader.load(IMagnetProvider.class, ServiceLoader::load);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            List<Magnet> shows = provider.show(IMDB_ID, SLUG, 1, -1);
            long time = System.currentTimeMillis() - start;
            
            printDebugStats(shows);
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + shows.size() + " show magnets in " + time + " ms");
        }
    }
    
    @Test
    void testSearch() {
        PluginLoader<IMagnetProvider> providers = PluginLoader.load(IMagnetProvider.class, ServiceLoader::load);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            List<Magnet> searched = provider.search(SEARCH_CATEGORY, SEARCH, 1, -1);
            long time = System.currentTimeMillis() - start;
            
            printDebugStats(searched);
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + searched.size() + " searched magnets in " + time + " ms");
        }
    }
    
    private void printDebugStats(List<Magnet> recent) {
        System.out.println("--- MAGNET CATEGORY TEST (" + recent.size() + ") ---");
        
        Map<Category, Integer> statMap = new HashMap<>();
        for (Magnet magnet : recent) {
            statMap.put(magnet.getCategory(), statMap.getOrDefault(magnet.getCategory(), 0) + 1);
        }
        
        BiStream.of(statMap)
                .sortedByValue(Comparator.comparing(Integer::intValue).reversed())
                .forEach((cat, count) -> System.out.println(cat.toString().toLowerCase() + ": " + count));
        
        System.out.println("---------------------------------");
    }
}
