package com.wexalian.jmagnet;

import com.wexalian.common.collection.wrapper.ListWrapper;
import com.wexalian.jmagnet.api.Magnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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
    
    public String createUri() {
        StringBuilder uri = new StringBuilder();
        for (var entry : magnetMap.entrySet()) {
            for (String value : entry.getValue()) {
                uri.append(entry.getKey().getKey());
                uri.append('=');
                uri.append(value);
                uri.append('&');
            }
        }
        return uri.toString();
    }
    
    public static MagnetMap build(Consumer<ParameterMap> builder) {
        Map<Magnet.Parameter, List<String>> map = new HashMap<>();
        builder.accept((parameter, value) -> map.computeIfAbsent(parameter, k -> new ArrayList<>()).add(value));
        return new MagnetMap(map);
    }
    
    @FunctionalInterface
    public interface ParameterMap {
        void addParameter(Magnet.Parameter parameter, String value);
    }
}
