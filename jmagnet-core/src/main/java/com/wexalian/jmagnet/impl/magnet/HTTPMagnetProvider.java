package com.wexalian.jmagnet.impl.magnet;

import com.google.gson.reflect.TypeToken;
import com.wexalian.common.gson.GsonUtil;
import com.wexalian.common.util.StringUtil;
import com.wexalian.jmagnet.api.IMagnetProvider;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class HTTPMagnetProvider implements IMagnetProvider {
    private final String baseUrl;
    private final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).followRedirects(HttpClient.Redirect.NORMAL).build();
    
    protected HTTPMagnetProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    protected <T> T getAndParse(String[] query, TypeToken<T> token) {
        HttpRequest.Builder builder = HttpRequest.newBuilder().GET().uri(URI.create(baseUrl + StringUtil.join(query, "&")));
        
        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString())
                     .thenApply(HttpResponse::body)
                     .thenApply(s -> GsonUtil.fromJsonString(s, token))
                     .join();
    }
}
