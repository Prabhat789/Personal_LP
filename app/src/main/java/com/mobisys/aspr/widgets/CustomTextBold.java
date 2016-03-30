package com.mobisys.aspr.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mobisys.aspr.util.ApplicationConstant;

/**
 * Created by ubuntu1 on 30/3/16.
 */
public class CustomTextBold extends TextView{

        public CustomTextBold(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.TEXT_FONT_NAME_BOLD);
        this.setTypeface(face);
    }

        public CustomTextBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.TEXT_FONT_NAME_BOLD);
        this.setTypeface(face);
    }

        public CustomTextBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.TEXT_FONT_NAME_BOLD);
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}