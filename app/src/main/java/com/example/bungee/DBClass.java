package com.example.bungee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBClass extends SQLiteOpenHelper {
    public static final String dbname = "AppDB";
    Context context;

    //IF COUNT > 0 THEN ITS TRUE, ELSE FALSE
    public DBClass(@Nullable Context context) {
        super(context, dbname, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (email TEXT, user TEXT, password TEXT, type TEXT, isBanned INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS products (seller TEXT, itemName TEXT, itemPrice FLOAT, itemQuantity INTEGER, itemSold INTEGER, boughtCount INTEGER, itemImage TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS orders (whoOrdered TEXT, productName TEXT, productQuantity INTEGER, seller TEXT, orderDate TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean addUser(String user, String password, String email, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Cursor c = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        if (c.getCount() == 0) {
            cv.put("email", email);
            cv.put("user", user);
            cv.put("password", password);
            cv.put("type", type);
            cv.put("isBanned", 0);

            db.insert("users", null, cv);
        }

        int count = c.getCount();
        c.close();

        return count == 0;
    }

    public boolean checkCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean addProduct(String seller, String itemName, Float itemPrice, int itemQuantity, int itemSold, int boughtCount, String itemImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Cursor c = db.rawQuery("SELECT * FROM products WHERE itemName = ?", new String[]{itemName});

        if (c.getCount() == 0) {
            cv.put("seller", seller);
            cv.put("itemName", itemName);
            cv.put("itemPrice", itemPrice);
            cv.put("itemQuantity", itemQuantity);
            cv.put("itemSold", itemSold);
            cv.put("boughtCount", boughtCount);
            cv.put("itemImage", itemImage);

            db.insert("products", null, cv);
        }
        int count = c.getCount();
        c.close();

        return count == 0;
    }

    public List<ProductData> searchProduct(String itemName) {
        List<ProductData> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE itemName LIKE ?", new String[]{"%" + itemName + "%"});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int sellerIndex = cursor.getColumnIndex("seller");
                int itemNameIndex = cursor.getColumnIndex("itemName");
                int itemPriceIndex = cursor.getColumnIndex("itemPrice");
                int itemQuantityIndex = cursor.getColumnIndex("itemQuantity");
                int itemSoldIndex = cursor.getColumnIndex("itemSold");
                int boughtCountIndex = cursor.getColumnIndex("boughtCount");
                int itemImageIndex = cursor.getColumnIndex("itemImage");

                if (sellerIndex != -1 && itemNameIndex != -1 && itemPriceIndex != -1 &&
                        itemQuantityIndex != -1 && itemSoldIndex != -1 && boughtCountIndex != -1 &&
                        itemImageIndex != -1) {

                    String seller = cursor.getString(sellerIndex);
                    String itemNameValue = cursor.getString(itemNameIndex);
                    float itemPrice = cursor.getFloat(itemPriceIndex);
                    int itemQuantity = cursor.getInt(itemQuantityIndex);
                    int itemSold = cursor.getInt(itemSoldIndex);
                    int boughtCount = cursor.getInt(boughtCountIndex);
                    String itemImage = cursor.getString(itemImageIndex);

                    result.add(new ProductData(seller, itemNameValue, itemPrice, Uri.parse(itemImage), itemQuantity, itemSold, boughtCount));
                } else {
                    Log.e("DBClass", "Column index is -1 for one or more columns");
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return result;
    }

    public String getType(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});

        String type = null;
        if (c.moveToFirst()) {
            int index = c.getColumnIndex("type");
            type = c.getString(index);
        }
        c.close();
        Log.d("TYPE", "Type is: " + type);
        return type;
    }

    public List<ProductData> getAllProducts() {
        List<ProductData> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int sellerIndex = cursor.getColumnIndex("seller");
                int itemNameIndex = cursor.getColumnIndex("itemName");
                int itemPriceIndex = cursor.getColumnIndex("itemPrice");
                int itemQuantityIndex = cursor.getColumnIndex("itemQuantity");
                int itemSoldIndex = cursor.getColumnIndex("itemSold");
                int boughtCountIndex = cursor.getColumnIndex("boughtCount");
                int itemImageIndex = cursor.getColumnIndex("itemImage");

                if (sellerIndex != -1 && itemNameIndex != -1 && itemPriceIndex != -1 &&
                        itemQuantityIndex != -1 && itemSoldIndex != -1 && boughtCountIndex != -1 &&
                        itemImageIndex != -1) {

                    String seller = cursor.getString(sellerIndex);
                    String itemName = cursor.getString(itemNameIndex);
                    float itemPrice = cursor.getFloat(itemPriceIndex);
                    int itemQuantity = cursor.getInt(itemQuantityIndex);
                    int itemSold = cursor.getInt(itemSoldIndex);
                    int boughtCount = cursor.getInt(boughtCountIndex);
                    String itemImage = cursor.getString(itemImageIndex);

                    productList.add(new ProductData(seller, itemName, itemPrice, Uri.parse(itemImage), itemQuantity, itemSold, boughtCount));
                } else {
                    Log.e("DBClass", "Column index is -1 for one or more columns");
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return productList;
    }


}
