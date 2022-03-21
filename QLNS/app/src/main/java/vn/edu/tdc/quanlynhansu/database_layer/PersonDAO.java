package vn.edu.tdc.quanlynhansu.database_layer;

import android.app.AlarmManager;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.quanlynhansu.Person;

@Dao
public interface PersonDAO {
    @Query("SELECT * FROM " + Person.TABLE_NAME + " ORDER BY " + Person.NAME + " ASC")
    List<Person> getAllPerson();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllPerson(ArrayList<Person> people );

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long savePerson(Person person);

    @Delete
    int delPerson (Person person);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updatePerson(Person person);
}
