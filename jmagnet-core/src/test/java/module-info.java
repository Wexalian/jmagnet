import com.wexalian.jmagnet.provider.IMagnetProvider;

module com.wexalian.jmagnet.test {
    requires com.wexalian.common;
    requires com.wexalian.jmagnet;
    
    requires org.junit.jupiter.api;
    
    opens com.wexalian.jmagnet.test to org.junit.platform.commons;
    
    uses IMagnetProvider;
}