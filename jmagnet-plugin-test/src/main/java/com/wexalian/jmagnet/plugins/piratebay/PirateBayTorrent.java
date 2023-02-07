package com.wexalian.jmagnet.plugins.piratebay;

public class PirateBayTorrent {
    public String id;
    public String name;
    public String info_hash;
    public String leechers;
    public String seeders;
    public String num_files;
    public String size;
    public String username;
    public String added;
    public String status;
    public String category;
    public String imdb;
    
    public long getId() {
        return Long.parseLong(id);
    }
    
    public String getName() {
        return name;
    }
    
    public String getInfoHash() {
        return info_hash;
    }
    
    public int getLeechers() {
        return Integer.parseInt(leechers);
    }
    
    public int getSeeders() {
        return Integer.parseInt(seeders);
    }
    
    public int getNumFiles() {
        return Integer.parseInt(num_files);
    }
    
    public long getSize() {
        return Long.parseLong(size);
    }
    
    public String getUsername() {
        return username;
    }
    
    public long getAdded() {
        return Long.parseLong(added);
    }
    
    public String getStatus() {
        return status;
    }
    
    public int getCategory() {
        return Integer.parseInt(category);
    }
    
    public String getImdb() {
        return imdb;
    }
}
