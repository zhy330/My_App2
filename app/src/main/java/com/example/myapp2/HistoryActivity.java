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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerView_history);


        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        dataBaseQuery();
    }

    private void dataBaseQuery() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(HistoryActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query("history",null,null,null,null,null,null);


        if(cursor!=null){
            Intent int1 = getIntent();
            String history_id = int1.getStringExtra("id");
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; cursor.moveToNext(); i++) {
                String history_ids = cursor.getString(cursor.getColumnIndex("history_id"));
                if(history_id.equals(history_ids)) {
                    Map<String, Object> map = new HashMap<>();
                    String id = cursor.getString(cursor.getColumnIndex("goods_id"));
                    String name = cursor.getString(cursor.getColumnIndex("goods_name"));
                    String price = cursor.getString(cursor.getColumnIndex("goods_price"));
                    Log.e("TAG", name );
                    map.put("goods_id", id);
                    map.put("goods_name", name);
                    map.put("goods_price",price);
                    list.add(map);
                }
            }

            recyclerView.setAdapter(new HistoryAdapter(HistoryActivity.this, list));

        }
        cursor.close();
        database.close();
    }

    @Override
    public void onClick(View v) {
        Intent int2 = getIntent();
        String user_id = int2.getStringExtra("id");
        Log.d("id",user_id);
        switch (v.getId()){
            case R.id.btn_history:
                Intent intent = new Intent(HistoryActivity.this,GoodsEnterActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
                break;
        }
    }


}
