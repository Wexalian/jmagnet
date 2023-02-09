package com.wexalian.jmagnet.api;

import com.wexalian.common.plugin.IAbstractPlugin;
import com.wexalian.jmagnet.Magnet;

import java.util.List;

public interface IMagnetProvider extends IAbstractPlugin {
    List<Magnet> recommended(int page);
    
    List<Magnet> search(SearchOptions options);
    
    @Override
    default boolean isEnabled() {
        return true;
    }
}
