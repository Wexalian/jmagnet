package com.wexalian.jmagnet.impl.magnet.piratebay;

import com.google.gson.reflect.TypeToken;
import com.wexalian.common.gson.GsonUtil;
import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.MagnetMap;
import com.wexalian.jmagnet.api.IMagnetProvider;
import com.wexalian.jmagnet.api.SearchOptions;
import com.wexalian.jmagnet.parser.MagnetParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TPBMagnetProvider implements IMagnetProvider {
    public static final String NAME = "ThePirateBay";
    
    public static final String BASE_URL = "https://apibay.org/q.php?q=";
    
    private final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).followRedirects(HttpClient.Redirect.NORMAL).build();
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public List<Magnet> recommended(int page) {
        return List.of();
    }
    
    @Override
    public List<Magnet> search(SearchOptions options) {
        List<Magnet> magnets = new ArrayList<>();
        List<TPBTorrent> torrents = getAndParse(BASE_URL + options.season(), new TypeToken<>() {});
        
        for (TPBTorrent torrent : torrents) {
            int peers = torrent.getLeechers();
            int seeds = torrent.getSeeders();
            
            MagnetMap magnetMap = MagnetMap.build(b -> {
                b.addParameter(Magnet.Parameter.EXACT_TOPIC, "urn:btih:" + torrent.getInfoHash());
                b.addParameter(Magnet.Parameter.DISPLAY_NAME, torrent.getName().replace(" ", "%20"));
            });
            magnets.add(MagnetParser.parse(magnetMap.createUri(), MagnetInfo.of(NAME, peers, seeds)));
        }
        return magnets;
    }
    
    public <T> T getAndParse(String url, TypeToken<T> typeToken) {
        HttpRequest.Builder builder = HttpRequest.newBuilder().GET().uri(URI.create(url));
        
        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString())
                     .thenApply(HttpResponse::body)
                     .thenApply(s -> GsonUtil.fromJsonString(s, typeToken))
                     .join();
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
