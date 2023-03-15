package com.wexalian.jmagnet.impl.magnet.eztv;

import com.google.gson.reflect.TypeToken;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.MagnetInfo.Category;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.impl.magnet.BasicTorrent;
import com.wexalian.jmagnet.impl.magnet.HTTPMagnetProvider;
import com.wexalian.jmagnet.parser.MagnetParser;
import com.wexalian.nullability.annotations.Nonnull;

import java.util.List;
import java.util.Set;

import static com.wexalian.jmagnet.impl.magnet.eztv.EZTVMagnetProvider.EZTVTorrent;

public class EZTVMagnetProvider extends HTTPMagnetProvider<EZTVTorrent> {
    private static final String NAME = "EZTV";
    private static final String BASE_URL = "https://eztv.re/api/";
    private static final TypeToken<EZTVTorrent> TYPE_TOKEN = new TypeToken<>() {};
    
    private static final Set<Category> SUPPORTED = Set.of(Category.TV_SHOWS);
    
    public EZTVMagnetProvider() {
        super(BASE_URL, TYPE_TOKEN, result -> result.getAsJsonObject().getAsJsonArray("torrents"));
        setPagination(true);
        setMaxLimit(100);
    }
    
    @Nonnull
    public String getName() {
        return NAME;
    }
    
    @Nonnull
    @Override
    public Set<Category> supported() {
        return SUPPORTED;
    }
    
    @Nonnull
    @Override
    public List<Magnet> recent(Category category, int page, int limit) {
        return get(category, "get-torrents", page, limit);
    }
    
    @Nonnull
    @Override
    public List<Magnet> show(String imdbId, String slug, int page, int limit) {
        return get(Category.TV_SHOWS, "get-torrents", "imdb_id=" + imdbId);
    }
    
    @Override
    public Magnet parseTorrent(EZTVTorrent torrent) {
        MagnetInfo info = createMagnetInfo(torrent, Category.TV_SHOWS);
        return MagnetParser.parse(torrent.getMagnetUri(), info);
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    protected static final class EZTVTorrent extends BasicTorrent {
        private long id;
        private String hash;
        private String filename;
        private String episode_url;
        private String torrent_url;
        private String magnet_url;
        private String title;
        private String imdb_id;
        private int season;
        private int episode;
        private String small_screenshot;
        private String large_screenshot;
        private int seeds;
        private int peers;
        private long date_released_unix;
        private long size_bytes;
        
        @Override
        public long getId() {
            return id;
        }
        
        @Override
        public String getHash() {
            return hash;
        }
        
        @Override
        public String getFilename() {
            return filename;
        }
        
        public String getMagnetUri() {
            return magnet_url;
        }
        
        @Override
        public String getTitle() {
            return title;
        }
        
        @Override
        public String getImdbId() {
            return imdb_id;
        }
        
        @Override
        public int getSeason() {
            return season;
        }
        
        @Override
        public int getEpisode() {
            return episode;
        }
        
        @Override
        public int getSeeds() {
            return seeds;
        }
        
        @Override
        public int getPeers() {
            return peers;
        }
        
        @Override
        public long getReleaseDate() {
            return date_released_unix;
        }
        
        @Override
        public long getSizeInBytes() {
            return size_bytes;
        }
        
        public String getEpisodeUrl() {
            return episode_url;
        }
        
        public String getTorrentUrl() {
            return torrent_url;
        }
        
        public String getSmallScreenshot() {
            return small_screenshot;
        }
        
        public String getLargeScreenshot() {
            return large_screenshot;
        }
    }
}
