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
        EXACT_TOPIC("Exact Topic", "xt"),
        DISPLAY_NAME("Display Name", "dn"),
        TRACKERS("Trackers", "tr"),
        EXACT_LENGTH("Exact Length", "xl"),
        WEB_SEED("Web Seed", "ws"),
        ACCEPTABLE_SOURCE("Acceptable Source", "as"),
        EXACT_SOURCE("Exact Source", "xs"),
        KEYWORD_TOPIC("Keyword Topic", "kt"),
        MANIFEST_TOPIC("Manifest Topic", "mt"),
        SELECT_ONLY("Select Only", "so");
        
        private static final Map<String, Parameter> REVERSE_KEY_LOOKUP = MapUtil.newHashMap().values(values(), Parameter::getKey);
        
        private final String name;
        private final String key;
        
        Parameter(String name, String key) {
            this.name = name;
            this.key = key;
        }
        
        public String getName() {
            return name;
        }
        public String getKey() {
            return key;
        }
        
        public static Parameter get(String key) {
            return REVERSE_KEY_LOOKUP.get(key);
        }
    }
}
