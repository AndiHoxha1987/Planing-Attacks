package com.autochthonoustech.planingattacks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

public class MyCities extends AppCompatActivity {

    private ViewModel mMyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cities);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        SharedPreferences prefs = this.getSharedPreferences("enemyData", MODE_PRIVATE);
        String sXCordEnemy = prefs.getString("xCord", "0");
        String sYCordEnemy = prefs.getString("yCord", "0");
        String sHoursEnemy = prefs.getString("hours", "0");
        String sMinutesEnemy = prefs.getString("minutes", "0");
        String sSecondsEnemy = prefs.getString("seconds", "0");

        TextView xCordEnemy = findViewById(R.id.city_x_cord_enemy_main);
        xCordEnemy.setText(sXCordEnemy);
        TextView yCordEnemy = findViewById(R.id.city_y_cord_enemy_main);
        yCordEnemy.setText(sYCordEnemy);
        TextView hours = findViewById(R.id.hours_list_enemy_main);
        hours.setText(sHoursEnemy);
        TextView minutes = findViewById(R.id.minutes_list_enemy_main);
        minutes.setText(sMinutesEnemy);
        TextView seconds = findViewById(R.id.seconds_list_enemy_main);
        seconds.setText(sSecondsEnemy);


        TextView addCity = findViewById(R.id.add_city);
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyCities.this,AddMyCity.class);
                startActivity(i);
            }
        });
        RecyclerView recyclerViewMyCities = findViewById(R.id.recycleView_my_cities);
        final MyCitiesAdapter adapterMy = new MyCitiesAdapter(this);
        recyclerViewMyCities.setAdapter(adapterMy);
        recyclerViewMyCities.setLayoutManager(new LinearLayoutManager(this));
        mMyViewModel = ViewModelProviders.of(this).get(ViewModel.class);

        mMyViewModel.getmAllMyCities().observe(this, new Observer<List<CitiesClass>>() {
            @Override
            public void onChanged(@Nullable final List<CitiesClass> cities) {
                // Update the cached copy of the words in the adapter.
                adapterMy.setMyCities(cities);
            }
        });

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        CitiesClass myWord = adapterMy.getCitiesAtPosition(position);

                        // Delete the word
                        mMyViewModel.deleteOneCity(myWord);
                    }
                });

        helper.attachToRecyclerView(recyclerViewMyCities);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_delete_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        if(item.getItemId()==R.id.delete_all){
            mMyViewModel.deleteAllMyCities();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
