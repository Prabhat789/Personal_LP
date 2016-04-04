package com.mobisys.aspr.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mobisys.aspr.fragments.StepOneRegistration;
import com.mobisys.aspr.util.ApplicationConstant;
import com.pktworld.aspr.R;


/**
 * Created by ubuntu1 on 10/3/16.
 */
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;
    /*private EditText editUserName, editPassword, editConfirmPassword, editMobileNumber, editEmail;
    private Button btnConfirm;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

       /* editUserName = (EditText)findViewById(R.id.editName);
        editPassword = (EditText)findViewById(R.id.editPassword);
        editConfirmPassword = (EditText)findViewById(R.id.editPasswordConfirm);
        editMobileNumber = (EditText)findViewById(R.id.editMobile);
        editEmail = (EditText)findViewById(R.id.editEmail);
        btnConfirm = (Button)findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(this);*/
        if(findViewById(R.id.fragmentContainer) != null)
        {
            if (savedInstanceState != null)
                return;

            StepOneRegistration fragment = new StepOneRegistration();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        }



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

   /* public void SlideToAbove() {
        Animation slide = null;
        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -5.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        rl_footer.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                rl_footer.clearAnimation();

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        rl_footer.getWidth(), rl_footer.getHeight());
                // lp.setMargins(0, 0, 0, 0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                rl_footer.setLayoutParams(lp);

            }

        });

    }*/
    private BroadcastReceiver mgetUserReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int oId = intent.getIntExtra(ApplicationConstant.FLAG, 0);
            Log.d(TAG, "Got message: " + oId);
            isUserExist(oId);

        }
    };
    private boolean isUserExist(int size){
        if (size > 0){
            showMessage(1);
            //Utils.showToastMessage(this, "Already User");
            return true;
        }else {
            showMessage(0);
          //  Utils.showToastMessage(this,"New User");
            return false;
        }
    }
    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mgetUserReceiver);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mgetUserReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mgetUserReceiver,
                new IntentFilter(ApplicationConstant.REGISTRATION_FRAGMENT_ONE));

        super.onResume();
    }

    void showMessage(int id) {
        if (id == 0) {
            snackbar = Snackbar
                    .make(coordinatorLayout, "Verified, Press Next to Continue.", Snackbar.LENGTH_LONG)
                    .setAction("NEXT", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

        }else {

            snackbar = Snackbar
                    .make(coordinatorLayout, "Email Already Taken by another User.", Snackbar.LENGTH_LONG);
                    /*.setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);*/
        }


        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackbar.show();
    }
}
