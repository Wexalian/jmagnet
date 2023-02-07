package com.wexalian.jmagnet;

import com.wexalian.common.collection.wrapper.ListWrapper;

import java.util.List;
import java.util.Map;

public class MagnetMap {
    private final Map<Magnet.Parameter, List<String>> magnetMap;
    
    public MagnetMap(Map<Magnet.Parameter, List<String>> magnetMap) {
        this.magnetMap = magnetMap;
    }
    
    public String get(Magnet.Parameter parameter) {
        return magnetMap.get(parameter).get(0);
    }
    
    public ListWrapper<String> getList(Magnet.Parameter parameter) {
        List<String> values = magnetMap.get(parameter);
        return values == null ? List::of : () -> values;
    }
    
    public Map<Magnet.Parameter, List<String>> getInternalMap() {
        return magnetMap;
    }
}
