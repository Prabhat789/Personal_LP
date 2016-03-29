package com.mobisys.aspr.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pktworld.aspr.R;


/**
 * Created by ubuntu1 on 10/3/16.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText editUserName, editPassword, editConfirmPassword, editMobileNumber, editEmail;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUserName = (EditText)findViewById(R.id.editName);
        editPassword = (EditText)findViewById(R.id.editPassword);
        editConfirmPassword = (EditText)findViewById(R.id.editPasswordConfirm);
        editMobileNumber = (EditText)findViewById(R.id.editMobile);
        editEmail = (EditText)findViewById(R.id.editEmail);
        btnConfirm = (Button)findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
