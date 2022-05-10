package cc.zjlsx.shoutinvite.callbacks;

import net.md_5.bungee.api.Callback;

public class ConnectCallback implements Callback<Boolean> {

    @Override
    public void done(Boolean bool, Throwable throwable) {
        if (!bool) {
            return;
        }

    }
}
