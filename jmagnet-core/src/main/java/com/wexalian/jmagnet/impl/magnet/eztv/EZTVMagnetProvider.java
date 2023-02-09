package com.wexalian.jmagnet.impl.magnet.eztv;

import com.google.gson.reflect.TypeToken;
import com.wexalian.jmagnet.Magnet;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.api.SearchOptions;
import com.wexalian.jmagnet.impl.magnet.HTTPMagnetProvider;
import com.wexalian.jmagnet.parser.MagnetParser;
import com.wexalian.nullability.annotations.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class EZTVMagnetProvider extends HTTPMagnetProvider {
    public static final String NAME = "EZTV";
    
    public static final String BASE_URL = "https://eztv.re/api/get-torrents?imdb_id=";
    
    public EZTVMagnetProvider() {
        super(BASE_URL);
    }
    
    @Nonnull
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
        
        int page = 1;
        String[] query = new String[3];
        query[0] = options.imdbId().replace("tt", "");
        if (options.limit() > 0) query[1] = "limit=" + options.limit();
        if (options.pages() > 0) query[2] = "page=" + page;
        
        EZTVApiResult result = getAndParse(query, new TypeToken<>() {});
        
        while (result.getTorrents() != null && result.getTorrents().size() > 0) {
            for (EZTVTorrent torrent : result.getTorrents()) {
                int peers = torrent.getPeers();
                int seeds = torrent.getSeeds();
                int season = torrent.getSeason();
                int episode = torrent.getEpisode();
                String title = torrent.getTitle();
                
                MagnetInfo info = MagnetInfo.builder(NAME, peers, seeds).setSeason(season).setEpisode(episode).setFormattedName(title).build();
                Magnet magnet = MagnetParser.parse(torrent.getMagnetUrl(), info);
                
                magnets.add(magnet);
            }
            page++;
            if (page > options.pages()) break;
            
            query[2] = "page=" + page;
            result = getAndParse(query, new TypeToken<>() {});
        }
        
        return magnets;
    }
    
    private static final class EZTVApiResult {
        private String imdb_id;
        private int torrents_count;
        private int limit;
        private int page;
        private List<EZTVTorrent> torrents;
        
        public String getImdbId() {
            return imdb_id;
        }
        
        public int getTorrentsCount() {
            return torrents_count;
        }
        
        public int getLimit() {
            return limit;
        }
        
        public int getPage() {
            return page;
        }
        
        public List<EZTVTorrent> getTorrents() {
            return torrents;
        }
    }
    
    private static final class EZTVTorrent {
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
        
        public long getId() {
            return id;
        }
        
        public String getHash() {
            return hash;
        }
        
        public String getFilename() {
            return filename;
        }
        
        public String getEpisodeUrl() {
            return episode_url;
        }
        
        public String getTorrentUrl() {
            return torrent_url;
        }
        
        public String getMagnetUrl() {
            return magnet_url;
        }
        
        public String getTitle() {
            return title;
        }
        
        public String getImdbId() {
            return imdb_id;
        }
        
        public int getSeason() {
            return season;
        }
        
        public int getEpisode() {
            return episode;
        }
        
        public String getSmallScreenshot() {
            return small_screenshot;
        }
        
        public String getLargeScreenshot() {
            return large_screenshot;
        }
        
        public int getSeeds() {
            return seeds;
        }
        
        public int getPeers() {
            return peers;
        }
        
        public long getDateReleasedUnix() {
            return date_released_unix;
        }
        
        public long getSizeBytes() {
            return size_bytes;
        }
    }
}
