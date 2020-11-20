package com.clark.aidl_server.aidl;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class FruitManager implements FruitManagerInterface{
    private static  volatile FruitManager instance;

    public static FruitManager getInstance(){
        if (instance==null){
            synchronized (FruitManager.class){
                if (instance==null){
                    instance=new FruitManager();
                }
            }
        }
        return instance;
    }

    private List<Fruit> fruitList;

    public FruitManager() {
        fruitList=new ArrayList<>();
        fruitList.add(new Fruit("橙子","橙色"));
        fruitList.add(new Fruit("葡萄","紫色"));
    }


    @Override
    public List<Fruit> getAllFruit() {
        return fruitList;
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitList.add(fruit);
    }

    @Override
    public void removeFruit(Fruit fruit) {
        int index=-1;
        for (int i = 0; i < fruitList.size(); i++) {
            if (TextUtils.equals(fruit.getName(),fruitList.get(i).getName())){
                index=i;
                break;
            }
        }
        if (index!=-1)
        fruitList.remove(index);
    }
}
