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
    
    private static class TPBTorrent {
        String id;
        String name;
        String info_hash;
        String leechers;
        String seeders;
        String num_files;
        String size;
        String username;
        String added;
        String status;
        String category;
        String imdb;
        
        long getId() {
            return Long.parseLong(id);
        }
        
        String getName() {
            return name;
        }
        
        String getInfoHash() {
            return info_hash;
        }
        
        int getLeechers() {
            return Integer.parseInt(leechers);
        }
        
        int getSeeders() {
            return Integer.parseInt(seeders);
        }
        
        int getNumFiles() {
            return Integer.parseInt(num_files);
        }
        
        long getSize() {
            return Long.parseLong(size);
        }
        
        String getUsername() {
            return username;
        }
        
        long getAdded() {
            return Long.parseLong(added);
        }
        
        String getStatus() {
            return status;
        }
        
        int getCategory() {
            return Integer.parseInt(category);
        }
        
        String getImdb() {
            return imdb;
        }
    }
}
