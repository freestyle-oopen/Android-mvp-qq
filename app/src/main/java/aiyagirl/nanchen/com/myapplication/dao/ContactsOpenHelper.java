package aiyagirl.nanchen.com.myapplication.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/10/27.
 */

public class ContactsOpenHelper extends SQLiteOpenHelper {
    public ContactsOpenHelper(Context context) {
        super(context, "contacts_list.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table t_contact (_id integer primary key,username varchar(20),contact varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
