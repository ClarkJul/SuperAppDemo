package com.clark.aidl_server.manual.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.clark.aidl_server.aidl.Book;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ManualAidlService extends Service {
    private static final String TAG="manual_aidl";
    private List<Book> books = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book(88,"三体");
        books.add(book);
        Log.e(TAG, "onCreate: "+(new Gson().toJson(books)));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ManualAidlService" );
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ManualAidlService" );
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return bookManager;
    }

    private final Stub bookManager = new Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            Log.e(TAG, "getBooks: ManualAidlService" );
            synchronized (this) {
                if (books != null) {
                    return books;
                }
                return new ArrayList<>();
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.e(TAG, "addBook: ManualAidlService" );
            synchronized (this) {
                if (books == null) {
                    books = new ArrayList<>();
                }
                if (book == null)
                    return;
                books.add(book);
                Log.e(TAG, "books: " + book.toString());
            }
        }
    };
}
