package com.wexalian.jmagnet.impl.tracker;

import com.wexalian.jmagnet.api.Tracker;
import com.wexalian.jmagnet.api.provider.ITrackerProvider;
import com.wexalian.jmagnet.tracker.TrackerCache;
import com.wexalian.nullability.annotations.Nonnull;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

public class TrackerCacheProvider implements ITrackerProvider {
    @Nonnull
    @Override
    public String getName() {
        return "Tracker Cache";
    }
    
    @Override
    public List<Tracker> load() {
        if (Files.exists(TrackerCache.DEFAULT_TRACKER_CACHE_PATH)) {
            try (Stream<String> lines = Files.lines(TrackerCache.DEFAULT_TRACKER_CACHE_PATH)) {
                return lines.map(Tracker::new).toList();
            }
            catch (IOException e) {
                throw new RuntimeException("Error loading tracker cache: ", e);
            }
        }
        return List.of();
    }
}
