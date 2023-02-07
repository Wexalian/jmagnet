import com.wexalian.jmagnet.plugins.EZTVMagnetProvider;
import com.wexalian.jmagnet.plugins.piratebay.TPBMagnetProvider;
import com.wexalian.jmagnet.plugins.temp.TempFileMagnetProvider;
import com.wexalian.jmagnet.provider.IMagnetProvider;

module com.wexalian.jmagnet.plugins {
    requires com.wexalian.common;
    requires com.wexalian.jmagnet;
    requires com.wexalian.nullability;
    
    requires java.net.http;
    
    requires com.google.gson;
    
    exports com.wexalian.jmagnet.plugins;
    exports com.wexalian.jmagnet.plugins.piratebay;
    
    provides IMagnetProvider with TPBMagnetProvider, EZTVMagnetProvider, TempFileMagnetProvider;
}