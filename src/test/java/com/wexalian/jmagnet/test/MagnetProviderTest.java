package com.wexalian.jmagnet.test;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.jmagnet.api.provider.IMagnetProvider;
import com.wexalian.jmagnet.api.SearchOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ServiceLoader;

public class MagnetProviderTest {
    
    public static final Path PATH = Path.of(System.getProperty("user.dir"), "..", "jmagnet-plugin-test/build/libs");
    public static final SearchOptions SEARCH_OPTIONS = SearchOptions.paged("tt10234724", "moon_knight", -1, -1, -1, 10);
    
    @BeforeAll
    static void init() {
        PluginLoader.init(ServiceLoader::load);
        PluginLoader.loadPlugins(PATH);
    }
    
    @Test
    void testLoadProvidersSearchAll() {
        Iterable<IMagnetProvider> providers = PluginLoader.load(IMagnetProvider.class);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            long count = provider.recommended(0).size();
            long time = System.currentTimeMillis() - start;
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + count + " magnets in " + time + " ms");
        }
    }
    
    @Test
    void testLoadProvidersSearchOption() {
        Iterable<IMagnetProvider> providers = PluginLoader.load(IMagnetProvider.class);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            long count = provider.search(SEARCH_OPTIONS).size();
            long time = System.currentTimeMillis() - start;
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + count + " magnets in " + time + " ms");
        }
    }
}
