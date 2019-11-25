package com.example.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddGoodsActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText add_goods_id;
    private EditText add_goods_name;
    private EditText add_goods_picture;
    private EditText add_goods_num;
    private EditText add_goods_price;
    private Button add_goods_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        init();
        add_goods_start.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void init(){
        add_goods_id = findViewById(R.id.add_goods_id);
        add_goods_name = findViewById(R.id.add_goods_name);
        add_goods_picture = findViewById(R.id.add_goods_picture);
        add_goods_num = findViewById(R.id.add_goods_num);
        add_goods_price = findViewById(R.id.add_goods_price);
        add_goods_start = findViewById(R.id.add_goods_start);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_goods_start:
                dataBaseQuery();
                break;
        }
    }

    private void dataBaseQuery() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(AddGoodsActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String goods_id =add_goods_id .getText().toString();

        Cursor cursor = database.query("goods",new String[]{"goods_id"},"goods_id=?",new String[]{goods_id},null,null,null);

        if(cursor.moveToFirst()){
            Toast.makeText(AddGoodsActivity.this,"商品编号已存在",Toast.LENGTH_LONG).show();cursor.close();database.close();
        }else {
            dataBaseInsert();
        }

    }

    private void dataBaseInsert() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent1 = getIntent();
        String shop_id = intent1.getStringExtra("shop_id");
        String goods_ids = add_goods_id.getText().toString();
        String goods_name = add_goods_name.getText().toString();
        String goods_num = add_goods_num.getText().toString();
        String goods_price = add_goods_price.getText().toString();
        String goods_picture = add_goods_picture.getText().toString();

        if(goods_name.equals("")&&goods_picture.equals("")&&goods_ids.equals("")&&goods_num.equals("")&&goods_price.equals("")){Toast.makeText(AddGoodsActivity.this,"信息不能为空", Toast.LENGTH_LONG).show();}
        else if(goods_name.equals("")){Toast.makeText(AddGoodsActivity.this,"店铺名不能为空", Toast.LENGTH_LONG).show();}
        else if(goods_picture.equals("")){Toast.makeText(AddGoodsActivity.this,"相片不能为空", Toast.LENGTH_LONG).show();}
        else {

            database.execSQL("insert into goods(goods_id,goods_name,goods_picture,goods_num,goods_price,shop_id) values('" + goods_ids + "','" + goods_name + "','" + goods_picture + "','" + goods_num + "','" + goods_price + "','" + shop_id + "');");

            database.close();
            Toast.makeText(AddGoodsActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
        }
    }
}
