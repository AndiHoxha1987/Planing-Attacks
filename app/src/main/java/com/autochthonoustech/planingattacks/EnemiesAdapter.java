package com.autochthonoustech.planingattacks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class EnemiesAdapter extends RecyclerView.Adapter<EnemiesAdapter.EnemiesViewHolder> {

    private final Context context;
    private final LayoutInflater mInflater;
    private List<EnemiesClass> mEnemiesCities; // Cached copy of words


    EnemiesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }


    @NonNull
    @Override
    public EnemiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_enemies, parent, false);
        return new EnemiesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EnemiesViewHolder holder, int position) {
        if (mEnemiesCities != null) {
            EnemiesClass current = mEnemiesCities.get(position);
            holder.worldSpeed.setText(current.getEWorldSpeed());
            holder.unitSpeed.setText(current.getEUnitSpeed());
            holder.xCordEnemy.setText(current.getEXCord());
            holder.yCordEnemy.setText(current.getEYCord());
            holder.hours.setText(current.getEHours());
            holder.minutes.setText(current.getEMinutes());
            holder.seconds.setText(current.getESeconds());

        } else {
            // Covers the case of data not being ready yet.
            holder.xCordEnemy.setText(R.string.no_city);
        }

    }

    void setEnemyCities(List<EnemiesClass> enemiesCities) {
        mEnemiesCities = enemiesCities;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mEnemiesCities != null)
            return mEnemiesCities.size();
        else return 0;
    }
    @SuppressWarnings("WeakerAccess")
    public EnemiesClass getEnemiesAtPosition (int position) {
        return mEnemiesCities.get(position);
    }

    class EnemiesViewHolder extends RecyclerView.ViewHolder {
        private final TextView worldSpeed;
        private final TextView unitSpeed;
        private final TextView xCordEnemy;
        private final TextView yCordEnemy;
        private final TextView hours;
        private final TextView minutes;
        private final TextView seconds;


        private EnemiesViewHolder(View itemView) {
            super(itemView);
            worldSpeed = itemView.findViewById(R.id.city_world_speed_enemy);
            unitSpeed = itemView.findViewById(R.id.city_unit_speed_enemy);
            xCordEnemy = itemView.findViewById(R.id.city_x_cord_enemy);
            yCordEnemy = itemView.findViewById(R.id.city_y_cord_enemy);
            hours = itemView.findViewById(R.id.hours_list);
            minutes = itemView.findViewById(R.id.minutes_list);
            seconds = itemView.findViewById(R.id.seconds_list);

            LinearLayout selectEnemy = itemView.findViewById(R.id.select_enemy);
            selectEnemy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.getSharedPreferences(
                            "com.example.myprofile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = context.getSharedPreferences("enemyData", Context.MODE_PRIVATE).edit();
                    editor.putString("WorldSpeed",worldSpeed.getText().toString());
                    editor.putString("UnitSpeed",unitSpeed.getText().toString());
                    editor.putString("xCord", xCordEnemy.getText().toString());
                    editor.putString("yCord", yCordEnemy.getText().toString());
                    editor.putString("hours", hours.getText().toString());
                    editor.putString("minutes", minutes.getText().toString());
                    editor.putString("seconds", seconds.getText().toString());
                    editor.apply();

                    Intent i = new Intent(context,MyCities.class);
                    context.startActivity(i);
                }
            });
        }
    }
}