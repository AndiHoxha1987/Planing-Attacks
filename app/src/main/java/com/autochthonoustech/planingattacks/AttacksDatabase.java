package com.autochthonoustech.planingattacks;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {CitiesClass.class, EnemiesClass.class}, version = 3, exportSchema = false)

abstract class AttacksDatabase extends RoomDatabase {

    private static AttacksDatabase INSTANCE;
    private static AttacksDatabase INSTANCE_ENEMY;

    //Define the DAOs that work with the database. Provide an abstract "getter" method for each @Dao.
    public abstract DaoCities daoCities();

    //Define the DAOs that work with the database. Provide an abstract "getter" method for each @Dao.
    public abstract DaoEnemies daoEnemies();

    static AttacksDatabase getMyCitiesDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AttacksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AttacksDatabase.class, "my_cities")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallbackMyCities)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallbackMyCities =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsyncMyCities(INSTANCE).execute();
                }
            };

    static AttacksDatabase getEnemyCitiesDatabase(final Context context) {
        if (INSTANCE_ENEMY == null) {
            synchronized (AttacksDatabase.class) {
                if (INSTANCE_ENEMY == null) {
                    INSTANCE_ENEMY = Room.databaseBuilder(context.getApplicationContext(),
                            AttacksDatabase.class, "enemy_cities")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallbackEnemy)
                            .build();
                }
            }
        }
        return INSTANCE_ENEMY;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallbackEnemy =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsyncEnemies(INSTANCE_ENEMY).execute();
                }
            };

    /**
     * Populate the database of my cities in the background.
     */
    private static class PopulateDbAsyncMyCities extends AsyncTask<Void, Void, Void> {

        private final DaoCities mDao;

        CitiesClass[] cities;

        PopulateDbAsyncMyCities(AttacksDatabase db) {
            mDao = db.daoCities();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            if(cities != null){
            for (int i = 0; i <= cities.length - 1; i++) {
                CitiesClass citiesClass = new CitiesClass(cities[i].getID(),cities[i].getName(),cities[i].getXCord(),cities[i].getYCord());
                mDao.insert(citiesClass);
                }
            }
            return null;
        }

    }

    /**
     * Populate the database of my cities in the background.
     */
    private static class PopulateDbAsyncEnemies extends AsyncTask<Void, Void, Void> {


        private final DaoEnemies eDao;

        EnemiesClass[] enemies;

        PopulateDbAsyncEnemies(AttacksDatabase db) {
            eDao = db.daoEnemies();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            if(enemies != null){
            for (int i = 0; i <= enemies.length - 1; i++) {
                EnemiesClass enemiesClass = new EnemiesClass(enemies[i].getEID(),enemies[i].getEWorldSpeed(),enemies[i].getEUnitSpeed(),enemies[i].getEXCord(),enemies[i].getEYCord(),
                        enemies[i].getEHours(),enemies[i].getEMinutes(),enemies[i].getESeconds());
                eDao.insert(enemiesClass);
            }}
            return null;
        }
    }
}
