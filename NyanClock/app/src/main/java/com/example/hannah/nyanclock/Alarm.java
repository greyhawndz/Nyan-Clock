package com.example.hannah.nyanclock;

import java.util.ArrayList;
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
    public static final String COLUMN_SUNDAY = "Sunday";
    public static final String COLUMN_MONDAY = "Monday";
    public static final String COLUMN_TUESDAY = "Tuesday";
    public static final String COLUMN_WEDNESDAY = "Wednesday";
    public static final String COLUMN_THURSDAY = "Thursday";
    public static final String COLUMN_FRIDAY = "Friday";
    public static final String COLUMN_SATURDAY = "Saturday";
    public static final String COLUMN_CLOCK = "Clock";

    private Timestamp timeStamp;
    private int id;
    private boolean sun;
    private boolean mon;





    private boolean tues;
    private boolean wed;
    private boolean thurs;



    private boolean fri;
    private boolean sat;
    private String time;
    private String clock; // AM or PM

    public Alarm(){
    }

    //Values of day array SHOULD be in order: Sunday - Saturday
    public Alarm(int id, String hour, String minutes, boolean[] day, String clock){
        this.id = id;
        time = hour+":"+minutes +" "+clock;
        sun = day[0];
        mon = day[1];
        tues = day[2];
        wed = day[3];
        thurs = day[4];
        fri = day[5];
        sat = day[6];
        System.out.println(sun + " " + mon + " " + tues);

    }

    //Values of day array SHOULD be in order: Sunday - Saturday
    public Alarm(String hour, String minutes,  boolean[] day, String clock){
        time = hour+":"+minutes +" "+clock;
        sun = day[0];
        mon = day[1];
        tues = day[2];
        wed = day[3];
        thurs = day[4];
        fri = day[5];
        sat = day[6];
        System.out.println(sun + " " + mon + " " + tues);
    }

    public Timestamp getTimeStamp(){
        return timeStamp;
    }

    //Converts the time String of h:mm a to a time stamp
    public void createTimeStamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        try{
            Date d = dateFormat.parse(time);
            timeStamp = new Timestamp(d.getTime());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean isTues() {
        return tues;
    }

    public void setTues(boolean tues) {
        this.tues = tues;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean isThurs() {
        return thurs;
    }

    public void setThurs(boolean thurs) {
        this.thurs = thurs;
    }

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
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
