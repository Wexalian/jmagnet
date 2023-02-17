package com.wexalian.jmagnet.api.provider;

import com.wexalian.common.plugin.IAbstractPlugin;
import com.wexalian.jmagnet.api.Tracker;

import java.util.List;

public interface ITrackerProvider extends IAbstractPlugin {
    List<Tracker> load();
    
    @Override
    default boolean isEnabled() {
        return true;
    }
}
