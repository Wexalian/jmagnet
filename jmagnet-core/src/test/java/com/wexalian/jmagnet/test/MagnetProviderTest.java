package com.wexalian.jmagnet.test;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.jmagnet.provider.IMagnetProvider;
import com.wexalian.jmagnet.provider.SearchOptions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;

public class MagnetProviderTest {
    
    public static final Path PATH = Path.of(System.getProperty("user.dir"), "..", "jmagnet-plugin-test/build/libs");
    public static final SearchOptions SEARCH_OPTIONS = new SearchOptions("tt10234724", "moon_knight", -1, -1);
    
    private final PluginLoader pluginLoader = PluginLoader.init(PATH, ServiceLoader::load);
    
    @Test
    void testLoadProvidersSearchAll() {
        List<IMagnetProvider> providers = pluginLoader.loadPlugins(IMagnetProvider.class);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            long count = provider.searchAll().size();
            long time = System.currentTimeMillis() - start;
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + count + " magnets in " + time + " ms");
        }
    }
    
    @Test
    void testLoadProvidersSearchOption() {
        List<IMagnetProvider> providers = pluginLoader.loadPlugins(IMagnetProvider.class);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            long count = provider.search(SEARCH_OPTIONS).size();
            long time = System.currentTimeMillis() - start;
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + count + " magnets in " + time + " ms");
        }
    }
}
