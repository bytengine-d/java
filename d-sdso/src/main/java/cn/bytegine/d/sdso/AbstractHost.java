package cn.bytegine.d.sdso;

/**
 * @author sunkaihan
 * @version 1.0
 */
public abstract class AbstractHost<C extends AbstractHost<C>> implements Host {
    private final C me;
    private String id;
    private String version;
    private String address;
    private int port;

    protected AbstractHost(String id) {
        this.me = (C) this;
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public C setVersion(String version) {
        this.version = version;
        return me;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public C setAddress(String address) {
        this.address = address;
        return me;
    }

    @Override
    public int getPort() {
        return port;
    }

    public C setPort(int port) {
        this.port = port;
        return me;
    }
}
