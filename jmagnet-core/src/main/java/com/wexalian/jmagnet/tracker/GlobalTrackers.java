package com.wexalian.jmagnet.tracker;

import com.wexalian.jmagnet.Tracker;
import com.wexalian.jmagnet.api.ITrackerProvider;

import java.util.*;
import java.util.stream.Collectors;

public final class GlobalTrackers {
    private static final Map<ITrackerProvider, List<Tracker>> PROVIDER_TRACKERS = new HashMap<>();
    private static final List<Tracker> CUSTOM_TRACKERS = new ArrayList<>();
    
    public static List<Tracker> getAllTrackers() {
        List<Tracker> trackers = PROVIDER_TRACKERS.values().stream().flatMap(List::stream).collect(Collectors.toList());
        trackers.addAll(CUSTOM_TRACKERS);
        return trackers;
    }
    
    public static void onTrackersLoaded(ITrackerProvider provider, Collection<Tracker> trackers) {
        PROVIDER_TRACKERS.computeIfAbsent(provider, p -> new ArrayList<>()).addAll(trackers);
    }
    
    public static void onTrackersLoaded(Collection<Tracker> trackers) {
        List<Tracker> allTrackers = getAllTrackers();
        
        for (Tracker tracker : trackers) {
            if (!allTrackers.contains(tracker)) {
                CUSTOM_TRACKERS.add(tracker);
            }
        }
    }
    
    public static void onTrackerUrisLoaded(Collection<String> trackers) {
        onTrackersLoaded(trackers.stream().map(Tracker::new).toList());
    }
}
