package com.wexalian.jmagnet.plugins;

import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.provider.IMagnetProvider;
import com.wexalian.nullability.annotations.Nonnull;

import java.util.stream.Stream;

public class EZTVMagnetProvider implements IMagnetProvider {
    @Nonnull
    public String getName(){
        return "EZTV";
    }
    @Override
    public Stream<Magnet> provideAll() {
        return Stream.empty();
    }
    
    @Override
    public Stream<Magnet> provide(String query) {
        return Stream.empty();
    }
    
    
}
