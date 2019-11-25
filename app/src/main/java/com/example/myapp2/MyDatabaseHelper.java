package com.example.myapp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public MyDatabaseHelper(@Nullable Context context){
        super(context,"database",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_USER = "create table user(user_id INTEGER(11) PRIMARY KEY,user_name text,password text,introduction text,photo blob,address text);";
        db.execSQL(TABLE_USER);
        String TABLE_SHOP = "create table shop(shop_id INTEGER PRIMARY KEY,user_id integer(11),shop_name text,shop_picture blob);";
        db.execSQL(TABLE_SHOP);
        String TABLE_GOODS = "create table goods(goods_id INTEGER PRIMARY KEY,shop_id integer,goods_name text,goods_price double,goods_num integer,goods_picture blob);";
        db.execSQL(TABLE_GOODS);
        String TABLE_HISTORY = "create table history(id INTEGER PRIMARY KEY autoincrement,history_id text,goods_id integer,goods_name text,goods_price double,goods_num integer,goods_picture blob);";
        db.execSQL(TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists shop");
        db.execSQL("drop table if exists goods");
        db.execSQL("drop table if exists history");
        onCreate(db);

    }
}
