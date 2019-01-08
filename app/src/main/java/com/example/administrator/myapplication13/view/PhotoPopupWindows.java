package com.example.administrator.myapplication13.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.myapplication13.R;

public class PhotoPopupWindows extends PopupWindow {
    // PopupWindow 菜单布局
    private View mMenuView;
    // 上下文参数
    private Context context;
    // PopupWindow 菜单 空间单击事件
    private View.OnClickListener myOnClick;

    public PhotoPopupWindows(Activity context, View.OnClickListener myOnClick) {
        super(context);
        this.context = context;
        this.myOnClick = myOnClick;
        Init();
    }

    private void Init() {
        // TODO Auto-generated method stub
        // PopupWindow 导入
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.fragment_my_personal_hanger_image_popupwindow_item, null);
        TextView btn_camera =  mMenuView
                .findViewById(R.id.open_camera);
        TextView btn_photo = mMenuView
                .findViewById(R.id.open_album);
        TextView btn_cancel = mMenuView
                .findViewById(R.id.cancal);

        btn_camera.setOnClickListener(myOnClick);
        btn_cancel.setOnClickListener(myOnClick);
        btn_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

        // 导入布局
        this.setContentView(mMenuView);
        // 设置动画效果
       /* this.setAnimationStyle(R.style.AnimationFade);
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);*/
        // 设置可触
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
        // 单击弹出窗以外处 关闭弹出窗
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mMenuView.findViewById(R.id.ll_pop).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


}
