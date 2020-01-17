package com.example.ts.neteasytest.LoginThings;

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

public class RegistActivity extends AppCompatActivity {
    private EditText mEditTextUserName,mEditTextUserPassword,mEditTextUserRePassword,mEditTextEmail,mEditTextPhoneNumber;
    private Button mBtnReturn, mBtnLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initClik();
        initView();
    }
    public void initClik(){
        mBtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistActivity.this.finish();
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPerson userPerson = new UserPerson();
                if (TextUtils.isEmpty(mEditTextUserName.getText())){
                    Toast.makeText(RegistActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mEditTextUserPassword.getText().toString().equals(mEditTextUserRePassword.getText().toString())){
                    Toast.makeText(RegistActivity.this,"两次输入密码不一样！",Toast.LENGTH_SHORT).show();
                }
                if (!TextUtils.isEmpty(mEditTextEmail.getText())){
                    userPerson.setMail(mEditTextEmail.getText().toString());
                }
                if (!TextUtils.isEmpty(mEditTextPhoneNumber.getText())){
                    userPerson.setPhoneNumber(mEditTextPhoneNumber.getText().toString());
                }
                userPerson.setName(mEditTextUserName.getText().toString());
                userPerson.setPassword(mEditTextUserPassword.getText().toString());
                //登录待完善
            }
        });
    }
    public void initView(){
        mEditTextUserName = (EditText) findViewById(R.id.id_login_etxt_username);
        mEditTextPhoneNumber = (EditText) findViewById(R.id.id_login_etxt_phoneNumber);
        mEditTextEmail = (EditText) findViewById(R.id.id_login_etxt_email);
        mEditTextUserRePassword = (EditText) findViewById(R.id.id_login_etxt_passWord_repeat);
        mEditTextUserPassword = (EditText) findViewById(R.id.id_login_etxt_passWord1);
        mBtnReturn = (Button) findViewById(R.id.id_login_btn_return);
        mBtnLogin = (Button) findViewById(R.id.id_login_btn_confirmLogin);


    }
}
