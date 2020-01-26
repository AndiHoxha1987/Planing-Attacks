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

public class AddMyCity extends AppCompatActivity {

    private ViewModel mMyViewModel;
    private EditText X_Cord ;
    private EditText Y_Cord ;
    private EditText Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_my_city);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Name = findViewById(R.id.edit_city_name);
        X_Cord = findViewById(R.id.edit_x_cord);
        Y_Cord = findViewById(R.id.edit_y_cord);
        mMyViewModel= ViewModelProviders.of(this).get(ViewModel.class);
        TextView save = findViewById(R.id.add_city_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(addCity()){
                 Intent i = new Intent(AddMyCity.this,MyCities.class);
                 startActivity(i);
             }else{
                 Toast.makeText(getApplicationContext(),"Fill all lines please",
                         Toast.LENGTH_LONG).show();
             }
            }
        });

    }

    private boolean addCity()  {
        int cID=0;
        String name = Name.getText().toString().trim();
        String xString = X_Cord.getText().toString().trim();
        String yString = Y_Cord.getText().toString().trim();

        if(!name.equals("")&&!xString.equals("")&&!yString.equals("")){
        CitiesClass city = new CitiesClass(cID,name,xString,yString);
        mMyViewModel.insertMyCities(city);
        return true;
        }
        return false;
    }
}