package com.reafor.jiamixiu.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;

import com.reafor.jiamixiu.R;

/**
 * selector button
 */

public final class RoundButton extends android.support.v7.widget.AppCompatButton {
    private int[][] states;
    private StateListDrawable mStateBackground;
    private GradientDrawable mNormalBackground;
    private GradientDrawable mPressedBackground;
    private GradientDrawable mUnableBackground;
    private int mNormalTextColor;
    private int mPressedTextColor;
    private int mUnableTextColor;
    private ColorStateList mTextColorStateList;
    private int strokeDashWidth;
    private int strokeDashGap;
    private int cornerRadius;
    private int btn_normalStrokeColor;
    private int btn_pressedSrokeColor;
    private int btn_unableStrokeColor;
    private int mNormalStrokeWidth;
    private int mPressedStokeWidth;
    private int mUnableStrokeWidth;
    private int btn_normalSolidColor;
    private int btn_pressedSolidColor;
    private int btn_unableSolidColor;

    public RoundButton(Context context) {
        this(context, null);
    }

    public RoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        states = new int[4][];
        Drawable drawable = getBackground();
        if(drawable != null && drawable instanceof StateListDrawable){
            mStateBackground = (StateListDrawable) drawable;
        }else{
            mStateBackground = new StateListDrawable();
        }

        mNormalBackground = new GradientDrawable();
        mPressedBackground = new GradientDrawable();
        mUnableBackground = new GradientDrawable();
        //pressed, focused, normal, unable
        states[0] = new int[] { android.R.attr.state_enabled, android.R.attr.state_pressed };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[3] = new int[] { -android.R.attr.state_enabled};
        states[2] = new int[] { android.R.attr.state_enabled };

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundButton);
        //textcolor
        mTextColorStateList = getTextColors();
        int mDefaultNormalTextColor = mTextColorStateList.getColorForState(states[2], getCurrentTextColor());
        int mDefaultPressedTextColor = mTextColorStateList.getColorForState(states[0], getCurrentTextColor());
        int mDefaultUnableTextColor = mTextColorStateList.getColorForState(states[3], getCurrentTextColor());
        mNormalTextColor = a.getColor(R.styleable.RoundButton_btn_normalTextcolor,mDefaultNormalTextColor);
        mPressedTextColor = a.getColor(R.styleable.RoundButton_btn_pressedTextcolor,mDefaultPressedTextColor);
        mUnableTextColor = a.getColor(R.styleable.RoundButton_btn_unableTextcolor,mDefaultUnableTextColor);
        cornerRadius = a.getLayoutDimension(R.styleable.RoundButton_btnCornerRadius, 0);

        //背景
        btn_normalSolidColor = a.getColor(R.styleable.RoundButton_btn_normalSolidColor,0);
        btn_pressedSolidColor = a.getColor(R.styleable.RoundButton_btn_pressedSolidcolor,btn_normalSolidColor);
        btn_unableSolidColor = a.getColor(R.styleable.RoundButton_btn_unableSolidcolor,btn_normalSolidColor);


        //stroke
        btn_normalStrokeColor = a.getColor(R.styleable.RoundButton_btn_normalStrokeColor, 0x0);
        btn_pressedSrokeColor = a.getColor(R.styleable.RoundButton_btn_pressedStokeColor,0);
        btn_unableStrokeColor = a.getColor(R.styleable.RoundButton_btn_unableStrokeColor,0);
        mNormalStrokeWidth = a.getDimensionPixelSize(R.styleable.RoundButton_btn_normalStrokeWidth, 0);
        mPressedStokeWidth = a.getDimensionPixelSize(R.styleable.RoundButton_btn_pressedStrokeWidth,0);
        mUnableStrokeWidth = a.getDimensionPixelSize(R.styleable.RoundButton_btn_unableStrokeWidth,0);

        strokeDashWidth = a.getDimensionPixelSize(R.styleable.RoundButton_btnStrokeDashWidth, 0);
        strokeDashGap = a.getDimensionPixelSize(R.styleable.RoundButton_btnStrokeDashGap, 0);

        a.recycle();

        setSingleLine(true);
        setGravity(Gravity.CENTER);


    }

    public void changeColor(int textColor,int solidColor){
        if (textColor != -1){
            this.mNormalTextColor = this.mPressedTextColor= textColor;
        }
        if (solidColor != -1){
            this.btn_normalSolidColor =this.btn_pressedSolidColor = solidColor;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setup();
    }

    private void setup() {
        //set text color
        int[] colors = new int[] {mPressedTextColor, mPressedTextColor, mNormalTextColor, mUnableTextColor};
        mTextColorStateList = new ColorStateList(states, colors);
        setTextColor(mTextColorStateList);

        //set radius
        if (cornerRadius == -1){
            RectF rect = new RectF(mNormalBackground.getBounds());
            mNormalBackground.setCornerRadius((rect.height() > rect.width() ? rect.width() : rect.height()) / 2);
            mPressedBackground.setCornerRadius((rect.height() > rect.width() ? rect.width() : rect.height()) / 2);
            mUnableBackground.setCornerRadius((rect.height() > rect.width() ? rect.width() : rect.height()) / 2);
        } else {
            mNormalBackground.setCornerRadius(cornerRadius);
            mPressedBackground.setCornerRadius(cornerRadius);
            mUnableBackground.setCornerRadius(cornerRadius);
        }
        //set stroke
        setStroke(mNormalBackground, btn_normalStrokeColor, mNormalStrokeWidth);
        setStroke(mPressedBackground, btn_pressedSrokeColor, mPressedStokeWidth);
        setStroke(mUnableBackground, btn_unableStrokeColor, mUnableStrokeWidth);

        //set background
        mNormalBackground.setColor(btn_normalSolidColor);
        mPressedBackground.setColor(btn_pressedSolidColor);
        mUnableBackground.setColor(btn_unableSolidColor);
        mStateBackground.addState(states[0], mPressedBackground);
        mStateBackground.addState(states[1], mPressedBackground);
        mStateBackground.addState(states[3], mUnableBackground);
        mStateBackground.addState(states[2], mNormalBackground);
        setBackgroundDrawable(mStateBackground);
    }

    private void setStroke(GradientDrawable mBackground, int mStrokeColor, int mStrokeWidth) {
        mBackground.setStroke(mStrokeWidth, mStrokeColor, strokeDashWidth, strokeDashGap);
    }

}