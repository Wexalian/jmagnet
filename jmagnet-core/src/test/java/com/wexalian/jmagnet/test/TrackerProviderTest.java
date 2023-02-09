package com.wexalian.jmagnet.test;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.jmagnet.api.ITrackerProvider;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;

public class TrackerProviderTest {
    
    public static final Path PATH = Path.of(System.getProperty("user.dir"), "..", "jmagnet-plugin-test/build/libs");
    
    private final PluginLoader pluginLoader = PluginLoader.init(PATH, ServiceLoader::load);
    
    @Test
    void testLoadTrackers() {
        List<ITrackerProvider> providers = pluginLoader.loadPlugins(ITrackerProvider.class);
        for (ITrackerProvider provider : providers) {
            long start = System.currentTimeMillis();
            long count = provider.load().size();
            long time = System.currentTimeMillis() - start;
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + count + " trackers in " + time + " ms");
        }
    }
}
