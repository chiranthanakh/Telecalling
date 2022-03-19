package com.example.telecalling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn;
    SqlDB sqlDB = new SqlDB(this);
    private RecyclerView recyclerView;
    private List<Info> teleList = new ArrayList<>();
    private ArrayList<Modelclass> connectedlist = new ArrayList<>();
    private ArrayList<Modelclass>  notconnectedlist= new ArrayList<>();
    private ArrayList<Modelclass> callbacklist = new ArrayList<>();
    private ArrayList<Modelclass> notinterstedlist = new ArrayList<>();
    ImageView filterimage;
    ArrayList<Modelclass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initilize();

    }

    private void initilize() {

        btn = findViewById(R.id.add_btn);
        filterimage = findViewById(R.id.iv_filter);

        filterimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UploadActivity.class);
                startActivity(intent);
            }
        });

        Cursor cursor = sqlDB.getAllData();
        list = new ArrayList<Modelclass>();
        if(cursor.getCount()==0){

        }else {
            while (cursor.moveToNext()) {
            list.add(new Modelclass(cursor.getString(0),cursor.getString(1),cursor.getString(2)));

            if(cursor.getString(2).equals("1")){
                connectedlist.add(new Modelclass(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }else if(cursor.getString(2).equals("2")){
                notconnectedlist.add(new Modelclass(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }else if(cursor.getString(2).equals("3")){
                notinterstedlist.add(new Modelclass(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }else if(cursor.getString(2).equals("4")){
                callbacklist.add(new Modelclass(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }

            }
        }

        ListAdapter numbersArrayAdapter = new ListAdapter(this, list);
        // create the instance of the ListView to set the numbersViewAdapter
        ListView individualindentlist = findViewById(R.id.lv_individual_indent_status);
        // set the numbersViewAdapter for ListView
        individualindentlist.setAdapter(numbersArrayAdapter);

    }

    private void filter() {

        PopupMenu popupMenu = new PopupMenu(MainActivity.this, filterimage);

        popupMenu.getMenuInflater().inflate(R.menu.filteritems, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked

                if(menuItem.getTitle().equals("connected")){
                    sqldata();
                    listadapter(connectedlist);


                }else if(menuItem.getTitle().equals("notconnected")){
                    sqldata();
                    listadapter(notconnectedlist);


                }else if(menuItem.getTitle().equals("notintersted")){
                    sqldata();
                    listadapter(notinterstedlist);

                }else if(menuItem.getTitle().equals("callback")){
                    sqldata();
                    listadapter(callbacklist);


                }else if(menuItem.getTitle().equals("All")){
                    sqldata();
                    listadapter(list);

                }

                //Toast.makeText(IndividualIndentListActivity.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();

    }

    private void listadapter(ArrayList<Modelclass> list1) {
        ListAdapter numbersArrayAdapter = new ListAdapter(this, list1);
        // create the instance of the ListView to set the numbersViewAdapter
        ListView individualindentlist = findViewById(R.id.lv_individual_indent_status);
        // set the numbersViewAdapter for ListView
        individualindentlist.setAdapter(numbersArrayAdapter);

    }

    private void sqldata(){
        Cursor cursor = sqlDB.getAllData();
       list.clear();
       connectedlist.clear();
       notconnectedlist.clear();
       notinterstedlist.clear();
       callbacklist.clear();
        if(cursor.getCount()==0){

        }else {
            while (cursor.moveToNext()) {
                list.add(new Modelclass(cursor.getString(0),cursor.getString(1),cursor.getString(2)));

                if(cursor.getString(2).equals("1")){
                    connectedlist.add(new Modelclass(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
                }else if(cursor.getString(2).equals("2")){
                    notconnectedlist.add(new Modelclass(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
                }else if(cursor.getString(2).equals("3")){
                    notinterstedlist.add(new Modelclass(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
                }else if(cursor.getString(2).equals("4")){
                    callbacklist.add(new Modelclass(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
                }

            }
        }
    }

}