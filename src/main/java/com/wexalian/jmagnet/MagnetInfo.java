package com.wexalian.jmagnet;

public class MagnetInfo {
    private final String provider;
    private final String formattedName;
    private final Category category;
    
    private final boolean isSeason;
    private final boolean isEpisode;
    
    private final int season;
    private final int episode;
    
    private final int peers;
    private final int seeds;
    
    MagnetInfo(String provider, String formattedName, Category category, boolean isSeason, boolean isEpisode, int season, int episode, int peers, int seeds) {
        this.provider = provider;
        this.formattedName = formattedName;
        this.category = category;
        this.isSeason = isSeason;
        this.isEpisode = isEpisode;
        this.season = season;
        this.episode = episode;
        this.peers = peers;
        this.seeds = seeds;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public String getFormattedName() {
        return formattedName;
    }
    
    public Category getCategory() {
        return category;
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
    
    public int getPeers() {
        return peers;
    }
    
    public int getSeeds() {
        return seeds;
    }
    
    public static MagnetInfo of(String provider) {
        return builder(provider).build();
    }
    
    public static MagnetInfo of(String provider, Category category) {
        return builder(provider).setCategory(category).build();
    }
    
    public static MagnetInfo of(String provider, Category category, int peers, int seeds) {
        return builder(provider, category, peers, seeds).build();
    }
    
    // public static MagnetInfo of(String provider, Category category, int peers, int seeds, int season) {
    //     return builder(provider, category, peers, seeds, season).build();
    // }
    //
    // public static MagnetInfo of(String provider, Category category, int peers, int seeds, int season, int episode) {
    //     return builder(provider, category, peers, seeds, season, episode).build();
    // }
    // public static MagnetInfo of(String provider, String formattedName, Category category, int peers, int seeds, int season, int episode) {
    //     return builder(provider, formattedName, category, peers, seeds, season, episode).build();
    // }
    
    public static Builder builder(String provider) {
        return new Builder(provider).setCategory(Category.OTHER).setFormattedName("").setPeers(-1).setSeeds(-1);
    }
    
    public static Builder builder(String provider, Category category) {
        return new Builder(provider).setCategory(category).setFormattedName("").setPeers(-1).setSeeds(-1);
    }
    
    public static Builder builder(String provider, Category category, int peers, int seeds) {
        return new Builder(provider).setCategory(category).setFormattedName("").setPeers(peers).setSeeds(seeds);
    }
    
    public static Builder builder(String provider, Category category, int peers, int seeds, int season) {
        return new Builder(provider).setCategory(category).setFormattedName("").setPeers(peers).setSeeds(seeds).setSeason(season);
    }
    
    public static Builder builder(String provider, Category category, int peers, int seeds, int season, int episode) {
        return new Builder(provider).setCategory(category).setFormattedName("").setPeers(peers).setSeeds(seeds).setSeason(season).setEpisode(season, episode);
    }
    
    public static Builder builder(String provider, String formattedName, Category category, int peers, int seeds, int season, int episode) {
        return new Builder(provider).setCategory(category).setFormattedName(formattedName).setPeers(peers).setSeeds(seeds).setSeason(season).setEpisode(season, episode);
    }
    
    public static Builder builder(MagnetInfo info) {
        return builder(info.provider, info.category, info.peers, info.seeds, info.season, info.episode).setFormattedName(info.formattedName);
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
    
    public interface Category {
        
        Category ALL = Common.ALL;
        
        Category AUDIO = Common.AUDIO;
        Category MUSIC = Common.MUSIC;
        
        Category VIDEO = Common.VIDEO;
        Category MOVIES = Common.MOVIES;
        Category TV_SHOWS = Common.TV_SHOWS;
        
        Category APPLICATIONS = Common.APPLICATIONS;
        Category GAMES = Common.GAMES;
        Category PORN = Common.PORN;
        Category OTHER = Common.OTHER;
        
        boolean isIn(Category category);
    }
    
    public static class Builder {
        private final String provider;
        
        private String formattedName;
        
        private Category category;
        
        private int peers = -1;
        private int seeds = -1;
        
        private boolean isSeason = false;
        private boolean isEpisode = false;
        
        private int season = -1;
        private int episode = -1;
        
        private Builder(String provider) {
            this.provider = provider;
        }
        
        public Builder setFormattedName(String formattedName) {
            this.formattedName = formattedName;
            return this;
        }
        
        public Builder setCategory(Category category) {
            this.category = category;
            return this;
        }
        
        public Builder setPeers(int peers) {
            this.peers = peers;
            return this;
        }
        
        public Builder setSeeds(int seeds) {
            this.seeds = seeds;
            return this;
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
        
        public Builder setEpisode(int season, int episode) {
            this.season = season;
            return setEpisode(episode);
        }
        
        public MagnetInfo build() {
            return new MagnetInfo(provider, formattedName, category, isSeason, isEpisode, season, episode, peers, seeds);
        }
    }
    
    private enum Common implements Category {
        ALL,
        
        AUDIO,
        MUSIC(AUDIO),
        
        VIDEO,
        MOVIES(VIDEO),
        TV_SHOWS(VIDEO),
        
        APPLICATIONS,
        GAMES,
        PORN,
        OTHER;
        
        private final Common parent;
        
        Common() {
            this(null);
        }
        
        Common(Common parent) {
            this.parent = parent;
        }
        
        @Override
        public boolean isIn(Category other) {
            return this == ALL || other == ALL || this == other || parent == other;
        }
    }
}
