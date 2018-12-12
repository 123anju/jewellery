package com.JewelleryShop.Jewellery.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.JewelleryShop.Jewellery.Model.Order;
import com.JewelleryShop.Jewellery.common.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabh omer on 31-Mar-18.
 */

public class CreateDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "O6";
    public static final String COL_1 = "ProductId";
    public static final String COL_2 = "Quantity";
    public static final String COL_3 = "Price";
    public static final String COL_4 = "Discount";
    public static final String COL_5 = "Mobile";
    public static final String COL_6 = "ProductName";
    public static final String COL_7 = "Menu";
    public static final String COL_0 = "ID";

    public static final String COL_8 = "Refer";
    private int totalItem=0;


    public CreateDB(Context context) {
        super(context,"student2.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductId TEXT,ProductName TEXT,Quantity INTEGER,Price INTEGER,Discount INTEGER)");

        db.execSQL("create table  if not exists " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductId TEXT,Quantity TEXT,Price TEXT,Discount TEXT,Mobile TEXT,Productname TEXT,Menu Text,Refer TEXT)");
    }
    public boolean insertData(String productId, String quantity, String price, String mobile, String productName, String refer, String discount, String menu) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table  if not exists " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductId TEXT,Quantity TEXT,Price TEXT,Discount TEXT,Mobile TEXT,Productname TEXT,Menu Text,Refer TEXT)");
        //db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductId TEXT,Quantity TEXT,Price TEXT,Discount TEXT)");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,productId);
        contentValues.put(COL_2,quantity);
        contentValues.put(COL_3,price);
        contentValues.put(COL_4,discount);
        contentValues.put(COL_5,mobile);
        contentValues.put(COL_6,productName);
        contentValues.put(COL_7,menu);
        contentValues.put(COL_8,refer);


        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
//

    }

    public List<Order> getCarts()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table  if not exists " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductId TEXT,Quantity TEXT,Price TEXT,Discount TEXT,Mobile TEXT,Productname TEXT,Menu Text,Refer TEXT)");
        Cursor c = db.rawQuery("select * from "+TABLE_NAME +" where Mobile='"+ Common.currentUser.Phone+"'" ,null);

        final List<Order> result=new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                result.add(new Order(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8)
                ));
            } while (c.moveToNext());
        }

        return  result;
    }
    public int getTotalItem()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table  if not exists " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductId TEXT,Quantity TEXT,Price TEXT,Discount TEXT,Mobile TEXT,Productname TEXT,Menu Text,Refer TEXT)");
        Cursor c = db.rawQuery("select * from "+TABLE_NAME +" where Mobile='"+ Common.currentUser.Phone+"'" ,null);

        final List<Order> result=new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                result.add(new Order(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8)
                ));
                totalItem=totalItem+ Integer.parseInt(c.getString(2));
            } while (c.moveToNext());
        }

        return  totalItem;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public void cleanCart()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        // db.delete(TABLE_NAME, "ID = ?",new String[] {id});

    }
    public void addTOCard(Order order)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table  if not exists " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductId TEXT,Quantity TEXT,Price TEXT,Discount TEXT,Mobile TEXT,Productname TEXT,Menu Text,Refer TEXT)");
        //db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductId TEXT,Quantity TEXT,Price TEXT,Discount TEXT)");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_0,order.getID());
        contentValues.put(COL_1,order.getProductId());
        contentValues.put(COL_2,order.getQuantity());
        contentValues.put(COL_3,order.getPrice());
        contentValues.put(COL_4,order.getDiscount());
        contentValues.put(COL_5,order.getMobile());
        contentValues.put(COL_6,order.getProductName());
        contentValues.put(COL_7,order.getMenu());
        contentValues.put(COL_8,order.getRefer());


       db.insert(TABLE_NAME,null ,contentValues);
    }
    public void deletetoId(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME +" where ID='"+id +"'");

    }


//    public int getCountCart() {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("create table  if not exists " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductId TEXT,Quantity TEXT,Price TEXT,Discount TEXT,Mobile TEXT,Productname TEXT,Menu Text,Refer TEXT)");
//        Cursor c = db.rawQuery("SELECT COUNT(*) FROM "+TABLE_NAME +" where Mobile='"+ Common.currentUser.Phone+"'" ,null);
//        int i=0;
//        if(c.moveToFirst())
//        {
//            do{
//                i=c.getInt(0);
//
//            }while (c.moveToNext());
//
//        }
//        return i;
//    }
}
