package aiyagirl.nanchen.com.myapplication.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import aiyagirl.nanchen.com.myapplication.dao.ContactsOpenHelper;

/**
 * Created by Administrator on 2017/10/27.
 */

public class DBUtil {

    private static ContactsOpenHelper openHelper ;
    public static void initDB(Context context){
        if(openHelper == null){
            openHelper = new ContactsOpenHelper(context);
        }
    }

    public static void saveContacts(String username , List<String>  contacts) {
      if(openHelper == null){
            throw new RuntimeException("没有初始化数据库工具类!");
      }
        SQLiteDatabase database = openHelper.getWritableDatabase();
        //开启事务
        database.beginTransaction();
        database.delete("t_contact", "username = ?", new String[]{username});
        ContentValues values=new ContentValues();
        values.put("username",username);
        for (int i=0;i<contacts.size();i++){
          values.put("contact",contacts.get(i));
            database.insert("t_contact",null,values);
        }
        //提交事务
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
    }


    public static List<String> getContacts(String username){
        if(openHelper == null){
            throw new RuntimeException("没有初始化数据库工具类!");
        }
        List<String>  contactsList =new ArrayList<>();
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor query = database.query("t_contact", new String[]{"contact"}, "username = ?", new String[]{username}, null, null, "contact");
        while (query.moveToNext()){
            String contact = query.getString(0);
            contactsList.add(contact);
        }
        query.close();
        return contactsList;
    }
}
