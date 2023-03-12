package com.wexalian.jmagnet.impl.magnet.piratebay;

import com.google.gson.reflect.TypeToken;
import com.wexalian.common.collection.util.MapUtil;
import com.wexalian.jmagnet.MagnetMap;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.jmagnet.impl.magnet.BasicTorrent;
import com.wexalian.jmagnet.impl.magnet.HTTPMagnetProvider;
import com.wexalian.jmagnet.parser.MagnetParser;
import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.wexalian.jmagnet.MagnetInfo.Category;
import static com.wexalian.jmagnet.MagnetInfo.of;
import static com.wexalian.jmagnet.impl.magnet.piratebay.TPBMagnetProvider.TPBTorrent;

public class TPBMagnetProvider extends HTTPMagnetProvider<TPBTorrent> {
    private static final String NAME = "ThePirateBay";
    private static final String BASE_URL = "https://apibay.org/";
    private static final String DATA_TOP_100 = "data_top100_";
    private static final TypeToken<TPBTorrent> TYPE_TOKEN = new TypeToken<>() {};
    
    private static final Set<Category> SUPPORTED = Set.of(TBPCategory.values());
    
    public TPBMagnetProvider() {
        super(BASE_URL, TYPE_TOKEN);
        setPagination(false); // ThePirateBay does not support page limiting
        setMaxLimit(-1);      //
    }
    
