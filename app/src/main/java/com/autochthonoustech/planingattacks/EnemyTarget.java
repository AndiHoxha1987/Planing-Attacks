package com.autochthonoustech.planingattacks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class EnemyTarget  extends AppCompatActivity{

    private ViewModel mEnemyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enemies);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView addEnemy = findViewById(R.id.add_enemy);
        addEnemy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EnemyTarget.this,AddEnemy.class);
                startActivity(i);
            }
        });

        RecyclerView recyclerViewEnemy = findViewById(R.id.list_enemy);
        final EnemiesAdapter adapterEnemy = new EnemiesAdapter(this);
        recyclerViewEnemy.setAdapter(adapterEnemy);
        recyclerViewEnemy.setLayoutManager(new LinearLayoutManager(this));

        mEnemyViewModel = ViewModelProviders.of(this).get(ViewModel.class);

        mEnemyViewModel.getAllEnemies().observe(this, new Observer<List<EnemiesClass>>() {
            @Override
            public void onChanged(@Nullable final List<EnemiesClass> cities) {
                // Update the cached copy of the words in the adapter.
                adapterEnemy.setEnemyCities(cities);
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
                        EnemiesClass myWord = adapterEnemy.getEnemiesAtPosition(position);

                        // Delete the word
                        mEnemyViewModel.deleteOneEnemy(myWord);
                    }
                });

        helper.attachToRecyclerView(recyclerViewEnemy);

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
            mEnemyViewModel.deleteAllEnemies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
