package com.clark.process_aidl.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.clark.aidl_server.aidl.IFruitManager;
import com.clark.process_aidl.R;
import com.clark.process_aidl.files.Book;
import com.clark.aidl_server.aidl.Fruit;
import com.clark.process_aidl.files.IBookManager;
import com.google.gson.Gson;

import java.util.List;

public class AidlActivity extends AppCompatActivity {
    private static final String TAG="-----aidl-----";
    private boolean isSyncConnected =false;
    private boolean isunSyncConnected =false;
    private ServiceConnection syncConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("------>","连接成功");
            isSyncConnected =true;
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list= bookManager.getBookList();
                Log.d("------>",list.size()+"");
                Log.d("------>",list.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isSyncConnected =false;
        }
    };

    private ServiceConnection unsyncConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"连接成功");
            isunSyncConnected =true;
            fruitManager = IFruitManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"断开连接");
            isunSyncConnected=false;
            fruitManager=null;
        }
    };
    private IFruitManager fruitManager;
    private TextView tvDisplayFruit;
    private IBookManager bookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        tvDisplayFruit = findViewById(R.id.tv_display_fruit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isSyncConnected){
            unbindService(syncConnection);
        }
        if (isunSyncConnected){
            unbindService(unsyncConnection);
        }
    }

    public void onSyncAppClick(View view) {
        if (!isSyncConnected){
            Intent intent = new Intent();
            intent.setAction("com.android.clark.aidlserver.BookManagerService");
            intent.setPackage("com.android.clark.superappdemo");
            bindService(intent, syncConnection, BIND_AUTO_CREATE);
        }
    }

    public void onDifferentAppClick(View view) {
        if (!isunSyncConnected){
            Intent intent = new Intent();
            intent.setAction("com.clark.aidl_server.AidlService");
            intent.setPackage("com.clark.aidl_server");
            bindService(intent, unsyncConnection, BIND_AUTO_CREATE);
        }
    }


    public void onQueryFruitClick(View view) {
        Gson gson = new Gson();
        if (fruitManager!=null) {
            try {
                tvDisplayFruit.setText(gson.toJson(fruitManager.getAllFruit()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onRemoveFruitClick(View view) {
        Gson gson = new Gson();
        if (fruitManager!=null) {
            try {
                fruitManager.removeFruit(new Fruit("橙子","橙色"));
                tvDisplayFruit.setText(gson.toJson(fruitManager.getAllFruit()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onQueryBookClick(View view) {
        Gson gson = new Gson();
        if (bookManager!=null) {
            try {
                tvDisplayFruit.setText(gson.toJson(bookManager.getBookList()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}