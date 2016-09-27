package mina;

import org.apache.mina.core.session.IoSession;

/**
 * Created by HUPENG on 2016/9/6.
 */
public interface SimpleMinaListener {
    /**
     * 收到消息
     * */
    public void onReceive(Object obj, IoSession ioSession);

    /**
     * 对方上线
     * */
    public void onLine(String msg);
}
