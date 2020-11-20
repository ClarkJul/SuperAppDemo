package com.clark.aidl_server.aidl;
import com.clark.aidl_server.aidl.Fruit;
interface IFruitManager {
    List<Fruit> getAllFruit();
    void addFruit(in Fruit fruit);
    void removeFruit(in Fruit fruit);
}
