package com.wexalian.jmagnet;

import com.wexalian.common.collection.wrapper.ListWrapper;
import com.wexalian.jmagnet.api.Magnet;

import java.util.*;
import java.util.function.Consumer;

public class MagnetMap {
    private static final ParameterValues EMPTY = new ParameterValues();
    
    private final Map<Magnet.Parameter, ParameterValues> magnetMap;
    
    public MagnetMap(Map<Magnet.Parameter, ParameterValues> magnetMap) {
        this.magnetMap = magnetMap;
    }
    
    public String getValue(Magnet.Parameter parameter) {
        ParameterValues values = magnetMap.getOrDefault(parameter, EMPTY);
        return values.hasValues() ? values.getValue() : "";
    }
    
    public ListWrapper<String> getList(Magnet.Parameter parameter) {
        ParameterValues values = magnetMap.getOrDefault(parameter, EMPTY);
        return values.hasValues() ? values::getValues : List::of;
    }
    
    public String createUri() {
        StringBuilder uri = new StringBuilder();
        for (var entry : magnetMap.entrySet()) {
            Magnet.Parameter parameter = entry.getKey();
            ParameterValues values = entry.getValue();
            
            for (String value : values) {
                uri.append(parameter.getKey());
                uri.append('=');
                uri.append(value);
                uri.append('&');
            }
        }
        return uri.toString();
    }
    
    public static MagnetMap build(Consumer<ParameterMap> builder) {
        Map<Magnet.Parameter, ParameterValues> map = new HashMap<>();
        builder.accept((parameter, value) -> map.computeIfAbsent(parameter, ParameterValues::new).add(value));
        return new MagnetMap(map);
    }
    
    @FunctionalInterface
    public interface ParameterMap {
        void addParameter(Magnet.Parameter parameter, String value);
    }
    
    public static class ParameterValues implements Iterable<String> {
        private final List<String> values = new ArrayList<>();
        
        public ParameterValues() {}
        public ParameterValues(Magnet.Parameter p) {}
        
        protected void add(String value) {
            values.add(value);
        }
        
        protected boolean hasValues() {
            return !values.isEmpty();
        }
        
        protected String getValue() {
            return values.get(0);
        }
        
        protected List<String> getValues() {
            return values;
        }
        
        @Override
        public Iterator<String> iterator() {
            return values.iterator();
        }
    }
}
