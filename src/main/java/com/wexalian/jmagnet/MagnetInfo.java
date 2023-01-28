package com.wexalian.jmagnet;

public class MagnetInfo {
    private final String provider;
    
    private final int peers;
    private final int seeds;
    
    private boolean isSeason;
    private boolean isEpisode;
    
    private int season;
    private int episode;
    
    private MagnetInfo(String provider, int peers, int seeds, boolean isSeason, boolean isEpisode, int season, int episode) {
        this.provider = provider;
        this.peers = peers;
        this.seeds = seeds;
        this.isSeason = isSeason;
        this.isEpisode = isEpisode;
        this.season = season;
        this.episode = episode;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public int getPeers() {
        return peers;
    }
    
    public int getSeeds() {
        return seeds;
    }
    
    public boolean isSeason() {
        return isSeason;
    }
    
    public boolean isEpisode() {
        return isEpisode;
    }
    
    public int getSeason() {
        return season;
    }
    
    public void setSeason(int season) {
        this.season = season;
        this.isSeason = true;
        this.isEpisode = false;
    }
    
    public int getEpisode() {
        return episode;
    }
    
    public void setEpisode(int episode) {
        this.episode = episode;
        this.isEpisode = true;
        this.isSeason = false;
    }
    
    public static MagnetInfo of(String provider) {
        return new MagnetInfo(provider, -1, -1, false, false, -1, -1);
    }
    
    public static MagnetInfo of(String provider, int peers, int seeds) {
        return new MagnetInfo(provider, peers, seeds, false, false, -1, -1);
    }
    
    public static MagnetInfo of(String provider, int peers, int seeds, int season) {
        return new MagnetInfo(provider, peers, seeds, true, false, season, -1);
    }
    
    public static MagnetInfo of(String provider, int peers, int seeds, int season, int episode) {
        return new MagnetInfo(provider, peers, seeds, true, false, season, episode);
    }
    
    public enum Resolution {
        _2160P("2160p", 3840, 2160),
        _1440P("1440p", 2560, 1440),
        _1080P("1080p", 1920, 1080),
        _720P("720p", 1280, 720),
        _480P("480p", 640, 360),
        _360P("360p", 640, 360),
        UNKNOWN("unknown", -1, -1);
        
        private final String name;
        private final int width;
        private final int height;
        
        Resolution(String name, int width, int height) {
            this.name = name;
            this.width = width;
            this.height = height;
        }
        
        public String getName() {
            return name;
        }
        
        public int getWidth() {
            return width;
        }
        
        public int getHeight() {
            return height;
        }
    }
}
