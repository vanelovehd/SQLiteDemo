package com.example.sqlitedemo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class SQLHelper extends SQLiteOpenHelper {

    static  final String DB_NAME="OrderFood.db";
    static  final  int DB_VERSION = 1;
    static  final String DB_TABLE_FOOD="Foods";
    static  final  String DB_TABLE_PRICE="Price";

    private String FOODS_ID="id";
    private String FOODS_NAME="name";
    private String FOODS_PRICE="price";

    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable= "Create table "+ DB_TABLE_FOOD + " ( " +
                "id INTEGER primary key AUTOINCREMENT not null, "+
                "name nvarchar(50), "+
                "price int "+
                ")";

        db.execSQL(queryCreateTable);
    }


    //Phương thức này tự động gọi khi đã có DB trên Storage, nhưng phiên bản khác
    //với DATABASE_VERSION (newVersion)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        //Tiến hành tạo bảng mới
        onCreate(db);
        }
    }

    public  void insertFoods(Contact contact){
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        // ID tự động tăng
        contentValues.put(FOODS_NAME,contact.getName());
        contentValues.put(FOODS_PRICE,contact.getPrice());

        sqLiteDatabase.insert(DB_TABLE_FOOD,null,contentValues);
    }

    public  void updateFoods(Contact contact){
      sqLiteDatabase = getWritableDatabase();
      contentValues = new ContentValues();

      contentValues.put(FOODS_NAME,contact.getName());
      contentValues.put(FOODS_PRICE,contact.getPrice());
      sqLiteDatabase.update(DB_TABLE_FOOD,contentValues,"id=?",
              new String []{String.valueOf(contact.getId())});
    }

    public void deleteFoods(int id){
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE_FOOD,"id=?", new String[]{String.valueOf(id)});


        //Xóa tất cả
        sqLiteDatabase.delete(DB_TABLE_FOOD,null, null);
    }

    public void deleteAllFoods(){
        sqLiteDatabase = getWritableDatabase();

        //Xóa tất cả
        sqLiteDatabase.delete(DB_TABLE_FOOD,null, null);
    }

    public List<Contact> getAllFood(){
        Contact contact;
        List<Contact> contactList = new ArrayList<>();

         cursor = sqLiteDatabase.query(false,DB_TABLE_FOOD,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(FOODS_ID));
            String name = cursor.getString(cursor.getColumnIndex(FOODS_NAME));
            int price = cursor.getInt(cursor.getColumnIndex(FOODS_PRICE));

            contact = new Contact(id,name,price);
            contactList.add(contact);
        }

        return  contactList;
    }

    private  void closeDB(){
        if(contentValues != null){
            contentValues.clear();
        }
        if(cursor != null){
            cursor.close();
        }
        if(sqLiteDatabase != null){
            sqLiteDatabase.close();
        }
    }
}
