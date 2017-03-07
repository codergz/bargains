package com.example.gao.bargains.ui;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.gao.bargains.R;

import static com.example.gao.bargains.R.attr.editTextStyle;

/**
 * Created by gao on 2017/3/7.
 */

public class EditTextWithDelete extends EditText {
    private Drawable delete;
    //父类没有无参构造函数
    public EditTextWithDelete(Context context) {
        this(context, null);
        init();
    }

    public EditTextWithDelete(Context context, AttributeSet attrs) {
        this(context, attrs,editTextStyle);
        init();
    }

    public EditTextWithDelete(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
        init();
    }
    public EditTextWithDelete(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }




    private void init(){
        delete = getContext().getResources().getDrawable(R.drawable.search_box_delete);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }
    private void setDrawable() {
        //文本框没有内容，则图片不出现
        if (length() == 0 ) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        //文本框有输入内容时，文本框出现
        else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, delete, null);
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        if (delete != null && event.getAction() == MotionEvent.ACTION_UP) {
            //获取用户点击的位置
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            //创建一个矩形
            Rect rect = new Rect();
            //返回一个EditText大小的矩形
            getGlobalVisibleRect(rect);
            //更改矩形的左边界
            rect.left = rect.right - delete.getIntrinsicWidth();
            //判断用户点击位置是否在矩形范围内，是，则清空输入内容
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }
}