    @Override
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
        String file = DATA_TOP_100 + "recent";
        if (page > 1) file += "_" + (page - 1);
        return get(category, "precompiled/" + file + ".json");
    }
    
    @Nonnull
    @Override
    public List<Magnet> popular(Category category, int page, int limit) {
        int id = TBPCategory.wrap(category).id();
        String file = DATA_TOP_100 + (id < 0 ? "all" : id);
        return get(category, "precompiled/" + file + ".json");
    }
    
    // @Nonnull
    // @Override
    // public List<Magnet> search(Category category, String query, int page, int limit) {
    //     return super.search(category, query, page, limit);
    // }
    //
    // @Nonnull
    // @Override
    // public List<Magnet> show(String imdbId, String slug, int page, int limit) {
    //     return super.show(imdbId, slug, page, limit);
    // }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Nullable
    @Override
    protected Magnet parseTorrent(TPBTorrent torrent) {
        int peers = torrent.getPeers();
        int seeds = torrent.getSeeds();
        
        MagnetMap magnetMap = MagnetMap.build(b -> {
            b.addParameter(Magnet.Parameter.EXACT_TOPIC, MAGNET_URI_SCHEME + torrent.getHash());
            b.addParameter(Magnet.Parameter.DISPLAY_NAME, torrent.getFilename());
        });
        return MagnetParser.parse(magnetMap.createUri(), of(getName(), torrent.getCategory(), peers, seeds));
    }
    
    public enum TBPCategory implements Category {
        ALL(Category.ALL, -1),
        UNKNOWN(Category.OTHER, -1),
        
        AUDIO(Category.AUDIO, 100),
        AUDIO_MUSIC(Category.MUSIC, AUDIO),
        AUDIO_BOOKS(AUDIO),
        AUDIO_SOUND_CLIPS(AUDIO),
        AUDIO_FLAC(AUDIO),
        AUDIO_OTHER(AUDIO, 199),
        
        VIDEO(Category.VIDEO, 200),
        VIDEO_MOVIES(Category.MOVIES, VIDEO),
        VIDEO_MOVIES_DVDR(Category.MOVIES, VIDEO),
        VIDEO_MUSIC_VIDEOS(VIDEO),
        VIDEO_MOVIE_CLIPS(VIDEO),
        VIDEO_TV_SHOWS(Category.TV_SHOWS, VIDEO),
        VIDEO_HANDHELD(VIDEO),
        VIDEO_HD_MOVIES(Category.MOVIES, VIDEO),
        VIDEO_HD_TV_SHOWS(Category.TV_SHOWS, VIDEO),
        VIDEO_3D(VIDEO),
        VIDEO_OTHER(VIDEO, 299),
        
        APPLICATIONS(Category.APPLICATIONS, 300),
        APPLICATIONS_WINDOWS(APPLICATIONS),
        APPLICATIONS_MAC(APPLICATIONS),
        APPLICATIONS_UNIX(APPLICATIONS),
        APPLICATIONS_HANDHELD(APPLICATIONS),
        APPLICATIONS_IOS(APPLICATIONS),
        APPLICATIONS_ANDROID(APPLICATIONS),
        APPLICATIONS_OTHER_OS(APPLICATIONS, 399),
        
        GAMES(Category.GAMES, 400),
        GAMES_PC(GAMES),
        GAMES_MAC(GAMES),
        GAMES_PSX(GAMES),
        GAMES_XBOX_360(GAMES),
        GAMES_WII(GAMES),
        GAMES_HANDHELD(GAMES),
        GAMES_IOS(GAMES),
        GAMES_ANDROID(GAMES),
        GAMES_OTHER(GAMES, 499),
        
        PORN(Category.PORN, 500),
        PORN_MOVIES(PORN),
        PORN_MOVIES_DVDR(PORN),
        PORN_PICTURES(PORN),
        PORN_GAMES(PORN),
        PORN_HD_MOVIES(PORN),
        PORN_MOVIE_CLIPS(PORN),
        PORN_OTHER(PORN, 599),
        
        OTHER(Category.OTHER, 600),
        OTHER_EBOOKS(OTHER),
        OTHER_COMICS(OTHER),
        OTHER_PICTURES(OTHER),
        OTHER_COVERS(OTHER),
        OTHER_PHYSIBLES(OTHER),
        OTHER_OTHER(OTHER, 699);
        
        private static final Map<Integer, TBPCategory> REVERSE_LOOKUP = MapUtil.newHashMap().values(values(), TBPCategory::id);
        private static final Map<Category, TBPCategory> BASE_LOOKUP = MapUtil.newHashMap().fill(m -> {
            for (TBPCategory category : values()) {
                if (category.baseCategory != null) {
                    m.put(category.baseCategory, category);
                }
            }
        });
        
        private final Category baseCategory;
        private final TBPCategory parentCategory;
        
        private final int id;
        
        TBPCategory(@Nonnull TBPCategory parentCategory) {
            this(null, parentCategory);
        }
        
        TBPCategory(@Nonnull Category baseCategory, int id) {
            this(baseCategory, null, id);
        }
        
        TBPCategory(@Nonnull TBPCategory parentCategory, int id) {
            this(null, parentCategory, id);
        }
        
        TBPCategory(@Nullable Category baseCategory, @Nonnull TBPCategory parentCategory) {
            this.baseCategory = baseCategory;
            this.parentCategory = parentCategory;
            this.id = parentCategory.id + ordinal() - parentCategory.ordinal();
        }
        
        TBPCategory(@Nullable Category baseCategory, @Nullable TBPCategory parentCategory, int id) {
            this.baseCategory = baseCategory;
            this.parentCategory = parentCategory;
            this.id = id;
        }
        
        public int id() {
            return id;
        }
        
        // @Override
        // public Category getBaseCategory() {
        //     return baseCategory != null ? baseCategory : parentCategory != null ? parentCategory.getBaseCategory() : Category.ALL;
        // }
        
        @Override
        public boolean isIn(Category category) {
            return this == category || baseCategory != null && baseCategory.isIn(category) || parentCategory != null && parentCategory.isIn(category);
        }
        
        public static Category parse(String category) {
            if (!"all".equals(category)) {
                try {
                    return REVERSE_LOOKUP.get(Integer.parseInt(category));
                }
                catch (NumberFormatException ignored) {}
            }
            return Category.ALL;
        }
        
        public static TBPCategory wrap(Category category) {
            return BASE_LOOKUP.getOrDefault(category, UNKNOWN);
        }
        
        public static Object getFilePart(Category category) {
            TBPCategory cat = wrap(category);
            return (cat.id < 0 ? "all" : cat.id);
        }
    }
    
    protected static class TPBTorrent extends BasicTorrent {
        private String id;
        private String name;
        private String info_hash;
        private String leechers;
        private String seeders;
        private String num_files;
        private String size;
        private String username;
        private String added;
        private String status;
        private String category;
        private String imdb;
        
        @Override
        public long getId() {
            return Long.parseLong(id);
        }
        
        @Override
        public String getImdbId() {
            return imdb;
        }
        
        @Override
        public String getHash() {
            return info_hash;
        }
        
        @Override
        public String getFilename() {
            return URLEncoder.encode(name, StandardCharsets.UTF_8);
        }
        
        @Override
        public String getTitle() {
            return name;
        }
        
        @Override
        public int getSeeds() {
            return Integer.parseInt(seeders);
        }
        
        @Override
        public int getPeers() {
            return Integer.parseInt(leechers);
        }
        
        @Override
        public long getSizeInBytes() {
            return Long.parseLong(size);
        }
        
        public int getNumFiles() {
            return Integer.parseInt(num_files);
        }
        
        public String getUsername() {
            return username;
        }
        
        public long getAdded() {
            return Long.parseLong(added);
        }
        
        public String getStatus() {
            return status;
        }
        
        public Category getCategory() {
            return TBPCategory.parse(category);
        }
    }
}
