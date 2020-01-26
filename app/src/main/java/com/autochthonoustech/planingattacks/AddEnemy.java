package com.autochthonoustech.planingattacks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class AddEnemy extends AppCompatActivity {

    private ViewModel mEnemyViewModel;
    private EditText X_Cord ;
    private EditText Y_Cord ;
    private EditText Unit_Speed;
    private EditText World_Speed;
    private EditText Land_Time_Hours;
    private EditText Land_Time_Minutes;
    private EditText Land_Time_Seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_enemy);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        X_Cord = findViewById(R.id.x_cord);
        Y_Cord = findViewById(R.id.y_cord);
        Land_Time_Hours = findViewById(R.id.hours);
        Land_Time_Minutes = findViewById(R.id.minutes);
        Land_Time_Seconds = findViewById(R.id.seconds);
        Unit_Speed = findViewById(R.id.unit_speed);
        World_Speed = findViewById(R.id.world_speed);
        mEnemyViewModel= ViewModelProviders.of(this).get(ViewModel.class);
        TextView save = findViewById(R.id.save_enemy);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveEnemy()){
                    Intent i = new Intent(AddEnemy.this,EnemyTarget.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Fill all lines please",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean saveEnemy()  {
        int EID = 0;
        String worldString = World_Speed.getText().toString().trim();
        String unitString = Unit_Speed.getText().toString().trim();
        String xString = X_Cord.getText().toString().trim();
        String yString = Y_Cord.getText().toString().trim();
        String hoursString=Land_Time_Hours.getText().toString().trim();
        String minutesString=Land_Time_Minutes.getText().toString().trim();
        String secondsString=Land_Time_Seconds.getText().toString().trim();
        if(!unitString.equals("")&&!xString.equals("")&&!yString.equals("")&&!hoursString.equals("")
                &&!minutesString.equals("")&&!secondsString.equals("")){
            EnemiesClass enemies = new EnemiesClass(EID,worldString,unitString,xString,yString,hoursString,minutesString,secondsString);
            mEnemyViewModel.insertEnemies(enemies);
            return true;
        }
        return false;
    }
}
