package com.wexalian.jmagnet.parser;

import com.wexalian.common.util.StringUtil;
import com.wexalian.jmagnet.MagnetInfo;
import com.wexalian.jmagnet.MagnetInfo.Category;
import com.wexalian.jmagnet.MagnetMap;
import com.wexalian.jmagnet.api.Magnet;
import com.wexalian.nullability.annotations.Nonnull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MagnetParser {
    private static final Pattern PARTS_PATTERN = Pattern.compile("([a-z]{2})=(.*?)(?=\\Z|&(?![a-z]{1,4};))");
    
    private static final List<Pattern> EPISODE_PATTERNS = List.of(Pattern.compile("s(\\d{1,2})e(\\d{1,2})")); //tests for s01e01
    
    private static final List<Pattern> SEASON_PATTERNS = List.of(Pattern.compile("season (\\d{1,2})"), //tests for season 01
                                                                 Pattern.compile("s(\\d{1,2})")); //tests for s01
    
    @Nonnull
    public static Magnet parse(String magnetLink) {
        return parse(magnetLink, MagnetInfo.of("unknown", Category.ALL));
    }
    
    public static int PARSED = 0;
    
    @Nonnull
    public static Magnet parse(@Nonnull String magnetLink, @Nonnull MagnetInfo info) {
        MagnetMap map = decodeMagnetLink(magnetLink);
        
        PARSED++;
        
        if (!info.isSeason() || !info.isEpisode() || StringUtil.isBlank(info.getFormattedName()) || info.getCategory().isIn(Category.OTHER)) {
            MagnetInfo.Builder builder = MagnetInfo.builder(info);
            
            String displayName = map.getValue(Magnet.Parameter.DISPLAY_NAME);
            NameParseResult result = parseDisplayName(displayName);
            builder.setFormattedName(result.formattedName);
            if (result.isSeason) builder.setSeason(result.season);
            if (result.isEpisode) builder.setEpisode(result.season, result.episode);
            if (result.isSeason || result.isEpisode) builder.setCategory(Category.TV_SHOWS);
            
            info = builder.build();
        }
        
        return new Magnet(map, info);
    }
    
    @Nonnull
    public static NameParseResult parseMagnetUri(@Nonnull String magnetLink) {
        return parseDisplayName(decodeMagnetLink(magnetLink).getValue(Magnet.Parameter.DISPLAY_NAME));
    }
    
    private static MagnetMap decodeMagnetLink(String magnetLink) {
        Matcher matcher = PARTS_PATTERN.matcher(magnetLink);
        
        return MagnetMap.build(map -> {
            while (matcher.find()) {
                String param = matcher.group(1);
                String value = matcher.group(2);
                
                map.addParameter(Magnet.Parameter.get(param), value);
            }
        });
        
    }
    
    @Nonnull
    public static NameParseResult parseDisplayName(@Nonnull String name) {
        name = name.replaceAll("%20", " ").replace('.', ' ').replace('-', ' ');
        for (Pattern pattern : EPISODE_PATTERNS) {
            Matcher matcher = pattern.matcher(name.toLowerCase());
            if (matcher.find()) {
                int season = Integer.parseInt(matcher.group(1));
                int episode = Integer.parseInt(matcher.group(2));
                
                if (season > 0 && episode > 0) {
                    return new NameParseResult(name, false, true, season, episode);
                }
            }
        }
        
        for (Pattern pattern : SEASON_PATTERNS) {
            Matcher matcher = pattern.matcher(name.toLowerCase());
            if (matcher.find()) {
                int season = Integer.parseInt(matcher.group(1));
                
                if (season > 0) {
                    return new NameParseResult(name, true, false, season, -1);
                }
            }
        }
        
        return new NameParseResult(name, false, false, -1, -1);
    }
    
    public record NameParseResult(String formattedName, boolean isSeason, boolean isEpisode, int season, int episode) {}
}
