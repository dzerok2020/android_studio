package vn.edu.tdc.quanlynhansu.database_layer;

import static vn.edu.tdc.quanlynhansu.database_layer.PersonDatabase.DB_VERSION;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.edu.tdc.quanlynhansu.Person;

@Database(entities = {Person.class}, version = DB_VERSION)
public abstract class PersonDatabase extends RoomDatabase {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "person_database";
    private static volatile PersonDatabase DATABASE;

    //Async task processing
    private static final int NUMBER_OF_EXEC_THREAD = 10;
    public static final  ExecutorService dbReadWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_EXEC_THREAD);

    // Định ngĩa database
    public abstract PersonDAO getPersonDao();
    //
    public static PersonDatabase getDatabase (Context context){
        if (DATABASE == null){
            synchronized (PersonDatabase.class){
                DATABASE = Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, DB_NAME).build();

            }
        }
        return DATABASE;
    }
}
