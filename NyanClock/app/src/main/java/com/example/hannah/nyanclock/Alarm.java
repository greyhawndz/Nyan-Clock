package com.example.hannah.nyanclock;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by WilliamPC on 3/11/2016.
 */
public class Alarm {

    public static final String TABLE_NAME = "Alarms";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "Time";
    public static final String COLUMN_DAY = "Day";
    public static final String COLUMN_CLOCK = "Clock";

    private Timestamp timeStamp;
    private int id;
    private String day;
    private String time;
    private String clock; // AM or PM

    public Alarm(){
    }

    public Alarm(int id, String hour, String minutes, String day, String clock){
        this.id = id;
        this.day = day;
        time = hour+":"+minutes +" "+clock;

    }

    public Alarm(String hour, String minutes,  String day, String clock){
        time = hour+":"+minutes +" "+clock;
        this.day = day;
    }

    public Timestamp getTimeStamp(){
        return timeStamp;
    }

    public void createTimeStamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        try{
            Date d = dateFormat.parse(time);
            timeStamp = new java.sql.Timestamp(d.getTime());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setDay(String day){
        this.day = day;
    }

    public String getDay(){
        return day;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getClock(){
        return clock;
    }

    public void setClock(String clock){
        this.clock = clock;
    }
}
