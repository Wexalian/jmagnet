package com.wexalian.jmagnet.impl.magnet.eztv;

import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.api.IMagnetProvider;
import com.wexalian.jmagnet.api.SearchOptions;
import com.wexalian.nullability.annotations.Nonnull;

import java.util.List;

public class EZTVMagnetProvider implements IMagnetProvider {
    @Nonnull
    public String getName() {
        return "EZTV";
    }
    
    @Override
    public List<Magnet> recommended(int page) {
        return List.of();
    }
    
    @Override
    public List<Magnet> search(SearchOptions options) {
        return List.of();
    }
}
