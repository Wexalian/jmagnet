import com.wexalian.jmagnet.api.provider.IMagnetProvider;
import com.wexalian.jmagnet.api.provider.ITrackerProvider;

module com.wexalian.jmagnet.test {
    requires com.wexalian.common;
    requires com.wexalian.jmagnet;
    
    requires org.junit.jupiter.api;
    
    uses IMagnetProvider;
    uses ITrackerProvider;
    
    opens com.wexalian.jmagnet.test to org.junit.platform.commons;
}