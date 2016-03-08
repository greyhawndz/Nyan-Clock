package com.example.hannah.nyanclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by WilliamPC on 3/9/2016.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    final static String SCHEMA = "Nyan Clock";

    public DatabaseOpenHelper(Context context){
        super(context, SCHEMA, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Cat.TABLE_NAME + "("
                + Cat.COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Cat.COLUMN_NAME + " TEXT, " + Cat.COLUMN_HAPPINESS + " INT, "
                + Cat.COLUMN_HUNGER + " INT);";
        db.execSQL(sql);
    }

    public long createCat(Cat cat){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Cat.COLUMN_NAME, cat.getName());
        cv.put(Cat.COLUMN_HAPPINESS, cat.getHappiness());
        cv.put(Cat.COLUMN_HUNGER, cat.getHunger());
        long id = db.insert(Cat.TABLE_NAME, null, cv);
        db.close();
        return id;
    }

    public Cat getCat(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Cat.TABLE_NAME, null, " " + Cat.COLUMN_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        Cat cat = new Cat();
        if(c.moveToFirst()){
            cat.setId(c.getInt(c.getColumnIndex(Cat.COLUMN_ID)));
            cat.setName(c.getString(c.getColumnIndex(Cat.COLUMN_NAME)));
            cat.setHappiness(c.getInt(c.getColumnIndex(Cat.COLUMN_HAPPINESS)));
            cat.setHunger(c.getInt(c.getColumnIndex(Cat.COLUMN_HUNGER)));
        }else{
            cat = null;
        }

        db.close();
        c.close();
        return cat;
    }

    public int updateCat(Cat cat)
    {
        // updatedNote contains the ORIGINAL_id
        // updatedNote containes the NEW title and note
        // return number of rows affected

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Cat.COLUMN_HAPPINESS, cat.getHappiness());
        cv.put(Cat.COLUMN_HUNGER, cat.getHunger());

        return db.update( Cat.TABLE_NAME, cv, Cat.COLUMN_ID + "=? ", new String[]{String.valueOf(cat.getId())} );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
