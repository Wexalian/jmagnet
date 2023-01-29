package com.wexalian.jmagnet.plugins.temp;

import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.parser.MagnetParser;
import com.wexalian.jmagnet.provider.IMagnetProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class TempFileMagnetProvider implements IMagnetProvider {
    private static final Path MAGNETS_FILE = Path.of(System.getProperty("user.dir"), "..", "magnets", "magnets.txt");
    
    @Override
    public String getName() {
        return "Temp file";
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public Stream<Magnet> provideAll() {
        try {
            return Files.lines(MAGNETS_FILE).map(MagnetParser::parse);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return Stream.empty();
    }
    
    @Override
    public Stream<Magnet> provide(String query) {
        return provideAll();
    }
}
