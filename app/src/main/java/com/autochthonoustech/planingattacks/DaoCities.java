package com.autochthonoustech.planingattacks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


// /Annotate the class declaration with @Dao to identify the class as a DAO class for Room.
@Dao

public interface DaoCities {

    // Declare a method to insert one word:
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CitiesClass citiesClass);

    //Declare a method to delete all the words:
    @Query("DELETE FROM my_cities_table")
    void deleteAll();

    @Delete
    void deleteOneCity(CitiesClass citiesClass);

    //Return LiveData in WordDao
    @Query("SELECT * from my_cities_table ORDER BY `ID` ASC")
    LiveData<List<CitiesClass>> getAllMyCities();

}
