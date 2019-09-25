package com.milanps.bikenetwork;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String BROADCAST_ACTION = "com.milanps.bikenetwork.broadcast";

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    LinearLayoutManager layoutManager;
    List<CompanyDTO> visibleCompanies;

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseManager.initializeInstance(new DatabaseHelper(getApplicationContext()));


        startAutoDownloadService();

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DataAdapter(getBaseContext(), new ArrayList<CompanyDTO>());
        adapter.setClickListener(new DataAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                CompanyDTO clickedCompany = visibleCompanies.get(position);

                Intent intent = new Intent(getBaseContext(), LocationActivity.class);
                intent.putExtra("name", clickedCompany.getName());
                intent.putExtra("city", clickedCompany.getLocationDTO().getCity());
                intent.putExtra("country", clickedCompany.getLocationDTO().getCountry());
                intent.putExtra("longitude", clickedCompany.getLocationDTO().getLongitude());
                intent.putExtra("latitude", clickedCompany.getLocationDTO().getLatitude());
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter);

        updateUi();

        setNetworkNotifier();
    }

    private void startAutoDownloadService() {
        Intent intent = new Intent(getApplicationContext(), AutoDownloadService.class);
        intent.addCategory(AutoDownloadService.TAG);
        startService(intent);

    }

    private void updateUi() {
        DatabaseManager.getInstance().executeQuery(new QueryExecutor() {
            @Override
            public void run(SQLiteDatabase database) {
                CompanyDAO companyDAO = new CompanyDAO(database);
                //companyDAO.deleteAllCompanies();
                visibleCompanies = companyDAO.getAllCompanies();
            }
        });

        setListItems(visibleCompanies);
    }

    private void setListItems(final List<CompanyDTO> companies) {

        //visibleCompanies = new ArrayList<>(companies);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new DataAdapter(getBaseContext(), companies);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void setNetworkNotifier() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateUi();
            }
        };

        this.registerReceiver(broadcastReceiver, new IntentFilter(MainActivity.BROADCAST_ACTION));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.companies_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                visibleCompanies = adapter.getFilteredList();
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getBaseContext(), ConfigActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
