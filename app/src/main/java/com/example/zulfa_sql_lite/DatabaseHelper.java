package com.example.zulfa_sql_lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DB_VERSION=1;
    private static final String DB_NAME="UserInfo";
    private static final String TABLE_NAME="tbl_user";
    private static final String KEY_NAME="name";
    private
    static final String KEY_AGE="age";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable="Create Table "+TABLE_NAME+"("+KEY_NAME+"TEXT PRIMARYKEY,"+KEY_AGE+" INTEGER"+")";
        db.execSQL(createUserTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql=("drop table if exists " +TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }
    public void insert(PersonBean personBean){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,personBean.getName());
        values.put(KEY_AGE,personBean.getAge());
        db.insert(TABLE_NAME,null,values);
    }
    public List<PersonBean> selectUserData(){
        ArrayList<PersonBean> userList=new ArrayList<PersonBean>();
        SQLiteDatabase db= getReadableDatabase();
        String[] columns={KEY_NAME,KEY_AGE};
        Cursor c =db.query(TABLE_NAME,columns,null,null,null,null,null);
        while (c.moveToNext()){
            String name=c.getString(0);
            int age=c.getInt(1);
            PersonBean personBean=new PersonBean();
            personBean.setName(name);
            personBean.setAge(age);
            userList.add(personBean);
        }
        return userList;
    }
    public void delete(String name){
        SQLiteDatabase db =getWritableDatabase();
        String whereClause=KEY_NAME+"='"+name+"'";
        db.delete(TABLE_NAME,whereClause,null);
    }
    public void update(PersonBean personBean){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_AGE,personBean.getAge());
        String whereClause=KEY_NAME+"='"+personBean.getName()+"'";
        db.update(TABLE_NAME,values,whereClause,null);
    }
}
