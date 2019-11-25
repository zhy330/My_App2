package com.example.myapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyGoodsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button add_goods;
    private RecyclerView recyclerView_goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goods);
        recyclerView_goods = findViewById(R.id.recyclerView_goods);

        recyclerView_goods.setLayoutManager(new LinearLayoutManager(MyGoodsActivity.this));

        init();
        add_goods.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataBaseQuery();
    }

    private void dataBaseQuery() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(MyGoodsActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query("goods",null,null,null,null,null,null);

        if(cursor!=null){
            Intent intent = getIntent();
            String shop_id = intent.getStringExtra("shop_id");
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; cursor.moveToNext(); i++) {
                String shop_ids = cursor.getString(cursor.getColumnIndex("shop_id"));
                if(shop_id.equals(shop_ids)) {
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

            recyclerView_goods.setAdapter(new MyGoodsAdapter(MyGoodsActivity.this, list));

        }
        cursor.close();
        database.close();
    }

    private void init(){
        add_goods = findViewById(R.id.add_goods);
    }

    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        String shop_id = intent.getStringExtra("shop_id");
        switch (v.getId()){
            case R.id.add_goods:
                Intent intent1 = new Intent(MyGoodsActivity.this,AddGoodsActivity.class);
                intent1.putExtra("shop_id",shop_id);
                startActivity(intent1);
                break;
        }
    }


}
