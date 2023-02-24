package com.wexalian.jmagnet.api.provider;

import com.wexalian.common.plugin.IAbstractPlugin;
import com.wexalian.jmagnet.MagnetInfo.Category;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.nullability.annotations.Nonnull;

import java.util.List;
import java.util.Set;

public interface IMagnetProvider extends IAbstractPlugin {
    String MAGNET_URI_SCHEME = "urn:btih:";
    
    @Nonnull
    Set<Category> supported();
    
    @Nonnull
    default List<Magnet> recent(Category category, int page, int limit) {
        return List.of();
    }
    
    @Nonnull
    default List<Magnet> popular(Category category, int page, int limit) {
        return List.of();
    }
    
    @Nonnull
    default List<Magnet> search(Category category, String query, int page, int limit) {
        return List.of();
    }
    
    @Nonnull
    default List<Magnet> show(String imdbId, String slug, int page, int limit) {
        return List.of();
    }
    
    @Override
    default boolean isEnabled() {
        return true;
    }
    
    
}
