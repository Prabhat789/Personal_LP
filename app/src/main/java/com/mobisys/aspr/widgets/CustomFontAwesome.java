package com.mobisys.aspr.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mobisys.aspr.util.ApplicationConstant;

/**
 * Created by Prabhat on 26/03/16.
 */
public class CustomFontAwesome extends TextView {
    public CustomFontAwesome(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.FONT_AWESOME);
        this.setTypeface(face);
    }

    public CustomFontAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.FONT_AWESOME);
        this.setTypeface(face);
    }

    public CustomFontAwesome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.FONT_AWESOME);
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }


}
