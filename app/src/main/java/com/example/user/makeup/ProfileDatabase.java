package com.example.user.makeup;

/**
 * Created by user on 04/09/2017.
 */
public class ProfileDatabase {
    private int _id;
    private String _name;
    private int _age;
    //constructor
    public ProfileDatabase(){
    }
    public ProfileDatabase(String name,int age){
        this._name=name;
        this._age=age;
    }
//getter and setter
    public int get_age() {return _age;}
    public void set_age(int _age) {this._age = _age;}
    public int get_id() {return _id;}
    public void set_id(int _id) {
        this._id = _id;
    }
    public ProfileDatabase(String name){
        this._name = name;
    }
    public String get_name() {
        return _name;
    }
    public void set_name(String _name) {
        this._name = _name;
    }
}
