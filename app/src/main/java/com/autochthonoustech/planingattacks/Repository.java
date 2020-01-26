package com.autochthonoustech.planingattacks;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
@SuppressWarnings("WeakerAccess")
public class Repository {
    //Add member variables for the DAO and the list of words.
    private final DaoCities mDaoCities;
    private final LiveData<List<CitiesClass>> mAllCities;

    private final DaoEnemies mDaoEnemies;
    private final LiveData<List<EnemiesClass>> mEnemyCities;

    //Add a constructor that gets a handle to the database and initializes the member variables.
    Repository(Application application) {
        AttacksDatabase db = AttacksDatabase.getMyCitiesDatabase(application);
        mDaoCities = db.daoCities();
        mAllCities = mDaoCities.getAllMyCities();

        AttacksDatabase db1 = AttacksDatabase.getEnemyCitiesDatabase(application);
        mDaoEnemies = db1.daoEnemies();
        mEnemyCities = mDaoEnemies.getAllEnemies();
    }

    //add a wrapper method called getAllWords() that returns the cached words as LiveData.
    //Room executes all queries on a separate thread. Observed LiveData notifies the observer when the data changes.
    LiveData<List<CitiesClass>> getmAllCities() {
        return mAllCities;
    }
    LiveData<List<EnemiesClass>> getAllEnemies() {
        return mEnemyCities;
    }

    public void insertMyCities (CitiesClass citiesClass) {
        new insertAsyncTaskMyCities(mDaoCities).execute(citiesClass);
    }

    public void insertEnemies (EnemiesClass enemiesClass) {
        new insertAsyncTaskEnemies(mDaoEnemies).execute(enemiesClass);
    }

    public void deleteAllMyCities()  {
        new deleteAllMyCitiesAsyncTask(mDaoCities).execute();
    }

    public void deleteAllEnemies()  {
        new deleteAllEnemiesAsyncTask(mDaoEnemies).execute();
    }

    public void deleteOneCity(CitiesClass word)  {
        new deleteOneCityAsyncTask(mDaoCities).execute(word);
    }

    public void deleteOneEnemy(EnemiesClass word)  {
        new deleteOneCEnemyAsyncTask(mDaoEnemies).execute(word);
    }

    private static class insertAsyncTaskMyCities extends AsyncTask<CitiesClass, Void, Void> {

        private final DaoCities mAsyncTaskDao;

        insertAsyncTaskMyCities(DaoCities dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CitiesClass... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllMyCitiesAsyncTask extends AsyncTask<Void, Void, Void> {
        private final DaoCities mAsyncTaskMyCitiesDao;

        deleteAllMyCitiesAsyncTask(DaoCities dao) {
            mAsyncTaskMyCitiesDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskMyCitiesDao.deleteAll();
            return null;
        }
    }

    private static class deleteOneCityAsyncTask extends AsyncTask<CitiesClass, Void, Void> {
        private final DaoCities mAsyncTaskDao;

        deleteOneCityAsyncTask(DaoCities dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CitiesClass... params) {
            mAsyncTaskDao.deleteOneCity(params[0]);
            return null;
        }
    }

    private static class insertAsyncTaskEnemies extends AsyncTask<EnemiesClass, Void, Void> {

        private final DaoEnemies mAsyncTaskDao;

        insertAsyncTaskEnemies(DaoEnemies dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final EnemiesClass... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllEnemiesAsyncTask extends AsyncTask<Void, Void, Void> {
        private final DaoEnemies mAsyncTaskEnemiesDao;

        deleteAllEnemiesAsyncTask(DaoEnemies dao) {
            mAsyncTaskEnemiesDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskEnemiesDao.deleteAll();
            return null;
        }
    }

    private static class deleteOneCEnemyAsyncTask extends AsyncTask<EnemiesClass, Void, Void> {
        private final DaoEnemies mAsyncTaskDao;

        deleteOneCEnemyAsyncTask(DaoEnemies dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final EnemiesClass... params) {
            mAsyncTaskDao.deleteOneEnemy(params[0]);
            return null;
        }
    }
}
