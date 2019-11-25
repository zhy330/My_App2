package com.example.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user_id;
    private EditText password;
    private TextView sign_in;
    private Button log_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        sign_in.setOnClickListener(this);
        log_in.setOnClickListener(this);
    }
    private void init(){
        user_id = findViewById(R.id.user_id);
        password = findViewById(R.id.password);
        sign_in = findViewById(R.id.sign_in);
        log_in = findViewById(R.id.log_in);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_in:
                dataBaseQuery();
                break;
            case R.id.sign_in:
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void dataBaseQuery() {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String id = user_id.getText().toString();


        Cursor cursor = database.query("user",new String[]{"user_id","password"},"user_id=?",new String[]{id},null,null,null);

        if(cursor.moveToFirst()){
            String pds = cursor.getString(cursor.getColumnIndex("password"));
            String pw = password.getText().toString();
            if(pds.equals(pw)){
                    Intent intent1 = new Intent(MainActivity.this,DrawerActivity.class);
                    cursor.close();
                    database.close();
                    intent1.putExtra("id",id);
                startActivity(intent1);
            }
            else {
                Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_LONG).show();cursor.close();database.close();
            }
        }else {
            Toast.makeText(MainActivity.this,"未注册用户",Toast.LENGTH_LONG).show();cursor.close();database.close();
        }
    }
}
