package com.example.administrator.myapplication13.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class EditTextUtils {
    //点击获取焦点
    public static void searchPoint(Context context, EditText editText){
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.findFocus();
        mInputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }
    //失去焦点
    public static void losePoint(Context context,EditText editText){
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.setFocusable(false);
        if (mInputMethodManager.isActive()) {
            mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
}
