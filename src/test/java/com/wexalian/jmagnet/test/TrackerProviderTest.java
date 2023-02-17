package com.wexalian.jmagnet.test;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.jmagnet.api.Tracker;
import com.wexalian.jmagnet.api.ITrackerProvider;
import com.wexalian.jmagnet.tracker.TrackerCache;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;

public class TrackerProviderTest {
    
    public static final Path PATH = Path.of(System.getProperty("user.dir"), "..", "jmagnet-plugin-test/build/libs");
    
    @BeforeAll
    static void init() {
        PluginLoader.init(ServiceLoader::load);
        PluginLoader.loadPlugins(PATH);
    }
    @Test
    void testLoadTrackers() {
        Iterable<ITrackerProvider> providers = PluginLoader.load(ITrackerProvider.class);
        for (ITrackerProvider provider : providers) {
            long start = System.currentTimeMillis();
            List<Tracker> trackers = provider.load();
            long time = System.currentTimeMillis() - start;
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + trackers.size() + " trackers in " + time + " ms");
            
            trackers.forEach(System.out::println);
        }
    }
    
    @AfterAll
    static void shutdown() {
        TrackerCache.save();
    }
}
