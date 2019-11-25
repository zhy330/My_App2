package com.example.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsEnterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView goods_enter_name;
    private TextView goods_enter_price;
    private TextView goods_enter_num;
    private Button btn_goods_buy;
    private Button btn_shop_enter;
    private Integer nums;
    private Integer goods_nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_enter);
        init();
        btn_goods_buy.setOnClickListener(this);
        btn_shop_enter.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String goods_id = intent.getStringExtra("goods_id");
        dataBaseQuery2();
    }

    private void dataBaseQuery2() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(GoodsEnterActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent = getIntent();
        String goods_id = intent.getStringExtra("goods_id");

        Cursor cursor = database.query("goods",new String[]{"goods_id","goods_picture","goods_name","goods_num","goods_price"},"goods_id=?",new String[]{goods_id},null,null,null);

        if(cursor.moveToFirst()){
            String goods_name = cursor.getString(cursor.getColumnIndex("goods_name"));
            String goods_num = cursor.getString(cursor.getColumnIndex("goods_num"));
            String goods_price = cursor.getString(cursor.getColumnIndex("goods_price"));

            if (goods_name==null||goods_name.equals("")) {
                goods_enter_name.setText("无");
            }else {
                goods_enter_name.setText(goods_name);
            }
            if(goods_num==null||goods_num.equals("")){
                goods_enter_num.setText("无");
            }else {
                goods_enter_num.setText(goods_num);
            }
            if(goods_price==null||goods_price.equals("")){
                goods_enter_price.setText("无");
            }else {
                goods_enter_price.setText(goods_price);
            }
            cursor.close();database.close();
        }
    }

    private void init(){
        goods_enter_name = findViewById(R.id.goods_enter_name);
        goods_enter_num = findViewById(R.id.goods_enter_num);
        goods_enter_price = findViewById(R.id.goods_enter_price);
        btn_goods_buy = findViewById(R.id.btn_goods_enter_buy);
        btn_shop_enter = findViewById(R.id.btn_goods_enter_shop_enter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        String goods_id = intent.getStringExtra("goods_id");
        switch (v.getId()){
            case R.id.btn_goods_enter_buy:
                dataBaseQuery();
                break;
            case R.id.btn_goods_enter_shop_enter:
                Intent intent1 = new Intent(GoodsEnterActivity.this,ShopEnterActivity.class);
                intent1.putExtra("goods_id",goods_id);
                startActivity(intent1);
                break;
        }

    }

    private void dataBaseQuery() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(GoodsEnterActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent = getIntent();
        String goods_id = intent.getStringExtra("goods_id");

        Cursor cursor = database.query("goods",new String[]{"goods_id","goods_num"},"goods_id=?",new String[]{goods_id},null,null,null);

        if(cursor.moveToFirst()){
            String num = cursor.getString(cursor.getColumnIndex("goods_num"));
            String goods_num = goods_enter_num.getText().toString();
            nums = Integer.parseInt(num);
            goods_nums = Integer.parseInt(goods_num);

            if(nums>=goods_nums){
                cursor.close();database.close();
                dataBaseUpdate();
            }else {
                Toast.makeText(GoodsEnterActivity.this,"库存不够",Toast.LENGTH_LONG).show();cursor.close();database.close();
            }
        }
    }

    private void dataBaseUpdate() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(GoodsEnterActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent = getIntent();
        String goods_id = intent.getStringExtra("goods_id");
        Integer goods_num = nums-goods_nums;

        ContentValues values = new ContentValues();
        values.put("goods_num", goods_num);
        database.update("goods", values, "goods_id=?", new String[]{goods_id});

        Toast.makeText(GoodsEnterActivity.this, "购买成功", Toast.LENGTH_LONG).show();
        database.close();
        dataBaseInsert();
    }

    private void dataBaseInsert() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(GoodsEnterActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent = getIntent();
        String goods_id = intent.getStringExtra("goods_id");
        String user_id = intent.getStringExtra("user_id");
        String goods_name = goods_enter_name.getText().toString();
        String goods_price = goods_enter_price.getText().toString();

            database.execSQL("insert into history(history_id,goods_name,goods_price,goods_id) values('" + user_id + "','" + goods_name + "','" + goods_price + "','" + goods_id + "');");

            database.close();
        }

}
