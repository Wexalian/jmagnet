package com.wexalian.jmagnet;

public class MagnetInfo {
    private final String provider;
    
    private final int peers;
    private final int seeds;
    
    private final boolean isSeason;
    private final boolean isEpisode;
    
    private final int season;
    private final int episode;
    
    private final String formattedName;
    
    MagnetInfo(String provider, int peers, int seeds, boolean isSeason, boolean isEpisode, int season, int episode, String formattedName) {
        this.provider = provider;
        this.peers = peers;
        this.seeds = seeds;
        this.isSeason = isSeason;
        this.isEpisode = isEpisode;
        this.season = season;
        this.episode = episode;
        this.formattedName = formattedName;
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
    
    public int getSeason() {
        return season;
    }
    
    public boolean isEpisode() {
        return isEpisode;
    }
    
    public int getEpisode() {
        return episode;
    }
    
    public String getFormattedName() {
        return formattedName;
    }
    
    public static MagnetInfo of(String provider) {
        return builder(provider).build();
    }
    
    public static MagnetInfo of(String provider, int peers, int seeds) {
        return builder(provider, peers, seeds).build();
    }
    
    public static MagnetInfo of(String provider, int peers, int seeds, int season) {
        return builder(provider, peers, seeds, season).build();
    }
    
    public static MagnetInfo of(String provider, int peers, int seeds, int season, int episode) {
        return builder(provider, peers, seeds, season, episode).build();
    }
    
    public static Builder builder(String provider) {
        return new Builder(provider, -1, -1).setSeason(-1).setEpisode(-1).setFormattedName("");
    }
    
    public static Builder builder(String provider, int peers, int seeds) {
        return new Builder(provider, peers, seeds).setSeason(-1).setEpisode(-1).setFormattedName("");
    }
    
    public static Builder builder(String provider, int peers, int seeds, int season) {
        return new Builder(provider, peers, seeds).setSeason(season).setEpisode(-1).setFormattedName("");
    }
    
    public static Builder builder(String provider, int peers, int seeds, int season, int episode) {
        return new Builder(provider, peers, seeds).setSeason(season).setEpisode(episode).setFormattedName("");
    }
    
    public static Builder builder(MagnetInfo info) {
        return builder(info.provider, info.peers, info.seeds, info.season, info.episode).setFormattedName(info.formattedName);
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
    
    public static class Builder {
        private final String provider;
        private final int peers;
        private final int seeds;
        private boolean isSeason;
        private boolean isEpisode;
        private int season;
        private int episode;
        private String formattedName;
        
        private Builder(String provider, int peers, int seeds) {
            this.provider = provider;
            this.peers = peers;
            this.seeds = seeds;
        }
        
        public Builder setSeason(int season) {
            this.season = season;
            this.isSeason = true;
            this.isEpisode = false;
            return this;
        }
        
        public Builder setEpisode(int episode) {
            this.episode = episode;
            this.isSeason = false;
            this.isEpisode = true;
            return this;
        }
        
        public Builder setFormattedName(String formattedName) {
            this.formattedName = formattedName;
            return this;
        }
        
        public MagnetInfo build() {
            return new MagnetInfo(provider, peers, seeds, isSeason, isEpisode, season, episode, formattedName);
        }
    }
}
