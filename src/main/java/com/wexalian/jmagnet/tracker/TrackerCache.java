package com.wexalian.jmagnet.tracker;

import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.common.util.FilesUtil;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.api.Tracker;
import com.wexalian.jmagnet.api.provider.ITrackerProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public final class TrackerCache {
    private static final Set<Tracker> CACHED_TRACKERS = new TreeSet<>(Comparator.comparing(Tracker::uri));
    private static final Set<Tracker> NEW_TRACKERS = new HashSet<>();
    
    private static boolean dirty;
    private static Path cachePath;
    
    public static void load(Path path) {
        cachePath = path;
        
        loadTrackerCache(path);
        loadTrackersFromProvidersProviders();
    }
    
    private static void loadTrackerCache(Path path) {
        try {
            CACHED_TRACKERS.addAll(FilesUtil.read(path, Tracker::new));
        }
        catch (IOException e) {
            throw new RuntimeException("Error loading tracker cache from file " + path.toAbsolutePath().normalize(), e);
        }
    }
    
    private static void loadTrackersFromProvidersProviders() {
        PluginLoader<ITrackerProvider> providers = PluginLoader.load(ITrackerProvider.class, ServiceLoader::load);
        for (ITrackerProvider provider : providers) {
            if (CACHED_TRACKERS.addAll(provider.load())) {
                dirty = true;
            }
        }
    }
    
    public static void onMagnetLoaded(Magnet magnet) {
        for (Tracker tracker : magnet.getTrackers()) {
            if (!CACHED_TRACKERS.contains(tracker)) {
                NEW_TRACKERS.add(tracker);
            }
        }
    }
    
    public static Set<Tracker> getCachedTrackers() {
        return new TreeSet<>(CACHED_TRACKERS);
    }
    
    public static void save() {
        if (!NEW_TRACKERS.isEmpty()) {
            CACHED_TRACKERS.addAll(NEW_TRACKERS);
            NEW_TRACKERS.clear();
            dirty = true;
        }
        
        if (dirty) {
            try {
                FilesUtil.write(cachePath, CACHED_TRACKERS, Tracker::uri);
                dirty = false;
            }
            catch (IOException e) {
                throw new RuntimeException("Error saving tracker cache to file " + cachePath.toAbsolutePath().normalize(), e);
            }
        }
    }
}
