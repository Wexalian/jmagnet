package com.wexalian.jmagnet.impl.magnet.piratebay;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.MagnetMap;
import com.wexalian.jmagnet.api.SearchOptions;
import com.wexalian.jmagnet.impl.magnet.BasicTorrent;
import com.wexalian.jmagnet.impl.magnet.HTTPMagnetProvider;
import com.wexalian.jmagnet.parser.MagnetParser;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.wexalian.jmagnet.impl.magnet.piratebay.TPBMagnetProvider.TPBTorrent;

public class TPBMagnetProvider extends HTTPMagnetProvider<TPBTorrent> {
    private static final String NAME = "ThePirateBay";
    private static final String BASE_URL = "https://apibay.org/q.php?q=";
    private static final TypeToken<TPBTorrent> TYPE_TOKEN = new TypeToken<>() {};
    
    public TPBMagnetProvider() {
        super(BASE_URL, SearchOptions.Keywords.Type.SLUG, TYPE_TOKEN, JsonElement::getAsJsonArray);
        setPagination(false); // ThePirateBay does not support page limiting (currently...)
        setMaxLimit(-1); // ThePirateBay does not support page limiting
    }
    
    
    
    @Override
    public boolean isEnabled() {
        return false;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    protected Magnet parseTorrent(TPBTorrent torrent) {
        int peers = torrent.getPeers();
        int seeds = torrent.getSeeds();
        
        MagnetMap magnetMap = MagnetMap.build(b -> {
            b.addParameter(Magnet.Parameter.EXACT_TOPIC, MAGNET_URI_SCHEME + torrent.getHash());
            b.addParameter(Magnet.Parameter.DISPLAY_NAME, torrent.getFilename());
        });
        return MagnetParser.parse(magnetMap.createUri(), MagnetInfo.of(getName(), peers, seeds));
    }
    
    protected static class TPBTorrent extends BasicTorrent {
        private String id;
        private String name;
        private String info_hash;
        private String leechers;
        private String seeders;
        private String num_files;
        private String size;
        private String username;
        private String added;
        private String status;
        private String category;
        private String imdb;
        
        @Override
        public long getId() {
            return Long.parseLong(id);
        }
        
        @Override
        public String getImdbId() {
            return imdb;
        }
        
        @Override
        public String getHash() {
            return info_hash;
        }
        
        @Override
        public String getFilename() {
            return URLEncoder.encode(name, StandardCharsets.UTF_8);
        }
        
        @Override
        public String getTitle() {
            return name;
        }
        
        @Override
        public int getSeeds() {
            return Integer.parseInt(seeders);
        }
        
        @Override
        public int getPeers() {
            return Integer.parseInt(leechers);
        }
        
        @Override
        public long getSizeInBytes() {
            return Long.parseLong(size);
        }
        
        public int getNumFiles() {
            return Integer.parseInt(num_files);
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
    }
}
