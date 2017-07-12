package com.example.jenison_3631.roomtest.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by jenison-3631 on 14/06/17.
 */
@Dao
public interface AnimalDataAccess {
    @Query("SELECT * FROM animaltable")
    LiveData<List<AnimalTable>> getallAnimals();

    @Query("SELECT * FROM animaltable")
    List<AnimalTable> getallAnimalsnolive();

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    void insertAnimal(AnimalTable animal);

    @Delete
    void deleteAnimal(AnimalTable animal);
}
