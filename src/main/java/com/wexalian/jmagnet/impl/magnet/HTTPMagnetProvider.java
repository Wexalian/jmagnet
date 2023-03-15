package com.wexalian.jmagnet.impl.magnet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.wexalian.common.gson.GsonUtil;
import com.wexalian.common.util.StringUtil;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.MagnetInfo.Category;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.api.provider.IMagnetProvider;
import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class HTTPMagnetProvider<T> implements IMagnetProvider {
    private final String baseUrl;
    private final TypeToken<T> typeToken;
    private final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).followRedirects(HttpClient.Redirect.NORMAL).build();
    
    private boolean pagination = false;
    private int maxLimit = 0;
    
    private final Function<JsonElement, JsonArray> jsonDataFunc;
    
    protected HTTPMagnetProvider(String baseUrl, TypeToken<T> typeToken) {
        this(baseUrl, typeToken, JsonElement::getAsJsonArray);
    }
    
    protected HTTPMagnetProvider(String baseUrl, TypeToken<T> typeToken, Function<JsonElement, JsonArray> jsonDataFunc) {
        this.baseUrl = baseUrl;
        this.typeToken = typeToken;
        this.jsonDataFunc = jsonDataFunc;
    }
    
    @Nonnull
    protected List<Magnet> get(Category category, String endpoint, int page, int limit) {
        String[] params = new String[2];
        if (pagination && page > 0) params[0] = "page=" + page;
        if (limit > 0) params[0] = "limit=" + (maxLimit > 0 ? Math.min(limit, maxLimit) : limit);
        return get(category, endpoint, params);
    }
    
    protected List<Magnet> get(Category category, String endpoint, String... params) {
        List<Magnet> magnets = new ArrayList<>();
        JsonArray jsonArray = jsonDataFunc.apply(getJson(endpoint, params));
        for (JsonElement magnetJson : jsonArray) {
            T torrent = GsonUtil.fromJsonElement(magnetJson, typeToken.getType());
            Magnet magnet = parseTorrent(torrent);
            if (magnet != null && magnet.getCategory().isIn(category)) {
                magnets.add(magnet);
            }
        }
        return magnets;
    }
    
    @Nullable
    protected abstract Magnet parseTorrent(T torrent);
    
    protected final JsonElement getJson(String endpoint, String[] query) {
        if (query == null) return getJson(endpoint);
        return getJson(baseUrl + endpoint + "?" + StringUtil.join(query, "&"));
    }
    
    protected final JsonElement getJson(String url) {
        HttpRequest.Builder builder = HttpRequest.newBuilder().GET().uri(URI.create(url));
        
        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString())
                     .thenApply(HttpResponse::body)
                     .thenApply(GsonUtil::toJsonElement)
                     .join();
    }
    
    public final void setPagination(boolean pagination) {
        this.pagination = pagination;
    }
    
    public final void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
    }
    
    protected final MagnetInfo createMagnetInfo(BasicTorrent torrent, Category category) {
        int peers = torrent.getPeers();
        int seeds = torrent.getSeeds();
        int season = torrent.getSeason();
        int episode = torrent.getEpisode();
        
        return MagnetInfo.of(getName(), "", category, peers, seeds, season, episode);
    }
}
