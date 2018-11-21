package com.android.clark.myjavalib;

import java.util.ArrayList;
import java.util.List;

public class MyTestClass {



    public static void main(String[] args){
        List<Integer> list1;
        List<Integer> list2=new ArrayList<>();
        list1=list2;
        list1.add(5);
        if(list1==null){
            System.out.println("我是空的");
        }else if (list1.size()==0){
            System.out.println("我不是空的，我没有数据");
        }else {
            System.out.println("哈哈。。。我不是空的，我有数据");
        }
    }
}
