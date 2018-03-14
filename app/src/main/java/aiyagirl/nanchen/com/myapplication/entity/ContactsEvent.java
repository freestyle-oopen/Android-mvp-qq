package aiyagirl.nanchen.com.myapplication.entity;

/**
 * Created by Administrator on 2017/11/1.
 */

public class ContactsEvent {
    public String name;
    public boolean isAdded;
    public ContactsEvent(String name, boolean isAdded) {
        this.name = name;
        this.isAdded = isAdded;
    }
}
