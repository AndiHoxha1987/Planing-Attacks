package com.autochthonoustech.planingattacks;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_cities_table")

//Create a class called CitiesClass.

class CitiesClass {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private final int mID;

    @NonNull
    @ColumnInfo(name = "City Name")
    private final String mName;

    @NonNull
    @ColumnInfo(name = "X Cord")
    private final String mXCord;

    @NonNull
    @ColumnInfo(name = "Y Cord")
    private final String mYCord;



    //Add a constructor that takes a word string as an argument. Add the @NonNull annotation so that the parameter can never be null.
    public CitiesClass(int mID,@NonNull String name, @NonNull String xCord,@NonNull String yCord ) {
        this.mID = mID;
        this.mName = name;
        this.mXCord = xCord;
        this.mYCord = yCord; }

        // Add a "getter" method that returns the word. Room requires "getter" methods on the entity classes
        // so that it can instantiate your objects.


    @NonNull
    public String getName() {
        return mName;
    }

    @SuppressWarnings("WeakerAccess")
    @NonNull
    public String getXCord() {
        return mXCord;
    }

    @SuppressWarnings("WeakerAccess")
    @NonNull
    public String getYCord() {
        return mYCord;
    }

    @SuppressWarnings("WeakerAccess")
    public int getID() {
        return mID;
    }
}
