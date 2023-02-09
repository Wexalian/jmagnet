package com.wexalian.jmagnet.api;

import java.util.function.Function;

public record SearchOptions(Keywords keywords, int seasonNum, int episodeNum, int limit, int maxPages) {
    public SearchOptions() {
        this(Keywords.NONE, -1, -1, -1, 1);
        throw new IllegalStateException("Please use one of the factory methods to ensure proper settings");
    }
    
    public static SearchOptions imdb(String imdbID) {
        return new SearchOptions(Keywords.imdb(imdbID), -1, -1, -1, 1);
    }
    
    public static SearchOptions slug(String slug) {
        return new SearchOptions(Keywords.slug(slug), -1, -1, -1, 1);
    }
    
    public static SearchOptions season(String imdbID, String slug, int season) {
        return new SearchOptions(Keywords.all(imdbID, slug), season, -1, -1, 1);
    }
    
    public static SearchOptions episode(String imdbID, String slug, int season, int episode) {
        return new SearchOptions(Keywords.all(imdbID, slug), season, episode, -1, 1);
    }
    
    public static SearchOptions paged(String imdbID, String slug, int season, int episode, int limit, int pages) {
        return new SearchOptions(Keywords.all(imdbID, slug), season, episode, limit, pages);
    }
    
    public record Keywords(String imdbId, String slug) {
        public static final Keywords NONE = new Keywords(null, null);
        
        public String getKeyword(Keywords.Type type) {
            return type.get(this);
        }
        
        public static Keywords imdb(String imdbId) {
            return new Keywords(imdbId, null);
        }
        
        public static Keywords slug(String slug) {
            return new Keywords(null, slug);
        }
        
        public static Keywords all(String imdbId, String slug) {
            return new Keywords(imdbId, slug);
        }
        
        public enum Type {
            SLUG(s -> s.slug),
            IMDB_TT(s -> s.imdbId),
            IMDB_ID(s -> s.imdbId.replace("tt", ""));
            
            private final Function<Keywords, String> func;
            
            Type(Function<Keywords, String> func) {
                this.func = func;
            }
            
            public String get(Keywords keywords) {
                return func.apply(keywords);
            }
        }
    }
}