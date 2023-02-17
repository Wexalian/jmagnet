package com.wexalian.jmagnet.api;

import com.wexalian.common.plugin.IAbstractPlugin;
import com.wexalian.jmagnet.Magnet;
import com.wexalian.nullability.annotations.Nonnull;

import java.util.List;

public interface IMagnetProvider extends IAbstractPlugin {
    String MAGNET_URI_SCHEME = "urn:btih:";
    
    @Nonnull
    default List<Magnet> recommended(int page) {
        return List.of();
    }
    
    @Nonnull
    default List<Magnet> search(SearchOptions options) {
        return List.of();
    }
    
    @Override
    default boolean isEnabled() {
        return true;
    }
}
