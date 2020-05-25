package com.example.realm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyHelper {

    Realm realm;
    RealmResults<User> users;

    public MyHelper(Realm realm) {
        this.realm = realm;
    }

    public void selectFromDB(){
        users = realm.where(User.class).findAll();
    }
    public ArrayList<User> userArrayList(){

        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user: users){
            userArrayList.add(user);
        }
        return userArrayList;
    }

}

