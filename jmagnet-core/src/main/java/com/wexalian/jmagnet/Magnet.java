package com.wexalian.jmagnet;

import com.wexalian.common.collection.util.MapUtil;
import com.wexalian.jmagnet.parser.MagnetParser;
import com.wexalian.nullability.annotations.Nonnull;

import java.util.List;
import java.util.Map;

public final class Magnet {
    private final MagnetMap map;
    private final MagnetInfo info;
    
    private final String urn;
    private final String name;
    private final List<Tracker> trackers;
    
    public Magnet(MagnetMap map, MagnetInfo info) {
        this.map = map;
        this.info = info;
        
        this.urn = map.get(Parameter.EXACT_TOPIC);
        this.name = map.get(Parameter.DISPLAY_NAME);
        this.trackers = map.getList(Parameter.TRACKERS).as(Tracker::new);
    }
    
    public MagnetInfo getInfo() {
        return info;
    }
    
    public String getUrn() {
        return urn;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Tracker> getTrackers() {
        return trackers;
    }
    
    public String get(Parameter parameter) {
        return map.get(parameter);
    }
    
    public List<String> getList(Parameter parameter) {
        return map.getList(parameter);
    }
    
    @Nonnull
    public static Magnet parse(String magnetLink, MagnetInfo info) {
        return MagnetParser.parse(magnetLink, info);
    }
    
    public enum Parameter {
        EXACT_TOPIC("xt"),
        DISPLAY_NAME("dn"),
        TRACKERS("tr"),
        EXACT_LENGTH("xl"),
        WEB_SEED("ws"),
        ACCEPTABLE_SOURCE("as"),
        EXACT_SOURCE("xs"),
        KEYWORD_TOPIC("kt"),
        MANIFEST_TOPIC("mt"),
        SELECT_ONLY("so");
        
        private static final Map<String, Parameter> REVERSE_LOOKUP = MapUtil.newHashMap().values(values(), Parameter::getName);
        
        private final String name;
        
        Parameter(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
        public static Parameter get(String name) {
            return REVERSE_LOOKUP.get(name);
        }
    }
}
