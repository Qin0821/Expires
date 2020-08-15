package com.simpure.expires.ui.home;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.simpure.expires.IMyAidlInterface;

public class AIDLService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public interface OnLoginListener {
        void login(String userName, String passWord);
    }

    private OnLoginListener onLoginListener;

    public void setOnLoginListener(OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    class MyBinder extends IMyAidlInterface.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                               float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void login(String userName, String passWord) throws RemoteException {
            if (onLoginListener != null) {
                onLoginListener.login(userName, passWord);
            }
        }

        public AIDLService getService() {
            return AIDLService.this;
        }
    }
}
