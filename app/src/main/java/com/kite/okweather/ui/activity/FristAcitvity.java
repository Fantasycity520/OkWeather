package com.kite.okweather.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.kite.okweather.R;
import com.kite.okweather.utils.ActivityCollector;

import org.litepal.LitePal;

import java.util.Timer;
import java.util.TimerTask;

public class FristAcitvity extends AppCompatActivity {
    private static final String TAG = "FristAcitvity";
    AppCompatActivity appCompatActivity;
    ImageView image_frist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist_acitvity);
        appCompatActivity = this;
        image_frist = findViewById(R.id.image_frist);
        ActivityCollector.addActivity(this);
        LitePal.getDatabase();

        Timer timer = new Timer();
        //Glide.with(getApplicationContext()).load("http://195.133.53.243:8080/05_Stu/web/first_img.png").into(image_frist);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                    }
                });
            }
        }, 999);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Main.class));
                ActivityCollector.removeActivity(appCompatActivity);
                finish();
            }
        }, 1000);

    }
}