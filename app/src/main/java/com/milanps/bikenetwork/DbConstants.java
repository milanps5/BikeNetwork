package com.milanps.bikenetwork;

/**
 * Milan Pop Stefanija
 */
public class DbConstants {
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CITY = "city";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";



    public static final String TABLE_COMPANY= "company";


    public static final String CREATE_TABLE_COMPANY = "CREATE TABLE " + DbConstants.TABLE_COMPANY + "(" + DbConstants.KEY_ID + " INTEGER PRIMARY KEY," +DbConstants.KEY_NAME+ " TEXT,"  +DbConstants.KEY_CITY + " TEXT," + DbConstants.KEY_COUNTRY+ " TEXT," + DbConstants.KEY_LATITUDE + " REAL," + DbConstants.KEY_LONGITUDE + " REAL" + ")";


}
