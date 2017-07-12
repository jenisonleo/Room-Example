package com.example.jenison_3631.roomtest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jenison_3631.roomtest.room.AnimalTable;
import com.example.jenison_3631.roomtest.room.RecyclerAdapter;
import com.example.jenison_3631.roomtest.room.RoomDB;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "roomdatabase";
    private HashMap<String,String> intialdbvalues=new HashMap<>();
    private RoomDB db;
    private LiveData<List<AnimalTable>> animaLivedata;
    private RecyclerAdapter adapter;
    private Random random=new Random();
    Executor deliveryExecutor;
    List<AnimalTable> animalTables;
    long time=0;
    ThreadPoolExecutor threadPoolExecutor= new ThreadPoolExecutor(1,1,0, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Handler handler=new Handler(Looper.getMainLooper());
        deliveryExecutor=new Executor() {
            @Override
            public void execute(@NonNull Runnable command) {
                handler.post(command);
            }
        };

//        intialdbvalues.put("lion","mammal");
//        intialdbvalues.put("tiger","mammal");
//        intialdbvalues.put("tilapia","fish");
//        intialdbvalues.put("frog","amphibian");
//        intialdbvalues.put("snake","reptile");
//        intialdbvalues.put("cod","fish");
//        intialdbvalues.put("monkey","mammal");
//        intialdbvalues.put("hen","mammal");
//        intialdbvalues.put("lizard","reptile");
//        intialdbvalues.put("pigeon","bird");
        setContentView(R.layout.activity_main);
        dbTask.execute(getApplicationContext());
    }
    public void roomaddData(View view){
        Log.e("roomclick"," ");
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                time=System.currentTimeMillis();
                db.beginTransaction();
                for (int i=0;i<50;i++) {
                    int rand=random.nextInt(1000);
                    AnimalTable animalTable=new AnimalTable();
                    animalTable.setAnimalName(rand);
                    animalTable.setAnimalType1(random.nextInt());
                    animalTable.setAnimalType2(random.nextInt());
                    animalTable.setAnimalType3(random.nextInt());
                    animalTable.setAnimalType4(random.nextInt());
                    animalTable.setAnimalType5(random.nextInt());
                    animalTable.setAnimalType6(random.nextInt());
                    animalTable.setAnimalType7(random.nextInt());
                    animalTable.setAnimalType8(random.nextInt());
                    animalTable.setAnimalType9(random.nextInt());
                    animalTable.setAnimalType10(random.nextInt());
                    db.animalDataAccess().insertAnimal(animalTable);
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
            }
        }.execute();

    }

    private AsyncTask<Context,Void,Void> dbTask=new AsyncTask<Context,Void,Void>(){

        @Override
        protected Void doInBackground(Context... params) {
            deleteDatabase(DATABASE_NAME);
            db = Room.databaseBuilder(getApplicationContext(), RoomDB.class, DATABASE_NAME).build();
            db.beginTransaction();
            for (int i=0;i<50000;i++) {
                AnimalTable animalTable=new AnimalTable();
                animalTable.setAnimalName(i);
                animalTable.setAnimalType1(random.nextInt());
                animalTable.setAnimalType2(random.nextInt());
                animalTable.setAnimalType3(random.nextInt());
                animalTable.setAnimalType4(random.nextInt());
                animalTable.setAnimalType5(random.nextInt());
                animalTable.setAnimalType6(random.nextInt());
                animalTable.setAnimalType7(random.nextInt());
                animalTable.setAnimalType8(random.nextInt());
                animalTable.setAnimalType9(random.nextInt());
                animalTable.setAnimalType10(random.nextInt());
                db.animalDataAccess().insertAnimal(animalTable);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            return null;
        }

        @Override
        protected void onPostExecute(Void oid) {
            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.roomrecycelrview);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2, LinearLayoutManager.VERTICAL,false));
            adapter=new RecyclerAdapter();
            recyclerView.setAdapter(adapter);
            animaLivedata=db.animalDataAccess().getallAnimals();
            animaLivedata.observeForever(new Observer<List<AnimalTable>>() {
                @Override
                public void onChanged(@Nullable List<AnimalTable> animalTables) {
                    Log.e("time"," "+(System.currentTimeMillis()-time));
                    Log.e("datagot"," "+animalTables.size()+" "+Thread.currentThread().getName());
                    Toast.makeText(MainActivity.this,"started",Toast.LENGTH_SHORT).show();
                    adapter.addData(animalTables);
//                    adapter.notifyDataSetChanged();
                }
            });
        }
    };
}
