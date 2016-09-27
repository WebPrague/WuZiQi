package mina;

import org.apache.mina.core.session.IoSession;

/**
 * Created by HUPENG on 2016/9/6.
 */
public interface SimpleMinaListener {
    public void onReceive(Object obj, IoSession ioSession);
}
