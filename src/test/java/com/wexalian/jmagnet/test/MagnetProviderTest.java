package com.wexalian.jmagnet.test;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.jmagnet.api.provider.SearchOptions;
import com.wexalian.jmagnet.api.provider.IMagnetProvider;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

public class MagnetProviderTest/* extends BaseTest*/{
    public static final SearchOptions SEARCH_OPTIONS = SearchOptions.paged("tt10234724", "moon_knight", -1, -1, -1, 10);
    
    @Test
    void testLoadProvidersSearchAll() {
        PluginLoader<IMagnetProvider> providers = PluginLoader.load(IMagnetProvider.class, ServiceLoader::load);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            long count = provider.recommended(0).size();
            long time = System.currentTimeMillis() - start;
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + count + " magnets in " + time + " ms");
        }
    }
    
    @Test
    void testLoadProvidersSearchOption() {
        PluginLoader<IMagnetProvider> providers = PluginLoader.load(IMagnetProvider.class, ServiceLoader::load);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            long count = provider.search(SEARCH_OPTIONS).size();
            long time = System.currentTimeMillis() - start;
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + count + " magnets in " + time + " ms");
        }
    }
}
