package com.wexalian.jmagnet.impl.tracker;

import com.wexalian.jmagnet.Tracker;
import com.wexalian.jmagnet.api.ITrackerProvider;
import com.wexalian.nullability.annotations.Nonnull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FileCacheTrackerProvider implements ITrackerProvider {
    private static final Path FILE_PATH = Path.of(System.getProperty("user.dir"), "trackers.list");
    
    @Nonnull
    @Override
    public String getName() {
        return "File Cache";
    }
    
    @Override
    public List<Tracker> load() {
        if (Files.exists(FILE_PATH)) {
            try (Stream<String> lines = Files.lines(FILE_PATH)) {
                return lines.map(Tracker::new).toList();
            }
            catch (IOException e) {
                throw new RuntimeException("Error loading file cache: ", e);
            }
        }
        return List.of();
    }
}
