package com.clark.aidl_server.aidl;

import java.util.List;

public interface FruitManagerInterface {
    List<Fruit> getAllFruit();
    void addFruit(Fruit fruit);
    void removeFruit(Fruit fruit);
}
