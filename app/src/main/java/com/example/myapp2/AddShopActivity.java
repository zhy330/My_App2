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

public class AddShopActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText add_shop_id;
    private EditText add_shop_name;
    private EditText add_shop_picture;
    private Button add_shop_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        init();
        add_shop_start.setOnClickListener(this);
    }
    private void init(){
        add_shop_id = findViewById(R.id.add_shop_id);
        add_shop_name = findViewById(R.id.add_shop_name);
        add_shop_picture = findViewById(R.id.add_shop_picture);
        add_shop_start = findViewById(R.id.add_shop_start);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_shop_start:
                dataBaseQuery();
                break;
        }
    }

    private void dataBaseQuery() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(AddShopActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String shop_id =add_shop_id .getText().toString();

        Cursor cursor = database.query("shop",new String[]{"shop_id"},"shop_id=?",new String[]{shop_id},null,null,null);

        if(cursor.moveToFirst()){
            Toast.makeText(AddShopActivity.this,"店铺编号已存在",Toast.LENGTH_LONG).show();cursor.close();database.close();
        }else {
            dataBaseInsert();
        }

    }

    private void dataBaseInsert() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(AddShopActivity.this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String shop_ids = add_shop_id.getText().toString();
        String shop_name = add_shop_name.getText().toString();
        String shop_picture = add_shop_picture.getText().toString();

        if(shop_name.equals("")&&shop_picture.equals("")){Toast.makeText(AddShopActivity.this,"信息不能为空", Toast.LENGTH_LONG).show();}
        else if(shop_name.equals("")){Toast.makeText(AddShopActivity.this,"店铺名不能为空", Toast.LENGTH_LONG).show();}
        else if(shop_picture.equals("")){Toast.makeText(AddShopActivity.this,"相片不能为空", Toast.LENGTH_LONG).show();}
        else {

            database.execSQL("insert into shop(shop_id,shop_name,shop_picture,user_id) values('" + shop_ids + "','" + shop_name + "','" + shop_picture + "','" + user_id + "');");

            database.close();
            Toast.makeText(AddShopActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
    }
    }
}
