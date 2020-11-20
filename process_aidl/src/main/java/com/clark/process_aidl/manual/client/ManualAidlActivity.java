package com.clark.process_aidl.manual.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clark.process_aidl.R;
import com.clark.process_aidl.files.Book;
import com.clark.process_aidl.manual.server.ManualBookManager;
import com.clark.process_aidl.manual.server.Stub;
import com.google.gson.Gson;

import java.util.List;

public class ManualAidlActivity extends AppCompatActivity {
    private static final String TAG="manual_aidl";
    private ManualBookManager bookManager;
    private boolean isConnection = false;
    private Gson gson;
    private TextView tvDisplayBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_aidl);
        tvDisplayBooks = findViewById(R.id.tv_display_book);
        gson = new Gson();
    }

    /**
     * 绑定远程服务
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.clark.aidl_server.ManualAidlService");
        intent.setPackage("com.clark.aidl_server");
        try {
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnection = true;
            bookManager = Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected: bind success" );
/*            if (bookManager != null) {
                try {
                    List<Book> books = bookManager.getBooks();
                    Log.e(TAG, gson.toJson(books));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }*/
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: unbind success" );
            isConnection = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
/*        if (!isConnection) {
            attemptToBindService();
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isConnection) {
            unbindService(serviceConnection);
        }
    }

    public void onQueryBookClick(View view) {
        if (!isConnection) return;
        try {
            List<Book> books = bookManager.getBooks();
            Log.e(TAG, "onQueryBookClick: "+gson.toJson(books));
            tvDisplayBooks.setText(gson.toJson(books));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onBindServiceClick(View view) {
        if (!isConnection) {
            attemptToBindService();
        }
    }

    public void onAddBookClick(View view) {
        if (!isConnection) {
            attemptToBindService();
            return;
        }
        if (bookManager == null)
            return;
        try {
            Book book = new Book(10,"麻瓜的世界");
            bookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}