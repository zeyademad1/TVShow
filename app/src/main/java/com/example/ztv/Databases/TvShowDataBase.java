package com.example.ztv.Databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ztv.models.TvShow;

@Database(entities = TvShow.class, version = 1, exportSchema = false)
public abstract class TvShowDataBase extends RoomDatabase {

    private  static  TvShowDataBase instance;

    public abstract TVShowDAO tvShowDAO();

    public static synchronized TvShowDataBase getInstance(@NonNull final Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context , TvShowDataBase.class
                    , "TvShowDataBase").build();
        }
        return instance;
    }
}
