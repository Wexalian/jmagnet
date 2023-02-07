package com.wexalian.jmagnet.plugins.temp;

import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.parser.MagnetParser;
import com.wexalian.jmagnet.provider.IMagnetProvider;
import com.wexalian.jmagnet.provider.SearchOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class TempFileMagnetProvider implements IMagnetProvider {
    private static final Path MAGNETS_FILE = Path.of(System.getProperty("user.dir"), "..", "magnets", "magnets.txt");
    
    @Override
    public String getName() {
        return "Temp file";
    }
    
    @Override
    public List<Magnet> searchAll() {
        try (Stream<String> magnetLinks = Files.lines(MAGNETS_FILE).parallel()){
            return magnetLinks.map(MagnetParser::parse).toList();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return List.of();
    }
    
    @Override
    public List<Magnet> search(SearchOptions options) {
        return List.of();
    }
}
