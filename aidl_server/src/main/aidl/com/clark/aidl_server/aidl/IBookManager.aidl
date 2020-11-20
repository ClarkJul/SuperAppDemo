package com.clark.aidl_server.aidl;
import com.clark.aidl_server.aidl.Book;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}