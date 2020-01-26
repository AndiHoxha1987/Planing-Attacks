package com.autochthonoustech.planingattacks;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "enemy_cities_table")
class EnemiesClass {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private final int EID;

    @NonNull
    @ColumnInfo(name = "World Speed")
    private final String eWorldSpeed;

    @NonNull
    @ColumnInfo(name = "Unit Speed")
    private final String eUnitSpeed;

    @NonNull
    @ColumnInfo(name = "X Cord")
    private final String eXCord;

    @NonNull
    @ColumnInfo(name = "Y Cord")
    private final String eYCord;

    @NonNull
    @ColumnInfo(name = "Hours")
    private final String eHours;

    @NonNull
    @ColumnInfo(name = "Minutes")
    private final String eMinutes;

    @NonNull
    @ColumnInfo(name = "Seconds")
    private final String eSeconds;

    public EnemiesClass( int EID,@NonNull String eWorldSpeed,@NonNull String eUnitSpeed, @NonNull String eXCord,@NonNull String eYCord,@NonNull String eHours,
                         @NonNull String eMinutes,@NonNull String eSeconds) {
        this.EID = EID;
        this.eWorldSpeed=eWorldSpeed;
        this.eUnitSpeed = eUnitSpeed;
        this.eXCord = eXCord;
        this.eYCord = eYCord;
        this.eHours = eHours;
        this.eMinutes = eMinutes;
        this.eSeconds = eSeconds;
    }
    @SuppressWarnings("WeakerAccess")
    @NonNull
    public String getEUnitSpeed() {
        return eUnitSpeed;
    }

    @NonNull
    public String getEWorldSpeed() {
        return eWorldSpeed;
    }

    @SuppressWarnings("WeakerAccess")
    @NonNull
    public String getEXCord() {
        return eXCord;
    }
    @SuppressWarnings("WeakerAccess")
    @NonNull
    public String getEYCord() {
        return eYCord;
    }
    @SuppressWarnings("WeakerAccess")
    @NonNull
    public String getEHours() {
        return eHours;
    }
    @SuppressWarnings("WeakerAccess")
    @NonNull
    public String getEMinutes() {
        return eMinutes;
    }
    @SuppressWarnings("WeakerAccess")
    @NonNull
    public String getESeconds() {
        return eSeconds;
    }
    @SuppressWarnings("WeakerAccess")
    public int getEID() {
        return EID;
    }
}
