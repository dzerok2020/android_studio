package vn.edu.tdc.quanlynhansu.database_layer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import vn.edu.tdc.quanlynhansu.Person;
import vn.edu.tdc.quanlynhansu.adapters.MyListViewAdapter;
import vn.edu.tdc.quanlynhansu.adapters.MyRecyclerViewAdapter;

public class PersonDatabaseAPIs {
    private PersonDatabase database;
    private PersonDAO DAO;

    public PersonDatabaseAPIs(Context context) {
        //For test database
        database = PersonDatabase.getDatabase(context);
        DAO = database.getPersonDao();
    }

    private class Temp{
        private long position;
        private boolean isComplte;
    }
    //Database APIs Definition
    public void saveAllPerson(final ArrayList<Person> people){
        PersonDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                DAO.saveAllPerson(people);
            }
        });
    }
    public int saveAllPersonVersion2(ArrayList<Person> people)  {
        int saveCount = 0;
        for (Person person: people){
            long position = savePerson(person);
            if(position != -1){
                saveCount++;
            }
        }
        return saveCount;
    }
    public long savePerson(final Person person){
        final Temp temp = new Temp();
        PersonDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                temp.isComplte = false;
                temp.position = DAO.savePerson(person);
                temp.isComplte = true;
            }
        });
        // wait until the thread complete
        while (!temp.isComplte){
        }
        //update the id in the table person in the listview
        if (temp.position != -1){
            person.setId((int)temp.position);
        }
        return temp.position;
    }
    //public void getAlPerson(final ArrayList<Person> people, final MyListViewAdapter adapter){
    public void getAlPerson(final ArrayList<Person> people, final MyRecyclerViewAdapter adapter){
        //Load data from database
        PersonDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Person> list = DAO.getAllPerson();
                if(list.size()>0){
                    people.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public int delPerson(final Person person){
        final Temp temp = new Temp();
        PersonDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                temp.isComplte = false;
                temp.position = DAO.delPerson(person);
                temp.isComplte = true;
            }
        });
        while (!temp.isComplte){

        }return (int)temp.position;
    }

    public int update (final Person person){
        final Temp temp = new Temp();
        PersonDatabase.dbReadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                temp.isComplte = false;
                temp.position = DAO.updatePerson(person);
                temp.isComplte = true;
            }
        });
        while (!temp.isComplte){

        }return (int)temp.position;
    }
    /*public int updatePerson(Person person) throws ExecutionException, InterruptedException {
        Future<Integer> position =  PersonDatabase.dbReadWriteExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return DAO.updatePerson(person);
            }
        });
        return position.get();
    }

    public int delPerson(Person person) {
        Future<Integer> numRow =  PersonDatabase.dbReadWriteExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return DAO.updatePerson(person);
            }
        });
        try {
            return numRow.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }*/

    /*public long savePerson(Person person) throws ExecutionException, InterruptedException {
        Future<Integer> future1 = PersonDatabase.dbReadWriteExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return (int) DAO.savePerson(person);

            }
        });
        if (future1.get() != -1) {
            person.setId(future1.get());
        }
        return future1.get();
    }*/
}
