package com.mobisys.recipe.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.mobisys.recipe.util.ApplicationConstant;

/**
 * Created by ubuntu1 on 23/2/16.
 */
public class CustomEditText extends EditText {


    public CustomEditText(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.EDIT_TEXT_FONT_NAME);
        this.setTypeface(face);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.EDIT_TEXT_FONT_NAME);
        this.setTypeface(face);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), ApplicationConstant.EDIT_TEXT_FONT_NAME);
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
