package com.clark.process_aidl.files;
import com.clark.process_aidl.files.Book;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
