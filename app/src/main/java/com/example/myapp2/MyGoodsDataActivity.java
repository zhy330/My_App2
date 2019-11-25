package com.example.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyGoodsDataActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText goods_picture_edit;
    private EditText goods_name_edit;
    private EditText goods_num_edit;
    private EditText goods_price_edit;
    private Button btn_goods_picture_edit;
    private Button btn_goods_name_edit;
    private Button btn_goods_num_edit;
    private Button btn_goods_price_edit;
    private Button btn_goods_delete;
    private int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goods_data);
        init();
        btn_goods_picture_edit.setOnClickListener(this);
        btn_goods_name_edit.setOnClickListener(this);
        btn_goods_num_edit.setOnClickListener(this);
        btn_goods_price_edit.setOnClickListener(this);
        btn_goods_delete.setOnClickListener(this);
    }
    private void init(){
        goods_name_edit = findViewById(R.id.goods_name_edit);
        goods_picture_edit = findViewById(R.id.goods_picture_edit);
        goods_num_edit = findViewById(R.id.goods_num_edit);
        goods_price_edit = findViewById(R.id.goods_price_edit);
        btn_goods_name_edit = findViewById(R.id.btn_goods_name_edit);
        btn_goods_picture_edit = findViewById(R.id.btn_goods_picture_edit);
        btn_goods_num_edit = findViewById(R.id.btn_goods_num_edit);
        btn_goods_price_edit = findViewById(R.id.btn_goods_price_edit);
        btn_goods_delete = findViewById(R.id.btn_delete_goods);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_goods_name_edit:
                a=1;
                dataBaseUpdate();
                break;
            case R.id.btn_goods_picture_edit:
                a=2;
                dataBaseUpdate();
                break;
            case R.id.btn_goods_num_edit:
                a=3;
                dataBaseUpdate();
                break;
            case R.id.btn_goods_price_edit:
                a=4;
                dataBaseUpdate();
                break;
            case R.id.btn_delete_goods:
                dataBaseDelete();
                break;
        }
    }

    private void dataBaseDelete() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent = getIntent();
        String goods_id = intent.getStringExtra("goods_id");

        database.delete("goods", "goods_id=?", new String[]{goods_id});

        database.close();
        finish();
    }

    private void dataBaseUpdate() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent = getIntent();
        String goods_id = intent.getStringExtra("goods_id");

        if(a==1) {
            String goods_name = goods_name_edit.getText().toString();

            if (goods_name.equals("")) {
                Toast.makeText(MyGoodsDataActivity.this, "商品名不能为空", Toast.LENGTH_LONG).show();
            } else {
                ContentValues values = new ContentValues();
                values.put("goods_name", goods_name);
                database.update("goods", values, "goods_id=?", new String[]{goods_id});
                Toast.makeText(MyGoodsDataActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                database.close();
            }
        }else if(a==2) {
            String goods_picture = goods_picture_edit.getText().toString();

            if (goods_picture.equals("")) {
                Toast.makeText(MyGoodsDataActivity.this, "图片不能为空", Toast.LENGTH_LONG).show();
            } else {
                ContentValues values = new ContentValues();
                values.put("goods_picture", goods_picture);
                database.update("goods", values, "goods_id=?", new String[]{goods_id});
                Toast.makeText(MyGoodsDataActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                database.close();
            }
        }else if(a==3) {
            String goods_num = goods_num_edit.getText().toString();

            if (goods_num.equals("")) {
                Toast.makeText(MyGoodsDataActivity.this, "图片不能为空", Toast.LENGTH_LONG).show();
            } else {
                ContentValues values = new ContentValues();
                values.put("goods_picture", goods_num);
                database.update("goods", values, "goods_id=?", new String[]{goods_id});
                Toast.makeText(MyGoodsDataActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                database.close();
            }
        }else if(a==4) {
            String goods_price = goods_price_edit.getText().toString();

            if (goods_price.equals("")) {
                Toast.makeText(MyGoodsDataActivity.this, "图片不能为空", Toast.LENGTH_LONG).show();
            } else {
                ContentValues values = new ContentValues();
                values.put("goods_price", goods_price);
                database.update("goods", values, "goods_id=?", new String[]{goods_id});
                Toast.makeText(MyGoodsDataActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                database.close();
            }
        }

    }
}
