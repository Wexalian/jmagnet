package com.wexalian.jmagnet.tracker;

import com.wexalian.common.collection.util.ListUtilNew;
import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.Tracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class TrackerCache {
    public static final Path FILE_PATH = Path.of(System.getProperty("user.dir"), "trackers.cache");
    
    private static final Set<Tracker> SAVED_TRACKERS = new HashSet<>();
    private static final Set<Tracker> NEW_TRACKERS = new HashSet<>();
    
    public static void onTrackersProvided(Collection<Tracker> trackers) {
        SAVED_TRACKERS.addAll(trackers);
    }
    
    public static void onMagnetLoaded(Magnet magnet) {
        NEW_TRACKERS.addAll(magnet.getTrackers());
    }
    
    public static Set<Tracker> getCachedTrackers() {
        return Set.copyOf(SAVED_TRACKERS);
    }
    
    public static void save() {
        if (!NEW_TRACKERS.isEmpty()) {
            SAVED_TRACKERS.addAll(NEW_TRACKERS);
            NEW_TRACKERS.clear();
            
            try {
                Files.write(FILE_PATH, ListUtilNew.map(SAVED_TRACKERS, Tracker::uri));
            }
            catch (IOException e) {
                throw new RuntimeException("Error saving trackers to file " + FILE_PATH.toAbsolutePath(), e);
            }
        }
    }
}
