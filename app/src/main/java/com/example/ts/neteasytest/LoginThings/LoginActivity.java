package com.example.ts.neteasytest.LoginThings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ts.neteasytest.R;

/**
 * Created by ts on 20-1-14.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent = null;
    private EditText mEditTextUserName,mEditTextPassword;
    private Button mBtnlogin,mBtnForget,mBtnNewUser,mBtnReturn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);
        initView();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_main_new_user:{
                Intent intentNewUser = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intentNewUser);
                break;
            }
            case R.id.id_main_btn_signin:{
                LoginIn();
                break;
            }
        }

    }
    private void initView(){
        mEditTextPassword = (EditText) findViewById(R.id.id_main_edittext_password);
        mEditTextUserName = (EditText) findViewById(R.id.id_login_etxt_username);
        mBtnlogin = (Button) findViewById(R.id.id_main_btn_signin);
        mBtnForget = (Button) findViewById(R.id.id_main_btn_forget_password);
        mBtnReturn = (Button) findViewById(R.id.id_login_btn_return_main);
        mBtnNewUser = (Button) findViewById(R.id.id_main_new_user);
        mBtnNewUser.setOnClickListener(this);
        mBtnlogin.setOnClickListener(this);
        mBtnForget.setOnClickListener(this);
        mBtnNewUser.setOnClickListener(this);
        mBtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
    }
    private void LoginIn(){
        if (TextUtils.isEmpty(mEditTextUserName.getText().toString())){
            ToastForLogin("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(mEditTextPassword.getText().toString())){
            ToastForLogin("密码不能为空");
            return;
        }
        final  UserPerson userPerson = new UserPerson();
        userPerson.setName(mEditTextUserName.getText().toString());
        userPerson.setPassword(mEditTextPassword.getText().toString());
        //暂时这样
    }
    public void ToastForLogin(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_SHORT).show();
    }

}
