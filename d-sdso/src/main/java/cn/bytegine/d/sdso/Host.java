package cn.bytegine.d.sdso;

import cn.bytegine.d.sdso.message.HostMessage;

public interface Host {

    String getId();

    String getVersion();

    String getAddress();

    int getPort();

    void sendMessage(HostMessage message);
}
