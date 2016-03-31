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
                + Cat.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Cat.COLUMN_NAME + " TEXT, " + Cat.COLUMN_HAPPINESS + " INT, "
                + Cat.COLUMN_HUNGER + " INT);";
        String sql2 = "CREATE TABLE " +Alarm.TABLE_NAME + "("
                +Alarm.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +Alarm.BROADCAST_ID + " INTEGER,"
                +Alarm.COLUMN_TIME + " TEXT, "
                +Alarm.COLUMN_SUNDAY + " TEXT, "
                +Alarm.COLUMN_MONDAY + " TEXT, "
                +Alarm.COLUMN_TUESDAY + " TEXT, "
                +Alarm.COLUMN_WEDNESDAY + " TEXT, "
                +Alarm.COLUMN_THURSDAY + " TEXT, "
                +Alarm.COLUMN_FRIDAY + " TEXT, "
                +Alarm.COLUMN_SATURDAY + " TEXT, "
                +Alarm.COLUMN_CLOCK +" TEXT);";
        String sql3 = "CREATE TABLE " + "Broadcast_Table" + "("
                +Alarm.COLUMN_ID + " INTEGER,"
                +Alarm.BROADCAST_ID + " INTEGER);";
        db.execSQL(sql);
        db.execSQL(sql2);

        // Inserted cat into cat database
        Cat cat = new Cat(100,100);
        ContentValues cv1 = new ContentValues();
        cv1.put(Cat.COLUMN_NAME, cat.getName());
        cv1.put(Cat.COLUMN_HAPPINESS, cat.getHappiness());
        cv1.put(Cat.COLUMN_HUNGER, cat.getHunger());
        db.insert(Cat.TABLE_NAME, null, cv1);

        // Inserted the default alarm
        boolean[] setDays = {false, true, false, false, false, false, false};
        Alarm alarm = new Alarm("06", "00", setDays, "AM");
        ContentValues cv2 = new ContentValues();
        //cv2.put(Alarm.BROADCAST_ID, 0);
        cv2.put(Alarm.COLUMN_TIME, alarm.getTime());
        cv2.put(Alarm.COLUMN_CLOCK, alarm.getClock());
        cv2.put(Alarm.COLUMN_SUNDAY, alarm.isSun());
        cv2.put(Alarm.COLUMN_MONDAY, alarm.isMon());
        cv2.put(Alarm.COLUMN_TUESDAY, alarm.isTues());
        cv2.put(Alarm.COLUMN_WEDNESDAY, alarm.isWed());
        cv2.put(Alarm.COLUMN_THURSDAY, alarm.isThurs());
        cv2.put(Alarm.COLUMN_FRIDAY, alarm.isFri());
        cv2.put(Alarm.COLUMN_SATURDAY, alarm.isSat());
        db.insert(Alarm.TABLE_NAME, null, cv2);
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

        //db.close();
        //c.close();
        return cat;
    }

    public Alarm getAlarm(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Alarm.TABLE_NAME, null, " " + Alarm.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null,null,null);
        Alarm alarm = new Alarm();
        if(c.moveToFirst()){
            alarm.setId(c.getInt(c.getColumnIndex(Alarm.COLUMN_ID)));
            alarm.setTime(c.getString(c.getColumnIndex(Alarm.COLUMN_TIME)));
            if(c.getString(c.getColumnIndex(Alarm.COLUMN_SUNDAY)).equals("1"))
            {
                alarm.setSun(Boolean.parseBoolean("true"));
            }
            else
            {
                alarm.setSun(Boolean.parseBoolean("false"));
            }
            if(c.getString(c.getColumnIndex(Alarm.COLUMN_MONDAY)).equals("1"))
            {
                alarm.setMon(Boolean.parseBoolean("true"));
            }
            else
            {
                alarm.setMon(Boolean.parseBoolean("false"));
            }
            if(c.getString(c.getColumnIndex(Alarm.COLUMN_TUESDAY)).equals("1"))
            {
                alarm.setTues(Boolean.parseBoolean("true"));
            }
            else
            {
                alarm.setTues(Boolean.parseBoolean("false"));
            }
            if(c.getString(c.getColumnIndex(Alarm.COLUMN_WEDNESDAY)).equals("1"))
            {
                alarm.setWed(Boolean.parseBoolean("true"));
            }
            else
            {
                alarm.setWed(Boolean.parseBoolean("false"));
            }
            if(c.getString(c.getColumnIndex(Alarm.COLUMN_THURSDAY)).equals("1"))
            {
                alarm.setThurs(Boolean.parseBoolean("true"));
            }
            else
            {
                alarm.setThurs(Boolean.parseBoolean("false"));
            }
            if(c.getString(c.getColumnIndex(Alarm.COLUMN_FRIDAY)).equals("1"))
            {
                alarm.setFri(Boolean.parseBoolean("true"));
            }
            else
            {
                alarm.setFri(Boolean.parseBoolean("false"));
            }
            if(c.getString(c.getColumnIndex(Alarm.COLUMN_SATURDAY)).equals("1"))
            {
                alarm.setSat(Boolean.parseBoolean("true"));
            }
            else
            {
                alarm.setSat(Boolean.parseBoolean("false"));
            }
            alarm.setClock(c.getString(c.getColumnIndex(Alarm.COLUMN_CLOCK)));
        }
        else{
            alarm = null;
        }
        //db.close();
        //c.close();
        return alarm;
    }

    public Cursor queryAlarms(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Alarm.TABLE_NAME, null, null, null, null, null, null);
        return c;
    }

    public long addBroadCast(int id, int broadcast){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Alarm.COLUMN_ID, id);
        cv.put(Alarm.BROADCAST_ID, broadcast);

        return db.insert("Broadcast_Table", null, cv);
    }

    public Cursor queryBroadcasts(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Broadcast_Table", null, " " + Alarm.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null,null,null);
        return c;
    }

    public long addAlarm(Alarm alarm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Alarm.COLUMN_TIME, alarm.getTime());
        cv.put(Alarm.COLUMN_SUNDAY, alarm.isSun());
        cv.put(Alarm.COLUMN_MONDAY, alarm.isMon());
        cv.put(Alarm.COLUMN_TUESDAY, alarm.isTues());
        cv.put(Alarm.COLUMN_WEDNESDAY, alarm.isWed());
        cv.put(Alarm.COLUMN_THURSDAY, alarm.isThurs());
        cv.put(Alarm.COLUMN_FRIDAY, alarm.isFri());
        cv.put(Alarm.COLUMN_SATURDAY, alarm.isSat());
        cv.put(Alarm.COLUMN_CLOCK, alarm.getClock());

        long id = db.insert(Alarm.TABLE_NAME, null, cv);
        //db.close();
        return id;
    }

    public long editAlarm(Alarm alarm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Alarm.COLUMN_TIME, alarm.getTime());
        cv.put(Alarm.COLUMN_SUNDAY, alarm.isSun());
        cv.put(Alarm.COLUMN_MONDAY, alarm.isMon());
        cv.put(Alarm.COLUMN_TUESDAY, alarm.isTues());
        cv.put(Alarm.COLUMN_WEDNESDAY, alarm.isWed());
        cv.put(Alarm.COLUMN_THURSDAY, alarm.isThurs());
        cv.put(Alarm.COLUMN_FRIDAY, alarm.isFri());
        cv.put(Alarm.COLUMN_SATURDAY, alarm.isSat());
        cv.put(Alarm.COLUMN_CLOCK,alarm.getClock());

        return db.update(Alarm.TABLE_NAME, cv, Alarm.COLUMN_ID + "=?", new String[]{String.valueOf(alarm.getId())});
    }

    public int deleteAlarm(int id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(Alarm.TABLE_NAME, Alarm.COLUMN_ID + "=? ", new String[]{String.valueOf(id)});
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
