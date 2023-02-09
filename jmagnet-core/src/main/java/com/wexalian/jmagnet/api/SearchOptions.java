package com.wexalian.jmagnet.api;

public record SearchOptions(String imdbId, String slug, int season, int episode, int limit, int pages) {
    public SearchOptions() {
        this("", "", -1, -1, -1, -1);
        throw new IllegalStateException("Please use one of the factory methods to ensure proper settings");
    }
    
    public static SearchOptions imdb(String imdbID) {
        return new SearchOptions(imdbID, "", -1, -1, -1, -1);
    }
    
    public static SearchOptions show(String slug) {
        return new SearchOptions("", slug, -1, -1, -1, -1);
    }
    
    public static SearchOptions season(String slug, int season) {
        return new SearchOptions("", slug, season, -1, -1, -1);
    }
    
    public static SearchOptions episode(String slug, int season, int episode) {
        return new SearchOptions("", slug, season, episode, -1, -1);
    }
}