package com.wexalian.jmagnet.impl.magnet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.wexalian.common.collection.pair.Pair;
import com.wexalian.common.gson.GsonUtil;
import com.wexalian.common.util.StringUtil;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.api.IMagnetProvider;
import com.wexalian.jmagnet.api.SearchOptions;
import com.wexalian.nullability.annotations.Nonnull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class HTTPMagnetProvider<T> implements IMagnetProvider {
    private final String baseUrl;
    private final SearchOptions.Keywords.Type type;
    private final TypeToken<T> typeToken;
    private final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).followRedirects(HttpClient.Redirect.NORMAL).build();
    
    private boolean pagination = false;
    private int maxLimit = 0;
    
    private final Function<JsonElement, JsonArray> jsonDataFunc;
    
    protected HTTPMagnetProvider(String baseUrl, SearchOptions.Keywords.Type type, TypeToken<T> typeToken) {
        this(baseUrl, type, typeToken, null);
    }
    
    protected HTTPMagnetProvider(String baseUrl, SearchOptions.Keywords.Type type, TypeToken<T> typeToken, Function<JsonElement, JsonArray> jsonDataFunc) {
        this.baseUrl = baseUrl;
        this.type = type;
        this.typeToken = typeToken;
        this.jsonDataFunc = jsonDataFunc;
    }
    
    protected JsonArray getJsonData(JsonElement result) {
        if (jsonDataFunc != null) {
            return jsonDataFunc.apply(result);
        }
        else throw new IllegalStateException("You need to either set a jsonDataFunc or overwrite the getJsonData method!");
    }
    
    protected abstract Magnet parseTorrent(T torrent);
    
    protected Pair<Integer, String[]> getQuery(SearchOptions options, int page) {
        String[] query = new String[3];
        int optLimit = options.limit();
        int maxPages = options.maxPages();
        int limit = -1;
        
        query[0] = type.get(options.keywords());
        if (optLimit > 0 || maxLimit > 0) {
            limit = optLimit > 0 ? maxLimit > 0 ? Math.min(optLimit, maxLimit) : optLimit : maxLimit;
            query[1] = "limit=" + limit;
        }
        if (pagination && maxPages > 1 && page > 1) query[2] = "page=" + page;
        return Pair.of(limit, query);
    }
    
    @Nonnull
    @Override
    public final List<Magnet> search(SearchOptions options) {
        List<Magnet> magnets = new ArrayList<>();
        
        int page = 1;
        var query = getQuery(options, page);
        JsonElement result = getAndParse(query.getRight());
    
        int duplicate = 0;
        JsonArray torrentJsons;
        while ((torrentJsons = getJsonData(result)) != null && torrentJsons.size() > 0) {
            for (JsonElement torrentJson : torrentJsons) {
                T torrent = GsonUtil.fromJsonElement(torrentJson, typeToken.getType());
                Magnet magnet = parseTorrent(torrent);
                if (!magnets.contains(magnet)) {
                    magnets.add(magnet);
                }
                else duplicate++;
            }
            
            System.out.print("--- Magnet provider '" + getName() + "' loaded " + torrentJsons.size() + " torrents from page " + page + "/" + (pagination ? options.maxPages() : 1));
            
            int limit = query.getLeft();
            if (limit <= 0) System.out.println(" without a page limit");
            else System.out.println(" with a page limit of " + limit);
            
            if (!pagination || ++page > options.maxPages()) break;
            query = getQuery(options, page);
            result = getAndParse(query.getRight());
        }
        System.out.println("Duplicate torrents found: " + duplicate);
        return magnets;
    }
    
    protected final JsonElement getAndParse(String[] query) {
        HttpRequest.Builder builder = HttpRequest.newBuilder().GET().uri(URI.create(baseUrl + StringUtil.join(query, "&")));
        
        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString())
                     .thenApply(HttpResponse::body)
                     .thenApply(GsonUtil::toJsonElement)
                     .join();
    }
    
    public final void setPagination(boolean pagination) {
        this.pagination = pagination;
    }
    
    public final void setMaxLimit(int maxLimit) {
        this.maxLimit = Math.min(maxLimit, 100); //hardcoded constant from https://eztv.re/api/
    }
}
