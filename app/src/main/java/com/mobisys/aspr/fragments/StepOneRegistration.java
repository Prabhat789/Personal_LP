package com.mobisys.aspr.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mobisys.aspr.util.ApplicationConstant;
import com.mobisys.aspr.util.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.pktworld.aspr.R;

import java.util.List;

/**
 * Created by ubuntu1 on 30/3/16.
 */
public class StepOneRegistration extends Fragment implements View.OnClickListener{

    private static final String TAG = StepOneRegistration.class.getSimpleName();
    private Button btnNext;
    private EditText editEmail, editUserName;
    private ProgressDialog mProgressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reg1, container, false);

        editEmail = (EditText)rootView.findViewById(R.id.editEmailReg);
        editUserName = (EditText)rootView.findViewById(R.id.editUsernameReg);
        btnNext = (Button)rootView.findViewById(R.id.btnNextReg);

        btnNext.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        if (v == btnNext){
            verify();
        }
    }

    private void verify() {
        if (!validateEmail()) {
            return;
        }
        if (!validateUsername()) {
            return;
        }

        if (Utils.isConnected(getActivity())) {
            checkEmail(editEmail.getText().toString());
        }

    }
    private boolean validateUsername(){
        String username = editUserName.getText().toString().trim();
        if (username.isEmpty()) {
            Utils.showToastMessage(getActivity(),"Enter valid User Name");
            requestFocus(editUserName);
            return false;
        }

        return  true;
    }

    private boolean validateEmail(){
        String username = editEmail.getText().toString().trim();
        if (! isEmailValid(editEmail.getText().toString().trim())) {
            Utils.showToastMessage(getActivity(),"Enter valid Email");
            requestFocus(editEmail);
            return false;
        }

        return  true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void checkEmail(String email) {
        mProgressDialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.validating_email), true);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContains("email", email);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> messages, ParseException e) {
                if (e == null) {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    sendMessage(messages.size());
                    Log.e("TAG", "" + messages.size());

                }
            }
        });

    }

    private void sendMessage(int size) {
        Log.d(TAG, "Broadcasting message");
        Intent intent = new Intent(ApplicationConstant.REGISTRATION_FRAGMENT_ONE);
        intent.putExtra(ApplicationConstant.FLAG, size);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

}
