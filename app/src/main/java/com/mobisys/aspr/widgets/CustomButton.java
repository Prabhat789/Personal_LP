package com.mobisys.aspr.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.mobisys.aspr.util.ApplicationConstant;

/**
 * Created by ubuntu1 on 23/2/16.
 */
public class CustomButton extends Button {


    public CustomButton(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.BUTTON_FONT_NAME);
        this.setTypeface(face);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.BUTTON_FONT_NAME);
        this.setTypeface(face);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.BUTTON_FONT_NAME);
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}