package com.wexalian.jmagnet.tracker;

import com.wexalian.jmagnet.Tracker;

import java.util.HashSet;
import java.util.Set;

public final class GlobalTrackers {
    private static final Set<Tracker> GLOBAL_TRACKERS = new HashSet<>();
    
    public static Set<Tracker> getGlobalTrackers() {
        return Set.copyOf(GLOBAL_TRACKERS);
    }
    
    public static void addTracker(Tracker tracker) {
        GLOBAL_TRACKERS.add(tracker);
    }
    
    public static boolean hasTracker(Tracker tracker) {
        return GLOBAL_TRACKERS.contains(tracker);
    }
    
    public static boolean hasTracker(String tracker) {
        return GLOBAL_TRACKERS.stream().map(Tracker::uri).anyMatch(tracker::equals);
    }
}
