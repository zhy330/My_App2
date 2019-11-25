package com.example.myapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyShopActivity extends AppCompatActivity implements View.OnClickListener {
    private Button add_shop;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        recyclerView = findViewById(R.id.recyclerView_shop);


        recyclerView.setLayoutManager(new LinearLayoutManager(MyShopActivity.this));

        init();
        add_shop.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataBaseQuery();
    }

    private void dataBaseQuery() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(MyShopActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query("shop",null,null,null,null,null,null);


        if(cursor!=null){
            Intent int2 = getIntent();
            String user_id = int2.getStringExtra("id");
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; cursor.moveToNext(); i++) {
                String user_ids = cursor.getString(cursor.getColumnIndex("user_id"));
                if(user_id.equals(user_ids)) {
                    Map<String, Object> map = new HashMap<>();
                    String id = cursor.getString(cursor.getColumnIndex("shop_id"));
                    String name = cursor.getString(cursor.getColumnIndex("shop_name"));
                    String picture = cursor.getString(cursor.getColumnIndex("shop_picture"));
                    Log.e("TAG", name + picture);
                    map.put("shop_id", id);
                    map.put("name", name);
                    map.put("picture", picture);
                    list.add(map);
                }
            }

            recyclerView.setAdapter(new MyShopAdapter(MyShopActivity.this, list));

        }
        cursor.close();
        database.close();
    }

    private void init(){
        add_shop = findViewById(R.id.add_shop);
    }

    @Override
    public void onClick(View v) {
        Intent int2 = getIntent();
        String user_id = int2.getStringExtra("id");
        Log.d("id",user_id);
        switch (v.getId()){
            case R.id.add_shop:
                Intent intent = new Intent(MyShopActivity.this,AddShopActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
                break;
        }
    }


}
