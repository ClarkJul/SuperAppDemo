package com.android.clark.superappdemo.broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.clark.superappdemo.R;

public class LoginActivity extends BaseActivity {

    private Button loginButton;
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.et_user_name);
        password = findViewById(R.id.et_password);

        loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("------->",userName.getText().toString()+"/"+password.getText().toString());
                if (userName.getText().toString().equals("")&&password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"请输入用户名和密码！",Toast.LENGTH_SHORT).show();
                }else if (userName.getText().toString().equals("")&&!password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                }else if (password.getText().toString().equals("")&&!userName.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                }else if (!userName.getText().toString().equals("")&&!password.getText().toString().equals("")){
                    if (!userName.getText().toString().equals("admin")||!password.getText().toString().equals("123456")){
                        Toast.makeText(LoginActivity.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent=new Intent(LoginActivity.this,BroadcastTestActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

}
