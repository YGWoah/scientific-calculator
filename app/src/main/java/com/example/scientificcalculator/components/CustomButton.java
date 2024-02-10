package com.example.scientificcalculator.components;

import android.app.ActionBar;
import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.ContextCompat;
import com.example.scientificcalculator.R;
import android.graphics.drawable.GradientDrawable;
// import layout params
import android.widget.LinearLayout.LayoutParams;


public class CustomButton extends androidx.appcompat.widget.AppCompatButton {

    public CustomButton(Context context) {
        super(context);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(ContextCompat.getColor(getContext(), R.color.gray));
        gradientDrawable.setCornerRadius(20);
        this.setBackground(gradientDrawable);
        this.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
        this.setTextSize(20);
        // make the text in all lowercase
        this.setTransformationMethod(null);
    }
}