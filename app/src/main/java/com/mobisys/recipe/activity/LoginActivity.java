package com.mobisys.recipe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mobisys.recipe.R;
import com.mobisys.recipe.util.Utils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu1 on 10/3/16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ProgressDialog mProgressDialog;
    private EditText editUserName, editPassword;
    private Button btnLogin, btnRegister, btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        /*inputLayoutUserName = (TextInputLayout)findViewById(R.id.usernameWrapper);
        inputLayoutPassword = (TextInputLayout)findViewById(R.id.passwordWrapper);*/
        editUserName = (EditText)findViewById(R.id.editUsername);
        editPassword = (EditText)findViewById(R.id.editPassword);
        btnForgotPassword = (Button)findViewById(R.id.btnForgotPassword);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin){
            if (Utils.isConnected(LoginActivity.this)){
                submitForm();
            }
        }else if (v == btnRegister){
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }else if (v == btnForgotPassword){
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        }
    }

    private void submitForm() {
        if (!validateUsername()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }

        if (Utils.isConnected(LoginActivity.this)){
            login(editUserName.getText().toString(), editPassword.getText().toString());
        }

    }

    private void login(String username, String password) {

        mProgressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.loading), true);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                List<String> list = new ArrayList<String>();
                mProgressDialog.dismiss();
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, OneToOneChatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Utils.showToastMessage(LoginActivity.this, getResources().getString(R.string.user_not_found));
                }
            }
        });
    }

    private boolean validateUsername(){
        String username = editUserName.getText().toString().trim();
        if (username.isEmpty()) {
            Utils.showToastMessage(this,"Enter valid User Name");
            // inputLayoutUserName.setError("Enter valid User Name");
            requestFocus(editUserName);
            return false;
        } else {
            //inputLayoutUserName.setErrorEnabled(false);
        }

        return  true;
    }

    private boolean validatePassword(){
        String username = editPassword.getText().toString().trim();
        if (username.isEmpty()) {
            Utils.showToastMessage(this,"Enter valid Password");
            // inputLayoutPassword.setError("Enter valid Password");
            requestFocus(editPassword);
            return false;
        } else {
            // inputLayoutPassword.setErrorEnabled(false);
        }

        return  true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
