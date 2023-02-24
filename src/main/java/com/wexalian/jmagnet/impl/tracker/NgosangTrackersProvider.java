package com.wexalian.jmagnet.impl.tracker;

import com.wexalian.jmagnet.api.Tracker;
import com.wexalian.jmagnet.api.provider.ITrackerProvider;
import com.wexalian.nullability.annotations.Nonnull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class NgosangTrackersProvider implements ITrackerProvider {
    private static final String NAME = "Ngosang's TrackerList";
    private static final String TRACKERS_ALL_URL = "https://raw.githubusercontent.com/ngosang/trackerslist/master/trackers_all.txt";
    
    private final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).followRedirects(HttpClient.Redirect.NORMAL).build();
    
    @Nonnull
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public List<Tracker> load() {
        List<Tracker> trackers = new ArrayList<>();
        for (String trackerUri : getLines(TRACKERS_ALL_URL)) {
            trackers.add(new Tracker(trackerUri));
        }
        return trackers;
    }
    
    protected final String[] getLines(String url) {
        HttpRequest.Builder builder = HttpRequest.newBuilder().GET().uri(URI.create(url));
        
        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString())
                     .thenApply(HttpResponse::body)
                     .thenApply(body -> body.split("\n\n"))
                     .join();
    }
}
