import com.wexalian.jmagnet.impl.magnet.eztv.EZTVMagnetProvider;
import com.wexalian.jmagnet.impl.magnet.temp.TempFileMagnetProvider;
import com.wexalian.jmagnet.impl.magnet.piratebay.TPBMagnetProvider;
import com.wexalian.jmagnet.api.IMagnetProvider;

module com.wexalian.jmagnet {
    requires com.wexalian.common;
    requires com.wexalian.nullability;
    
    requires com.google.gson;
    
    requires java.net.http;
    
    exports com.wexalian.jmagnet;
    exports com.wexalian.jmagnet.parser;
    exports com.wexalian.jmagnet.api;
    exports com.wexalian.jmagnet.tracker;
    
    opens com.wexalian.jmagnet.impl.magnet.piratebay to com.google.gson;
    
    uses IMagnetProvider;
    
    provides IMagnetProvider with TPBMagnetProvider, EZTVMagnetProvider, TempFileMagnetProvider;
}