package com.lm.testzxing;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.lm.testzxing.R.id.id_bt1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView textView;
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.id_text);
        button1= (Button) findViewById(id_bt1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,DecoderActivity.class),0);

            }
        });
    }
}
