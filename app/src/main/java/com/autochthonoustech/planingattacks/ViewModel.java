package com.autochthonoustech.planingattacks;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
@SuppressWarnings("WeakerAccess")
public class ViewModel extends AndroidViewModel {
    private final Repository mRepository;
    private final LiveData<List<CitiesClass>> mAllMyCities;
    private final LiveData<List<EnemiesClass>> mAllEnemies;

    public ViewModel (Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllMyCities = mRepository.getmAllCities();
        mAllEnemies = mRepository.getAllEnemies();
    }

    LiveData<List<CitiesClass>> getmAllMyCities() { return mAllMyCities; }
    LiveData<List<EnemiesClass>> getAllEnemies() { return mAllEnemies; }
    @SuppressWarnings("WeakerAccess")
    public void insertMyCities(CitiesClass citiesClass) { mRepository.insertMyCities(citiesClass); }
    @SuppressWarnings("WeakerAccess")
    public void insertEnemies(EnemiesClass enemiesClass) { mRepository.insertEnemies(enemiesClass); }

    public void deleteAllMyCities() {mRepository.deleteAllMyCities();}
    public void deleteAllEnemies() {mRepository.deleteAllEnemies();}

    public void deleteOneCity(CitiesClass citiesClass){mRepository.deleteOneCity(citiesClass);}

    public void deleteOneEnemy(EnemiesClass enemiesClass){mRepository.deleteOneEnemy(enemiesClass);}


}
