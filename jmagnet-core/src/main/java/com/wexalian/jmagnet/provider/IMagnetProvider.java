package com.wexalian.jmagnet.provider;

import com.wexalian.common.plugin.IAbstractPlugin;
import com.wexalian.jmagnet.Magnet;

import java.util.List;

public interface IMagnetProvider extends IAbstractPlugin {
    List<Magnet> searchAll();
    
    List<Magnet> search(SearchOptions options);
    
    
    default boolean isEnabled() {
        return true;
    }
}
