package com.example.hp.myapplication.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.hp.myapplication.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "Tomato.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @SuppressLint("Range")
    public List<Order> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount"};
        String sqlTable = "Order";

        queryBuilder.setTables(sqlTable);
        Cursor cursor = queryBuilder.query(db, sqlSelect, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(
                        new Order(
                                cursor.getString(cursor.getColumnIndex("ProductId")),
                                cursor.getString(cursor.getColumnIndex("ProductName")),
                                cursor.getString(cursor.getColumnIndex("Quantity")),
                                cursor.getString(cursor.getColumnIndex("Price")),
                                cursor.getString(cursor.getColumnIndex("Discount"))
                        ));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format(
                "INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES ('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                "10.0");
        db.execSQL(query);

        // TODO: Replaced order.getDiscount()); with 10.0 as this method does not exist on the Order Class
    }

    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM OrderDetail";
        db.execSQL(query);
    }

    public Cursor getCart() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"0 _id", "FirstName", "LastName"};
        String sqlTables = "Employees";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();
        return c;

    }
}
