package com.mobisys.aspr.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mobisys.aspr.util.Utils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.pktworld.aspr.R;

/**
 * Created by ubuntu1 on 10/3/16.
 */
public class ForgotPasswordActivity extends Activity implements View.OnClickListener{

    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();
    private ProgressDialog mProgressDialog;
    private EditText editEmail;
    private Button btnSubmit;
    private LinearLayout llBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);

        editEmail = (EditText)findViewById(R.id.editEmail);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        llBottom = (LinearLayout)findViewById(R.id.llBottom);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSubmit){
            if (Utils.isConnected(ForgotPasswordActivity.this)){
                if (isEmailValid(editEmail.getText().toString())){
                    sendResetEmail(editEmail.getText().toString());
                }else {
                    Utils.showToastMessage(this,"Invalid Email Id");
                }
            }
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    void sendResetEmail(String email){
        mProgressDialog = ProgressDialog.show(this, "",getResources().getString(R.string.loading), true);
        ParseUser.requestPasswordResetInBackground(email,
                new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        mProgressDialog.dismiss();
                        if (e == null) {
                            Log.v(TAG, "An email was successfully sent with reset instructions");
                            llBottom.setVisibility(View.VISIBLE);
                        } else {
                            Utils.showToastMessage(ForgotPasswordActivity.this,"Something went wrong, Invalid Email.");
                            Log.v(TAG, "Something went wrong. Look at the ParseException to see what's up.");
                        }
                    }
                });
    }
}
