package com.wexalian.jmagnet.provider;

import com.wexalian.common.plugin.IAbstractPlugin;
import com.wexalian.jmagnet.Magnet;

import java.util.stream.Stream;

public interface IMagnetProvider extends IAbstractPlugin {
    Stream<Magnet> provideAll();
    
    Stream<Magnet> provide(String query);
    
    default boolean isEnabled() {
        return false;
    }
}
