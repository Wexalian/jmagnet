package com.wexalian.jmagnet.test.old;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.jmagnet.api.Tracker;
import com.wexalian.jmagnet.api.provider.ITrackerProvider;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ServiceLoader;

@Disabled
public class TrackerProviderTest extends BaseTest{
    @Test
    void testLoadTrackers() {
        PluginLoader<ITrackerProvider> providers = PluginLoader.load(ITrackerProvider.class, ServiceLoader::load);
        for (ITrackerProvider provider : providers) {
            long start = System.currentTimeMillis();
            List<Tracker> trackers = provider.load();
            long time = System.currentTimeMillis() - start;
            
            System.out.println("Provider '" + provider.getName() + "' has loaded " + trackers.size() + " trackers in " + time + " ms");
            
            trackers.forEach(System.out::println);
        }
    }
}
