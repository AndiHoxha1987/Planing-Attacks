package com.autochthonoustech.planingattacks;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MyCitiesAdapter extends RecyclerView.Adapter<MyCitiesAdapter.MyCitiesViewHolder> {


    private static SharedPreferences prefs ;
    private boolean bLancers = false;
    private boolean bSentries = false;
    private boolean bZerks = false;
    private boolean bKnights = false;
    private boolean bGuards = false;
    private boolean bRams = false;
    private boolean bNerds = false;

    private final LayoutInflater mInflater;
    private List<CitiesClass> mMyCities; // Cached copy of words

    MyCitiesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        prefs= context.getSharedPreferences("enemyData", MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyCitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_my_cities, parent, false);
        return new MyCitiesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCitiesViewHolder holder, int position) {
        if (mMyCities != null) {
            CitiesClass current = mMyCities.get(position);
            holder.cityName.setText(current.getName());
            holder.xCord.setText(current.getXCord());
            holder.yCord.setText(current.getYCord());

            int lancersSpeed = 12;
            int sentriesSpeed = 13;
            int zerksSpeed = 11;
            int knightsSpeed = 6;
            int guardsSpeed = 7;
            int ramsSpeed = 20;
            int nerdsSpeed = 25;
            double distance = calculateDistance(current.getXCord(), current.getYCord());

            int timeInSecondsLWT = calculateWalkingTime(lancersSpeed, distance);
            holder.lancersWt.setText(formatTime(timeInSecondsLWT));

            int timeInSecondsLLT= calculateLandTime(timeInSecondsLWT);
            holder.lancersLt.setText(formatTime(timeInSecondsLLT));

            int timeInSecondsSWT = calculateWalkingTime(sentriesSpeed, distance);
            holder.sentriesWt.setText(formatTime(timeInSecondsSWT));

            int timeInSecondsSLT= calculateLandTime(timeInSecondsSWT);
            holder.sentriesLt.setText(formatTime(timeInSecondsSLT));

            int timeInSecondsZWT = calculateWalkingTime(zerksSpeed, distance);
            holder.zerksWt.setText(formatTime(timeInSecondsZWT));

            int timeInSecondsZLT= calculateLandTime(timeInSecondsZWT);
            holder.zerksLt.setText(formatTime(timeInSecondsZLT));

            int timeInSecondsKWT = calculateWalkingTime(knightsSpeed, distance);
            holder.knightsWt.setText(formatTime(timeInSecondsKWT));

            int timeInSecondsKLT= calculateLandTime(timeInSecondsKWT);
            holder.knightsLt.setText(formatTime(timeInSecondsKLT));

            int timeInSecondsGWT = calculateWalkingTime(guardsSpeed, distance);
            holder.guardiansWt.setText(formatTime(timeInSecondsGWT));

            int timeInSecondsGLT= calculateLandTime(timeInSecondsGWT);
            holder.guardiansLt.setText(formatTime(timeInSecondsGLT));

            int timeInSecondsRWT = calculateWalkingTime(ramsSpeed, distance);
            holder.ramsWt.setText(formatTime(timeInSecondsRWT));

            int timeInSecondsRLT= calculateLandTime(timeInSecondsRWT);
            holder.ramsLt.setText(formatTime(timeInSecondsRLT));

            int timeInSecondsNWT = calculateWalkingTime(nerdsSpeed, distance);
            holder.nerdsWt.setText(formatTime(timeInSecondsNWT));

            int timeInSecondsNLT= calculateLandTime(timeInSecondsNWT);
            holder.nerdsLt.setText(formatTime(timeInSecondsNLT));

        } else {
            // Covers the case of data not being ready yet.
            holder.cityName.setText(R.string.no_city);
        }
    }

    void setMyCities(List<CitiesClass> myCities) {
        mMyCities = myCities;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mMyCities != null)
            return mMyCities.size();
        else return 0;
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(long seconds) {
        String time;
        if (seconds < 3600) {
            time = "0:" + DateUtils.formatElapsedTime(seconds);
        } else {
            time = DateUtils.formatElapsedTime(seconds);
        }
        return time;

    }

    private double calculateDistance(String x, String y) {

        if(!x.equals("")&&!y.equals("")) {
            int xInt = Integer.parseInt(x);
            int yInt = Integer.parseInt(y);

            String xCordEnemy = prefs.getString("xCord", "0");
            String yCordEnemy = prefs.getString("yCord", "0");

            int xEnemy = Integer.parseInt(xCordEnemy);
            int yEnemy = Integer.parseInt(yCordEnemy);

            return Math.sqrt(Math.pow((xInt - xEnemy), 2) + Math.pow((yInt - yEnemy), 2));
        }
        return 0;
    }

    private int calculateWalkingTime(int troopSpeed, double distance) {

        String unit = prefs.getString("UnitSpeed", "1");
        String world = prefs.getString("WorldSpeed", "1");
        int unitSpeed;
        int worldSpeed;
        try {
            unitSpeed = Integer.parseInt(unit);
            worldSpeed = Integer.parseInt(world);
        } catch (NumberFormatException ex) { // handle your exception
            unitSpeed = 1;
            worldSpeed = 1;
        }
        return (int) (troopSpeed * 60 *distance * unitSpeed/worldSpeed);
    }

    private int calculateLandTime(int walkTime) {
        String hoursEnemy = prefs.getString("hours", "0");
        String minutesEnemy = prefs.getString("minutes", "0");
        String secondsEnemy = prefs.getString("seconds", "0");

        int hEnemy = Integer.parseInt(hoursEnemy);
        int mEnemy = Integer.parseInt(minutesEnemy);
        int sEnemy = Integer.parseInt(secondsEnemy);

        return hEnemy * 3600 + mEnemy * 60 + sEnemy - walkTime;
    }
    @SuppressWarnings("WeakerAccess")
    public CitiesClass getCitiesAtPosition (int position) {
        return mMyCities.get(position);
    }

    class MyCitiesViewHolder extends RecyclerView.ViewHolder {
        private final TextView cityName;
        private final TextView xCord;
        private final TextView yCord;
        private final TextView lancersWt;
        private final TextView lancersLt;
        private final TextView sentriesWt;
        private final TextView sentriesLt;
        private final TextView zerksWt;
        private final TextView zerksLt;
        private final TextView knightsWt;
        private final TextView knightsLt;
        private final TextView guardiansWt;
        private final TextView guardiansLt;
        private final TextView ramsWt;
        private final TextView ramsLt;
        private final TextView nerdsWt;
        private final TextView nerdsLt;

        private MyCitiesViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.city_name_list);
            xCord = itemView.findViewById(R.id.x_cord_list);
            yCord = itemView.findViewById(R.id.y_cord_list);
            lancersWt = itemView.findViewById(R.id.lancers_wt);
            lancersLt = itemView.findViewById(R.id.lancers_lt);
            sentriesWt = itemView.findViewById(R.id.sentries_wt);
            sentriesLt = itemView.findViewById(R.id.sentries_lt);
            zerksWt = itemView.findViewById(R.id.zerks_wt);
            zerksLt = itemView.findViewById(R.id.zerks_lt);
            knightsWt = itemView.findViewById(R.id.knights_wt);
            knightsLt = itemView.findViewById(R.id.knights_lt);
            guardiansWt = itemView.findViewById(R.id.guards_wt);
            guardiansLt = itemView.findViewById(R.id.guards_lt);
            ramsWt = itemView.findViewById(R.id.rams_wt);
            ramsLt = itemView.findViewById(R.id.rams_lt);
            nerdsWt = itemView.findViewById(R.id.nerds_wt);
            nerdsLt = itemView.findViewById(R.id.nerds_lt);

            final LinearLayout lancers = itemView.findViewById(R.id.lancers);
            lancers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bLancers){
                    lancers.setBackgroundColor(Color.parseColor("#ffffff"));
                    helper();
                }else {
                        lancers.setBackgroundColor(Color.parseColor("#00ffffff"));
                        helper();
                    }
                }
            });

            final LinearLayout sentries = itemView.findViewById(R.id.sentries);
            sentries.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bSentries){
                        sentries.setBackgroundColor(Color.parseColor("#ffffff"));
                        helper();
                    }else {
                        sentries.setBackgroundColor(Color.parseColor("#00ffffff"));
                        helper();
                    }
                }
            });

            final LinearLayout zerks = itemView.findViewById(R.id.zerks);
            zerks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bZerks){
                        zerks.setBackgroundColor(Color.parseColor("#ffffff"));
                        helper();
                    }else {
                        zerks.setBackgroundColor(Color.parseColor("#00ffffff"));
                        helper();
                    }
                }
            });

            final LinearLayout knights = itemView.findViewById(R.id.knights);
            knights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bKnights){
                        knights.setBackgroundColor(Color.parseColor("#ffffff"));
                        helper();
                    }else {
                        knights.setBackgroundColor(Color.parseColor("#00ffffff"));
                        helper();
                    }
                }
            });

            final LinearLayout guards = itemView.findViewById(R.id.guards);
            guards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bGuards){
                        guards.setBackgroundColor(Color.parseColor("#ffffff"));
                        helper();
                    }else {
                        guards.setBackgroundColor(Color.parseColor("#00ffffff"));
                        helper();
                    }
                }
            });

            final LinearLayout rams = itemView.findViewById(R.id.rams);
            rams.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bRams){
                        rams.setBackgroundColor(Color.parseColor("#ffffff"));
                        helper();
                    }else {
                        rams.setBackgroundColor(Color.parseColor("#00ffffff"));
                        helper();
                    }
                }
            });

            final LinearLayout nerd = itemView.findViewById(R.id.nerds);
            nerd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bNerds){
                        nerd.setBackgroundColor(Color.parseColor("#ffffff"));
                        helper();
                    }else {
                        nerd.setBackgroundColor(Color.parseColor("#00ffffff"));
                        helper();
                    }
                }
            });
        }
    }

    private void helper(){
        if(!bLancers || !bSentries){
            bLancers = true;
            bSentries = true;
            bZerks = true;
            bKnights = true;
            bGuards = true;
            bRams = true;
            bNerds = true;
        }else {
            bLancers = false;
            bSentries = false;
            bZerks = false;
            bKnights = false;
            bGuards = false;
            bRams = false;
            bNerds = false;
        }
    }
}