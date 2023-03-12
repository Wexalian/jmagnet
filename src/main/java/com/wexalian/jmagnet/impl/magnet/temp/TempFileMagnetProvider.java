package com.wexalian.jmagnet.impl.magnet.temp;

import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.api.provider.IMagnetProvider;
import com.wexalian.nullability.annotations.Nonnull;

import java.nio.file.Path;
import java.util.Set;

public class TempFileMagnetProvider implements IMagnetProvider {
    private static final Path MAGNETS_FILE = Path.of(System.getProperty("user.dir"), "magnets", "magnets_lars.txt");
    public static final Set<MagnetInfo.Category> SUPPORTED = Set.of(MagnetInfo.Category.ALL);
    
    @Override
    public String getName() {
        return "Temp debug file";
    }
    
    // @Nonnull
    // @Override
    // public List<Magnet> popular(int page, int limit) {
    //     try (Stream<String> magnetLinks = Files.lines(MAGNETS_FILE).parallel()) {
    //         return magnetLinks.map(MagnetParser::parse).toList();
    //     }
    //     catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return List.of();
    // }
    
    @Nonnull
    @Override
    public Set<MagnetInfo.Category> supported() {
        return SUPPORTED;
    }
    
    @Override
    public boolean isEnabled() {
        return false;
    }
}
