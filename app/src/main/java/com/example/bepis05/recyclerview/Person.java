package com.example.bepis05.recyclerview;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Person {

    String name;
    String age;

    Bitmap photoBmp;

    public Person(String name, String age, Bitmap photo){
        this.name = name;
        this.age = age;
        this.photoBmp = photo;
    }


}
