package com.milanps.bikenetwork;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Milan Pop Stefanija
 */
public class AutoDownloadService extends Service {

    public final static String TAG = "AutoDownloadService";
    public static int INTERVAL = 900000; //15 minutes default value
    private Timer timer = new Timer();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerMethod();
            }
        }, 0, INTERVAL );


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

    private void timerMethod(){
        DatabaseManager.initializeInstance(new DatabaseHelper(getApplicationContext()));

        GetNetworkService service = RetrofitClientInstance.getRetrofitInstance().create(GetNetworkService.class);
        Call<CompanyList> call = service.getAllCompanies();
        call.enqueue(new Callback<CompanyList>() {
            @Override
            public void onResponse(Call<CompanyList> call, Response<CompanyList> response) {

                CompanyList elements = response.body();
                final List<CompanyDTO> companies = elements.getCompanies();
                DatabaseManager.getInstance().executeQuery(new QueryExecutor() {
                    @Override
                    public void run(SQLiteDatabase database) {
                        CompanyDAO companyDAO = new CompanyDAO(database);
                        companyDAO.deleteAllCompanies();
                        for(CompanyDTO company : companies){
                            companyDAO.createCompany(company);
                        }

                    }

                });
                Intent broadCastIntent = new Intent();
                broadCastIntent.setAction(MainActivity.BROADCAST_ACTION);
                sendBroadcast(broadCastIntent);
            }


            @Override
            public void onFailure(Call<CompanyList> call, Throwable t) {
                //Log.d("greska", t.getMessage());

                DatabaseManager.getInstance().executeQuery(new QueryExecutor() {
                    @Override
                    public void run(SQLiteDatabase database) {
                        CompanyDAO companyDAO = new CompanyDAO(database);
                        companyDAO.deleteAllCompanies();
                    }

                });
                Intent broadCastIntent = new Intent();
                broadCastIntent.setAction(MainActivity.BROADCAST_ACTION);
                sendBroadcast(broadCastIntent);
            }
        });

    }
}
