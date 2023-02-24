import com.wexalian.jmagnet.api.provider.IMagnetProvider;
import com.wexalian.jmagnet.api.provider.ITrackerProvider;
import com.wexalian.jmagnet.impl.magnet.eztv.EZTVMagnetProvider;
import com.wexalian.jmagnet.impl.magnet.piratebay.TPBMagnetProvider;
import com.wexalian.jmagnet.impl.magnet.temp.TempFileMagnetProvider;
import com.wexalian.jmagnet.impl.tracker.NgosangTrackersProvider;
import com.wexalian.jmagnet.impl.tracker.TrackerCacheProvider;

module com.wexalian.jmagnet {
    requires com.wexalian.common;
    requires com.wexalian.nullability;
    requires com.google.gson;
    
    requires java.net.http;
    
    requires org.fusesource.jansi;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    
    exports com.wexalian.jmagnet;
    exports com.wexalian.jmagnet.api;
    exports com.wexalian.jmagnet.api.provider;
    exports com.wexalian.jmagnet.parser;
    exports com.wexalian.jmagnet.tracker;
    
    opens com.wexalian.jmagnet.impl.magnet.piratebay to com.google.gson;
    opens com.wexalian.jmagnet.impl.magnet.eztv to com.google.gson;
    
    uses IMagnetProvider;
    uses ITrackerProvider;
    
    provides IMagnetProvider with TPBMagnetProvider, EZTVMagnetProvider, TempFileMagnetProvider;
    provides ITrackerProvider with TrackerCacheProvider, NgosangTrackersProvider;
}