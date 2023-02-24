package com.wexalian.jmagnet.impl.magnet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.wexalian.common.gson.GsonUtil;
import com.wexalian.common.util.StringUtil;
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
    
    // protected JsonArray getJsonData(JsonElement result) {
    //     if (jsonDataFunc != null) {
    //         return jsonDataFunc.apply(result);
    //     }
    //     else throw new IllegalStateException("You need to either set a jsonDataFunc or overwrite the getJsonData method!");
    // }
    //
    
    // protected Pair<Integer, String[]> getQuery(SearchOptions options, int page) {
    //     String[] query = new String[3];
    //     int optLimit = options.limit();
    //     int maxPages = options.maxPages();
    //     int limit = -1;
    //
    //     query[0] = type.get(options.keywords());
    //     if (optLimit > 0 || maxLimit > 0) {
    //         limit = optLimit > 0 ? maxLimit > 0 ? Math.min(optLimit, maxLimit) : optLimit : maxLimit;
    //         query[1] = "limit=" + limit;
    //     }
    //     if (pagination && maxPages > 1 && page > 1) query[2] = "page=" + page;
    //     return Pair.of(limit, query);
    // }
    //
    // protected String[] getQuery(String imdbId, String slug, int limit, int page) {
    //     String[] query = new String[3];
    //     query[0] = type.get(imdbId, slug);
    //     if(limit > 0) query[1] = "limit=" + limit;
    //     if(page > 0) query[2] = "page=" + page;
    //     return query;
    // }
    //
    // @Nonnull
    // @Override
    // public final List<Magnet> show(String imdbId, String slug, int limit, int page) {
    
    // List<Magnet> magnets = new ArrayList<>();
    //
    // int page = 1;
    // var query = getQuery(options, page);
    // JsonElement result = getAndParse(query.getRight());
    //
    // int duplicate = 0;
    // JsonArray torrentJsons;
    // while ((torrentJsons = getJsonData(result)) != null && torrentJsons.size() > 0) {
    //     for (JsonElement torrentJson : torrentJsons) {
    //         T torrent = GsonUtil.fromJsonElement(torrentJson, typeToken.getType());
    //         Magnet magnet = parseTorrent(torrent);
    //         if (!magnets.contains(magnet)) {
    //             magnets.add(magnet);
    //         }
    //         else duplicate++;
    //     }
    //
    //     System.out.print("--- Magnet provider '" + getName() + "' loaded " + torrentJsons.size() + " torrents from page " + page + "/" + (pagination ? options.maxPages() : 1));
    //
    //     int limit = query.getLeft();
    //     if (limit <= 0) System.out.println(" without a page limit");
    //     else System.out.println(" with a page limit of " + limit);
    //
    //     if (!pagination || ++page > options.maxPages()) break;
    //     query = getQuery(options, page);
    //     result = getAndParse(query.getRight());
    // }
    // System.out.println("Duplicate torrents found: " + duplicate);
    // return magnets;
    // }
    
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
}
