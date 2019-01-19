package com.example.administrator.myapplication13.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.view.View;

@SuppressLint("AppCompatCustomView")
public class ColorFilterImageView extends ImageView implements View.OnTouchListener {
    public ColorFilterImageView(Context context) {
        this(context, null, 0);
    }

    public ColorFilterImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            // 按下时图像变灰
            case MotionEvent.ACTION_DOWN:
                setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                break;
            // 手指离开或取消操作时恢复原色
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setColorFilter(Color.TRANSPARENT);
                break;
            default:
                break;
        }
        return false;
    }
}
