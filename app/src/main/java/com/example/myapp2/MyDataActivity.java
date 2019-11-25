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

public class MyDataActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private EditText my_style;private Button btn2;
    private EditText my_code;private Button btn3;
    private EditText my_address2;private Button btn4;
    private int a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);
        init();
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }
    private void init(){
        btn1 = findViewById(R.id.btn1);
        my_style = findViewById(R.id.my_style);btn2 = findViewById(R.id.btn2);
        my_code = findViewById(R.id.my_code);btn3 = findViewById(R.id.btn3);
        my_address2 = findViewById(R.id.my_address2);btn4 = findViewById(R.id.btn4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                Intent itn4 = getIntent();
                String id4 = itn4.getStringExtra("id");
                Intent intent = new Intent(MyDataActivity.this,CameraAlbumTest.class);
                intent.putExtra("user_id",id4);
                startActivity(intent);
                break;
            case R.id.btn2:
                a=2;
                dataBaseUpdate();
                break;
            case R.id.btn3:
                a=3;
                dataBaseUpdate();
                break;
            case R.id.btn4:
                a=4;
                dataBaseUpdate();
                break;
        }
    }

    private void dataBaseUpdate(){
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Intent itn4 = getIntent();
        String id4 = itn4.getStringExtra("id");
        if (a==2) {
            String style = my_style.getText().toString();

            if (style.equals("")) {
                Toast.makeText(MyDataActivity.this, "简介不能为空", Toast.LENGTH_LONG).show();
            } else {
                ContentValues values = new ContentValues();
                values.put("introduction", style);
                database.update("user", values, "user_id=?", new String[]{id4});
                Toast.makeText(MyDataActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                database.close();
            }
        }else if(a==3){
            String psd = my_code.getText().toString();

            if(psd.equals("")) {
                Toast.makeText(MyDataActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
            }else {
                ContentValues values = new ContentValues();
                values.put("password",psd);
                database.update("user",values,"user_id=?",new String[]{id4});
                Toast.makeText(MyDataActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                database.close();
            }
        }else if(a==4) {
            String address = my_address2.getText().toString();

            if (address.equals("")) {
                Toast.makeText(MyDataActivity.this, "地址不能为空", Toast.LENGTH_LONG).show();
            } else {
                ContentValues values = new ContentValues();
                values.put("address", address);
                database.update("user", values, "user_id=?", new String[]{id4});
                Toast.makeText(MyDataActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                database.close();
            }
        }



    }
}
