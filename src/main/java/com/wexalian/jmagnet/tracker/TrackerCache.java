package com.wexalian.jmagnet.tracker;

import com.wexalian.common.collection.util.ListUtilNew;
import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.api.Tracker;
import com.wexalian.jmagnet.api.provider.ITrackerProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Stream;

public final class TrackerCache {
    public static final Path DEFAULT_TRACKER_CACHE_PATH = Path.of(System.getProperty("user.dir"), "trackers.cache");
    
    private static final Set<Tracker> CACHED_TRACKERS = new HashSet<>();
    private static final Set<Tracker> NEW_TRACKERS = new HashSet<>();
    
    public static void onCustomTrackersProvided(Collection<Tracker> trackers) {
        CACHED_TRACKERS.addAll(trackers);
    }
    
    public static void onMagnetLoaded(Magnet magnet) {
        NEW_TRACKERS.addAll(magnet.getTrackers());
    }
    
    public static Set<Tracker> getCachedTrackers() {
        return Set.copyOf(CACHED_TRACKERS);
    }
    
    public static void load() {
        load(DEFAULT_TRACKER_CACHE_PATH);
    }
    
    public static void load(Path path) {
        loadTrackerCache(path);
        loadCustomTrackerProviders();
    }
    
    private static void loadTrackerCache(Path path) {
        try {
            try (Stream<String> lines = Files.lines(path)) {
                lines.map(Tracker::new).forEach(CACHED_TRACKERS::add);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Error loading tracker cache from file " + path.toAbsolutePath().normalize(), e);
        }
    }
    
    private static void loadCustomTrackerProviders() {
        PluginLoader.load(ITrackerProvider.class, ServiceLoader::load).forEach(ITrackerProvider::load);
    }
    
    public static void save() {
        if (!NEW_TRACKERS.isEmpty()) {
            CACHED_TRACKERS.addAll(NEW_TRACKERS);
            NEW_TRACKERS.clear();
            
            try {
                Files.write(DEFAULT_TRACKER_CACHE_PATH, ListUtilNew.map(CACHED_TRACKERS, Tracker::uri));
            }
            catch (IOException e) {
                throw new RuntimeException("Error saving tracker cache to file " + DEFAULT_TRACKER_CACHE_PATH.toAbsolutePath().normalize(), e);
            }
        }
    }
}
