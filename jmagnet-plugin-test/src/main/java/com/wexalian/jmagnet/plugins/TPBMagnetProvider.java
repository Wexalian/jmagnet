package com.wexalian.jmagnet.plugins;

import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.provider.IMagnetProvider;

import java.util.stream.Stream;

public class TPBMagnetProvider implements IMagnetProvider {
    @Override
    public String getName() {
        return "ThePirateBay";
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
