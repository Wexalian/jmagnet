package com.wexalian.jmagnet.impl.magnet;

public abstract class BasicTorrent {
    public long getId() {
        return -1L;
    }
    
    public String getImdbId() {
        return null;
    }
    
    public String getHash() {
        
        return null;
    }
    
    public String getFilename() {
        return null;
    }
    
    public String getTitle() {
        
        return null;
    }
    
    public String getMagnetUri() {
        
        return null;
    }
    
    public int getSeason() {
        return Integer.MIN_VALUE;
    }
    
    public int getEpisode() {
        return Integer.MIN_VALUE;
    }
    
    public int getSeeds() {
        return Integer.MIN_VALUE;
    }
    
    public int getPeers() {
        return Integer.MIN_VALUE;
    }
    
    public long getReleaseDate() {
        return Long.MIN_VALUE;
    }
    
    public long getSizeInBytes() {
        return Long.MIN_VALUE;
    }
    
    //TODO make single torrent with different names
}
