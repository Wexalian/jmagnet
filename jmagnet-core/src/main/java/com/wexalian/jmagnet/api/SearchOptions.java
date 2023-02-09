package com.wexalian.jmagnet.api;

public record SearchOptions(String imdbId, String slug, int seasonNum, int episodeNum, int limit, int pages) {
    public SearchOptions() {
        this("", "", -1, -1, -1, 1);
        throw new IllegalStateException("Please use one of the factory methods to ensure proper settings");
    }
    
    public static SearchOptions searchImdb(String imdbID) {
        return new SearchOptions(imdbID, "", -1, -1, -1, 1);
    }
    
    public static SearchOptions searchSlug(String imdbID, String slug) {
        return new SearchOptions(imdbID, slug, -1, -1, -1, 1);
    }
    
    public static SearchOptions searchSeason(String imdbID, String slug, int season) {
        return new SearchOptions(imdbID, slug, season, -1, -1, 1);
    }
    
    public static SearchOptions searchEpisode(String imdbID, String slug, int season, int episode) {
        return new SearchOptions(imdbID, slug, season, episode, -1, 1);
    }
    
    public static SearchOptions searchPaged(String imdbID, String slug, int season, int episode, int limit, int pages) {
        return new SearchOptions(imdbID, slug, season, episode, limit, pages);
    }
}