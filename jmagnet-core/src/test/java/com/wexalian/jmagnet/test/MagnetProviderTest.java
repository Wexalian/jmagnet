package com.wexalian.jmagnet.test;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.jmagnet.provider.IMagnetProvider;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;

public class MagnetProviderTest {
    public static final Path PATH = Path.of(System.getProperty("user.dir"), "..", "jmagnet-plugin-test/build/libs");
    
    @Test
    void testLoadProviders() {
        List<IMagnetProvider> providers = PluginLoader.loadPlugins(PATH, IMagnetProvider.class, ServiceLoader::load);
        for (IMagnetProvider provider : providers) {
            long start = System.currentTimeMillis();
            long count = provider.provideAll().count();
            long time = System.currentTimeMillis() - start;
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + count + " magnets in " + time + " ms");
        }
    }
}
