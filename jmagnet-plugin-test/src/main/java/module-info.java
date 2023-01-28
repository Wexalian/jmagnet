import com.wexalian.jmagnet.plugins.EZTVMagnetProvider;
import com.wexalian.jmagnet.plugins.TPBMagnetProvider;
import com.wexalian.jmagnet.plugins.temp.TempFileMagnetProvider;
import com.wexalian.jmagnet.provider.IMagnetProvider;

module com.wexalian.jmagnet.plugins {
    requires com.wexalian.jmagnet;
    requires com.wexalian.nullability;
    
    exports com.wexalian.jmagnet.plugins;
    
    provides IMagnetProvider with TPBMagnetProvider, EZTVMagnetProvider, TempFileMagnetProvider;
}