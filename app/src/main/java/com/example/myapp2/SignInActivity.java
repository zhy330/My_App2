package com.example.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLData;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText user_names;
    private EditText passwords;
    private EditText use_ids;
    private Button sign_ins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        sign_ins.setOnClickListener(this);
    }
    private void init(){
        use_ids = findViewById(R.id.user_ids);
        user_names = findViewById(R.id.user_names);
        passwords = findViewById(R.id.passwords);
        sign_ins = findViewById(R.id.sign_ins);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_ins:
                dataBaseQuery();
                break;
        }

    }

    private void dataBaseQuery() {
            MyDatabaseHelper databaseHelper = new MyDatabaseHelper(SignInActivity.this);
            SQLiteDatabase database = databaseHelper.getReadableDatabase();

            String user_id = use_ids.getText().toString();

            Cursor cursor = database.query("user",new String[]{"user_id"},"user_id=?",new String[]{user_id},null,null,null);

            if(cursor.moveToFirst()){
                Toast.makeText(SignInActivity.this,"该号码已注册",Toast.LENGTH_LONG).show();cursor.close();database.close();
            }else {
                dataBaseInsert();
            }

    }

    private void dataBaseInsert() {
            MyDatabaseHelper databaseHelper = new MyDatabaseHelper(SignInActivity.this);
            SQLiteDatabase database = databaseHelper.getReadableDatabase();

            String user_ids = use_ids.getText().toString();
            String user_name = user_names.getText().toString();
            String password = passwords.getText().toString();

            if(user_name.equals("")&&password.equals("")){Toast.makeText(SignInActivity.this,"信息不能为空", Toast.LENGTH_LONG).show();}
            else if(user_name.equals("")){Toast.makeText(SignInActivity.this,"用户名不能为空", Toast.LENGTH_LONG).show();}
            else if(password.equals("")){Toast.makeText(SignInActivity.this,"密码不能为空", Toast.LENGTH_LONG).show();}
            else {

            database.execSQL("insert into user(user_id,user_name,password,address,introduction) values('" + user_ids + "','" + user_name + "','" + password + "','无','无');");

            database.close();
            Toast.makeText(SignInActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(it);

            }
    }
}
