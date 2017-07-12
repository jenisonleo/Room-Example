package com.example.jenison_3631.roomtest.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by jenison-3631 on 14/06/17.
 */
@Database(entities = {AnimalTable.class},version = 1)
public abstract class RoomDB extends RoomDatabase {

    public abstract AnimalDataAccess animalDataAccess();
}
