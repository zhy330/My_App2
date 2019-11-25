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

public class MyShopDataActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText shop_picture_edit;
    private EditText shop_name_edit;
    private Button btn_shop_picture_edit;
    private Button btn_shop_name_edit;
    private Button btn_delete_shop;
    private int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop_data);
        init();
        btn_shop_picture_edit.setOnClickListener(this);
        btn_shop_name_edit.setOnClickListener(this);
        btn_delete_shop.setOnClickListener(this);
    }
    private void init(){
        shop_name_edit = findViewById(R.id.shop_name_edit);
        shop_picture_edit = findViewById(R.id.shop_picture_edit);
        btn_shop_name_edit = findViewById(R.id.btn_shop_name_edit);
        btn_shop_picture_edit = findViewById(R.id.btn_shop_picture_edit);
        btn_delete_shop = findViewById(R.id.btn_delete_shop);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_shop_name_edit:
                a=1;
                dataBaseUpdate();
                break;
            case R.id.btn_shop_picture_edit:
                a=2;
                dataBaseUpdate();
                break;
            case R.id.btn_delete_shop:
                dataBaseDelete();
                break;
        }
    }

    private void dataBaseDelete() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent = getIntent();
        String shop_id = intent.getStringExtra("shop_id");

        database.delete("shop", "shop_id=?", new String[]{shop_id});

        database.close();
        finish();
    }

    private void dataBaseUpdate() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent = getIntent();
        String shop_id = intent.getStringExtra("shop_id");

        if(a==1) {
            String shop_name = shop_name_edit.getText().toString();

            if (shop_name.equals("")) {
                Toast.makeText(MyShopDataActivity.this, "店铺名不能为空", Toast.LENGTH_LONG).show();
            } else {
                ContentValues values = new ContentValues();
                values.put("shop_name", shop_name);
                database.update("shop", values, "shop_id=?", new String[]{shop_id});
                Toast.makeText(MyShopDataActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                database.close();
            }
        }else if(a==2) {
            String shop_picture = shop_picture_edit.getText().toString();

            if (shop_picture.equals("")) {
                Toast.makeText(MyShopDataActivity.this, "图片不能为空", Toast.LENGTH_LONG).show();
            } else {
                ContentValues values = new ContentValues();
                values.put("shop_picture", shop_picture);
                database.update("shop", values, "shop_id=?", new String[]{shop_id});
                Toast.makeText(MyShopDataActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                database.close();
            }
        }

    }
}
