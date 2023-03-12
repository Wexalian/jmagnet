package com.wexalian.jmagnet.api;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public record Tracker(String uri) {
    public Tracker(String uri) {
        this.uri = URLDecoder.decode(uri, StandardCharsets.UTF_8);
    }
}
