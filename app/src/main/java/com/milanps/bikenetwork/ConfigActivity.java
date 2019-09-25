package com.milanps.bikenetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class ConfigActivity extends AppCompatActivity {
    EditText edtCache;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        edtCache = findViewById(R.id.edtCache);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = Integer.parseInt(edtCache.getText().toString());
                if (c > 14 && c < 61) {
                    stopAutoDownloadService();
                    AutoDownloadService.INTERVAL = c * 60000;
                    startAutoDownloadService();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(getApplicationContext(), "Please insert value between 15 and 60", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void startAutoDownloadService() {
        Intent intent = new Intent(getApplicationContext(), AutoDownloadService.class);
        intent.addCategory(AutoDownloadService.TAG);
        startService(intent);

    }

    private void stopAutoDownloadService() {
        Intent intent = new Intent(getApplicationContext(), AutoDownloadService.class);
        intent.addCategory(AutoDownloadService.TAG);
        stopService(intent);

    }
}
