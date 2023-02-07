import com.wexalian.jmagnet.provider.IMagnetProvider;

module com.wexalian.jmagnet {
    requires com.wexalian.common;
    requires com.wexalian.nullability;
    
    requires java.net.http;
    
    exports com.wexalian.jmagnet;
    exports com.wexalian.jmagnet.parser;
    exports com.wexalian.jmagnet.provider;
    exports com.wexalian.jmagnet.tracker;
    
    uses IMagnetProvider;
}