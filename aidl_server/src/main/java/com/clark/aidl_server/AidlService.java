package com.clark.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.clark.aidl_server.aidl.Fruit;
import com.clark.aidl_server.aidl.FruitManager;
import com.clark.aidl_server.aidl.IFruitManager;

import java.util.List;

public class AidlService extends Service {
    private static String TAG = "AidlService";
    private Binder mBinder = new IFruitManager.Stub() {
        @Override
        public List<Fruit> getAllFruit() throws RemoteException {
            return FruitManager.getInstance().getAllFruit();
        }

        @Override
        public void addFruit(Fruit fruit) throws RemoteException {
            FruitManager.getInstance().addFruit(fruit);
        }

        @Override
        public void removeFruit(Fruit fruit) throws RemoteException {
            FruitManager.getInstance().removeFruit(fruit);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
