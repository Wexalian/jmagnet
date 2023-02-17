package com.wexalian.jmagnet.api;

import com.wexalian.common.plugin.IAbstractPlugin;
import com.wexalian.jmagnet.Tracker;

import java.util.List;

public interface ITrackerProvider extends IAbstractPlugin {
    List<Tracker> load();
    
    @Override
    default boolean isEnabled() {
        return true;
    }
}
