package com.wexalian.jmagnet.plugins.piratebay;

import com.google.gson.reflect.TypeToken;
import com.wexalian.common.gson.GsonUtil;
import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.parser.MagnetParser;
import com.wexalian.jmagnet.provider.IMagnetProvider;
import com.wexalian.jmagnet.provider.SearchOptions;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TPBMagnetProvider implements IMagnetProvider {
    public static final String NAME = "ThePirateBay";
    
    public static final String BASE_URL = "https://apibay.org/q.php?q=";
    private static final String MAGNET_URI_FORMAT = "magnet:?xt=urn:btih:{InfoHash}&dn={Title}";
    
    private final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).followRedirects(HttpClient.Redirect.NORMAL).build();
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public List<Magnet> searchAll() {
        return List.of();
    }
    
    @Override
    public List<Magnet> search(SearchOptions options) {
        List<Magnet> magnets = new ArrayList<>();
        List<PirateBayTorrent> torrents = getAndParse(BASE_URL + options.season(), new TypeToken<>() {});
        
        for (PirateBayTorrent torrent : torrents) {
            String magnet = MAGNET_URI_FORMAT.replace("{InfoHash}", torrent.getInfoHash()).replace("{Title}", torrent.getName().replace(" ", "%20"));
            
            int peers = torrent.getLeechers();
            int seeds = torrent.getSeeders();
            
            magnets.add(MagnetParser.parse(magnet, MagnetInfo.of(NAME, peers, seeds)));
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
}
