package com.clark.aidl_server.manual.server;

import android.os.IInterface;
import android.os.RemoteException;


import com.clark.aidl_server.aidl.Book;

import java.util.List;

/**
 * 这个类用来定义服务端 RemoteService 具备什么样的能力
 */
public interface ManualBookManager extends IInterface {
    List<Book> getBooks() throws RemoteException;
    void addBook(Book book) throws RemoteException;
}
