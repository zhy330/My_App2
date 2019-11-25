package com.example.myapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView image;
    private TextView my_address;
    private TextView my_introduction;
    private TextView my_data;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(DrawerActivity.this));
            init();
            navigationView.setNavigationItemSelectedListener(this);
        }

    @Override
    protected void onStart() {
        super.onStart();
        dataBaseQuery();
        dataBaseQuery2();
    }

    private void dataBaseQuery2() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(DrawerActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent1 = getIntent();
        String id = intent1.getStringExtra("id");

        Cursor cursor = database.query("user",new String[]{"user_id","photo","introduction","address"},"user_id=?",new String[]{id},null,null,null);

        if(cursor.moveToFirst()){
            String introduction = cursor.getString(cursor.getColumnIndex("introduction"));
            String address = cursor.getString(cursor.getColumnIndex("address"));

            if (introduction==null||introduction.equals("")) {
                my_introduction.setText("无");
            }else {
                my_introduction.setText(introduction);
            }
            if(address==null||address.equals("")){
                my_address.setText("无");
            }else {
                my_address.setText(address);
            }
            cursor.close();database.close();

        }
    }

    private void dataBaseQuery() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(DrawerActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query("goods",null,null,null,null,null,null);

        Intent intent1 = getIntent();
        String id = intent1.getStringExtra("id");

        if(cursor!=null){
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; cursor.moveToNext(); i++) {
                Map<String, Object> map = new HashMap<>();
                String name = cursor.getString(cursor.getColumnIndex("goods_name"));
                String price = cursor.getString(cursor.getColumnIndex("goods_price"));
                String goods_id = cursor.getString(cursor.getColumnIndex("goods_id"));
                map.put("name", name);
                map.put("price","￥"+price);
                map.put("goods_id",goods_id);
                map.put("user_id",id);
                list.add(map);
            }

            recyclerView.setAdapter(new MyAdapter(DrawerActivity.this, list));

        }
        cursor.close();
        database.close();
    }

    private void init () {
            drawer = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            my_data = findViewById(R.id.my_data);

            View view = navigationView.inflateHeaderView(R.layout.nav_head);
            image = view.findViewById(R.id.image);
            my_address = view.findViewById(R.id.my_address);
            my_introduction = view.findViewById(R.id.my_introduction);
        }

        @Override
        public void onClick (View v){
            Intent intent1 = getIntent();
            String id = intent1.getStringExtra("id");
            switch (v.getId()) {
                case R.id.image:

                    break;
                case R.id.my_address:

                    break;
                case R.id.my_introduction:

                    break;
            }
        }

        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){
            Intent intent1 = getIntent();
            String id = intent1.getStringExtra("id");
            switch (menuItem.getItemId()) {
                case R.id.nav_history:
                    Intent int1 = new Intent(DrawerActivity.this, HistoryActivity.class);
                    int1.putExtra("id",id);
                    startActivity(int1);
                    break;
                case R.id.nav_shop:
                    Intent int2 = new Intent(DrawerActivity.this, MyShopActivity.class);
                    int2.putExtra("id", id);
                    startActivity(int2);
                    break;
                case R.id.nav_shopping_cart:
                    Intent int3 = new Intent(DrawerActivity.this, MyShoppingCartActivity.class);
                    startActivity(int3);
                    break;
                case R.id.my_data:
                    Intent int4 = new Intent(DrawerActivity.this, MyDataActivity.class);
                    int4.putExtra("id", id);
                    startActivity(int4);
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }


