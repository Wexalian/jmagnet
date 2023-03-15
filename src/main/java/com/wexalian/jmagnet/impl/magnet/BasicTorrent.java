package com.wexalian.jmagnet.impl.magnet;

public abstract class BasicTorrent {
    public long getId() {
        return -1L;
    }
    
    public String getImdbId() {
        return "";
    }
    
    public String getHash() {
        return "";
    }
    
    public String getFilename() {
        return "";
    }
    
    public String getTitle() {
        return "";
    }
    
    public String getMagnetUri() {
        return "";
    }
    
    public int getSeason() {
        return -1;
    }
    
    public int getEpisode() {
        return -1;
    }
    
    public int getSeeds() {
        return -1;
    }
    
    public int getPeers() {
        return -1;
    }
    
    public long getReleaseDate() {
        return -1;
    }
    
    public long getSizeInBytes() {
        return -1;
    }
    
    //TODO make single torrent with different names
}
