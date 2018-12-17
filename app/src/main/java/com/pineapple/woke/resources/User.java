package com.pineapple.woke.resources;

public class User {

    public String name;

    public User(){
        name = null;
    }

    public User(String n){
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
