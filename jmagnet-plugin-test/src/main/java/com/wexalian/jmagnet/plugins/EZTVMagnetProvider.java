package com.wexalian.jmagnet.plugins;

import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.provider.IMagnetProvider;
import com.wexalian.jmagnet.provider.SearchOptions;
import com.wexalian.nullability.annotations.Nonnull;

import java.util.List;

public class EZTVMagnetProvider implements IMagnetProvider {
    @Nonnull
    public String getName() {
        return "EZTV";
    }
    
    @Override
    public List<Magnet> searchAll() {
        return List.of();
    }
    
    @Override
    public List<Magnet> search(SearchOptions options) {
        return List.of();
    }
}
