package com.netanel.downloadandinstall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;

    private static final String appUrl = "http://149.49.55.90/avenger/sample01.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "button pressed...", Toast.LENGTH_SHORT).show();
                UpdateApp atualizaApp = new UpdateApp();
                atualizaApp.setContext(MainActivity.this);
                atualizaApp.execute(appUrl);
            }
        });
    }
}
