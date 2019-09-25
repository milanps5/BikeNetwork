package com.milanps.bikenetwork;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Milan Pop Stefanija
 */
public class CompanyDAO {
    private static final String select_all_companies= "SELECT * FROM " + DbConstants.TABLE_COMPANY;
    private static final String delete_all_companies = "DELETE FROM " + DbConstants.TABLE_COMPANY;

    private SQLiteDatabase mDatabase;

    public CompanyDAO(SQLiteDatabase database) {
        mDatabase = database;
    }

    public void createCompany(CompanyDTO company){
        try{
            mDatabase.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DbConstants.KEY_NAME,company.getName());
            contentValues.put(DbConstants.KEY_CITY,company.getLocationDTO().getCity());
            contentValues.put(DbConstants.KEY_COUNTRY,company.getLocationDTO().getCountry());
            contentValues.put(DbConstants.KEY_LATITUDE,company.getLocationDTO().getLatitude());
            contentValues.put(DbConstants.KEY_LONGITUDE,company.getLocationDTO().getLongitude());

            long companyId = mDatabase.insert(DbConstants.TABLE_COMPANY,null,contentValues);
            mDatabase.setTransactionSuccessful();

        }catch (Exception e){
            Log.e("CompanyDAO", e.getMessage());
        }finally {
            mDatabase.endTransaction();
        }
    }

    public void deleteAllCompanies() {
        mDatabase.execSQL(delete_all_companies);
    }

    public List<CompanyDTO> getAllCompanies() {
        Cursor cursor = mDatabase.rawQuery(select_all_companies, null);

        List<CompanyDTO> configs = manageCursor(cursor);

        closeCursor(cursor);

        return configs;
    }

    protected List<CompanyDTO> manageCursor(Cursor cursor) {
        List<CompanyDTO> dataList = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CompanyDTO c = cursorToCompanyDTO(cursor);
                dataList.add(c);
                cursor.moveToNext();
            }
        }

        return dataList;
    }

    private CompanyDTO cursorToCompanyDTO(Cursor cursor) {
        int nameIndex = cursor.getColumnIndex(DbConstants.KEY_NAME);
        int cityIndex = cursor.getColumnIndex(DbConstants.KEY_CITY);
        int countryIndex = cursor.getColumnIndex(DbConstants.KEY_COUNTRY);
        int latitudeIndex = cursor.getColumnIndex(DbConstants.KEY_LATITUDE);
        int longitudeIndex = cursor.getColumnIndex(DbConstants.KEY_LONGITUDE);

        CompanyDTO company = new CompanyDTO();
        LocationDTO location = new LocationDTO();
        location.setCity(cursor.getString(cityIndex));
        location.setCountry(cursor.getString(countryIndex));
        location.setLatitude(cursor.getFloat(latitudeIndex));
        location.setLongitude(cursor.getFloat(longitudeIndex));
        company.setName(cursor.getString(nameIndex));
        company.setLocationDTO(location);

        return company;
    }

    protected void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

}
