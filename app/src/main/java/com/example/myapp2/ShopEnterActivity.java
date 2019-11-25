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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopEnterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView shop_enter_name;
    private TextView shop_enter_user;
    private ImageView shop_enter_picture;
    private RecyclerView recyclerView_shop_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_enter);
        recyclerView_shop_enter = findViewById(R.id.recyclerView_shop_enter);
        recyclerView_shop_enter.setLayoutManager(new LinearLayoutManager(ShopEnterActivity.this));

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataBaseQuery();
        dataBaseQuery2();

    }

    private void dataBaseQuery2() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(ShopEnterActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent1 = getIntent();
        String goods_id = intent1.getStringExtra("goods_id");

        Cursor cursor = database.query("goods",new String[]{"shop_id"},"goods_id=?",new String[]{goods_id},null,null,null);
        if(cursor.moveToFirst()) {
            String shop_id = cursor.getString(cursor.getColumnIndex("shop_id")); cursor.close();

            if (shop_id == null || shop_id.equals("")) {
                shop_enter_name.setText("无");
            } else {
                Cursor cursors = database.query("shop", new String[]{"shop_name"}, "shop_id=?", new String[]{shop_id}, null, null, null);

                if (cursors.moveToFirst()) {
                    String shop_name = cursors.getString(cursors.getColumnIndex("shop_name"));

                    if (shop_name == null || shop_name.equals("")) {
                        shop_enter_name.setText("无");
                    } else {
                        shop_enter_name.setText(shop_name);
                    }

                    cursors.close();
                    database.close();
                }
            }
        }
    }

    private void dataBaseQuery() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(ShopEnterActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query("goods",null,null,null,null,null,null);

        Intent intent1 = getIntent();
        String good_id = intent1.getStringExtra("goods_id");
        if(cursor.moveToFirst()){
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; cursor.moveToNext(); i++) {
                String goodss_ids = cursor.getString(cursor.getColumnIndex("goods_id"));
                if(good_id.equals(goodss_ids)) {
                    Map<String, Object> map = new HashMap<>();
                    String name = cursor.getString(cursor.getColumnIndex("goods_name"));
                    String picture = cursor.getString(cursor.getColumnIndex("goods_picture"));
                    String price = cursor.getString(cursor.getColumnIndex("goods_price"));
                    String num = cursor.getString(cursor.getColumnIndex("goods_num"));
                    String goods_id = cursor.getString(cursor.getColumnIndex("goods_id"));
                    Log.e("TAG", name + picture);
                    map.put("name", name);
                    map.put("price", price);
                    map.put("picture", picture);
                    map.put("num", num);
                    map.put("goods_id", goods_id);
                    list.add(map);
                }
            }

            recyclerView_shop_enter.setAdapter(new ShopEnterAdapter(ShopEnterActivity.this, list));

        }
        cursor.close();
        database.close();
    }

    private void init(){
        shop_enter_picture = findViewById(R.id.shop_enter_picture);
        shop_enter_name = findViewById(R.id.shop_enter_name);
        shop_enter_user = findViewById(R.id.shop_enter_user);
    }

    @Override
    public void onClick(View v) {

    }
}
