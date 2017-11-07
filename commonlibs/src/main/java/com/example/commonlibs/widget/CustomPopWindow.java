package com.example.commonlibs.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.commonlibs.utils.BlurViewUtil;

/**
 * 自定义PopWindow类，封装了PopWindow的一些常用属性，用Builder模式支持链式调用
 */

public class CustomPopWindow implements PopupWindow.OnDismissListener {
    private static final float DEFAULT_ALPHA = 0.7f;
    private static final int DEFAULT_RADIUS = 8;
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private boolean mIsFocusable = true;
    private boolean mIsOutside = true;
    private int mResLayoutId = -1;
    private View mContentView;
    private PopupWindow mPopupWindow;
    private int mAnimationStyle = -1;

    private boolean mClippEnable = true;//default is true
    private boolean mIgnoreCheekPress = false;
    private int mInputMode = -1;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private int mSoftInputMode = -1;
    private boolean mTouchable = true;//default is ture
    private View.OnTouchListener mOnTouchListener;

    private Window mWindow;//当前Activity 的窗口
    /**
     * 弹出PopWindow 背景是否变暗，默认不会变暗。
     */
    private boolean mIsBackgroundDark = false;

    private float mBackgroundDrakValue = 0;// 背景变暗的值，0 - 1
    private boolean mIBlurEnable = false;//是否启用高斯模糊

    private int mRadius;//设置模糊度
    private int mStartHeight;//开始模糊的高度

    public View getContentView() {
        return mContentView;
    }

    private CustomPopWindow(PopupWindowBuilder mBuilder) {
        mContext = mBuilder.mContext;
        mWidth = mBuilder.mWidth;
        mHeight = mBuilder.mHeight;
        mIsFocusable = mBuilder.mIsFocusable;
        mResLayoutId = mBuilder.mResLayoutId;
        mContentView = mBuilder.mContentView;
        mAnimationStyle = mBuilder.mAnimationStyle;
        mClippEnable = mBuilder.mClippEnable;
        mIgnoreCheekPress = mBuilder.mIgnoreCheekPress;
        mInputMode = mBuilder.mInputMode;
        mOnDismissListener = mBuilder.mOnDismissListener;
        mSoftInputMode = mBuilder.mSoftInputMode;
        mTouchable = mBuilder.mTouchable;
        mIsBackgroundDark = mBuilder.mIsBackgroundDark;
        mBackgroundDrakValue = mBuilder.mBackgroundDrakValue;
        mOnTouchListener = mBuilder.mOnTouchListener;
        mIBlurEnable = mBuilder.mIBlurEnable;
        mStartHeight = mBuilder.mStartHeight;
        create();

    }

    public static PopupWindowBuilder builder() {
        return new PopupWindowBuilder();
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    /**
     * @param anchor
     * @param xOff
     * @param yOff
     * @return
     */
    public CustomPopWindow showAsDropDown(View anchor, int xOff, int yOff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff);
        }
        return this;
    }

    public CustomPopWindow showAsDropDown(View anchor) {
        if (mPopupWindow != null) {
            if (!mPopupWindow.isShowing()) {
                if (Build.VERSION.SDK_INT < 24) {
                    mPopupWindow.showAsDropDown(anchor);
                } else {
                    // 适配 android 7.0
                    int[] location = new int[2];
                    anchor.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    mPopupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, 0, y + mPopupWindow.getHeight() + anchor.getHeight());
                }
            }
        }
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public CustomPopWindow showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff, gravity);
        }
        return this;
    }

    public boolean isShowing() {
        if (mPopupWindow != null) {
            return mPopupWindow.isShowing();
        } else {
            return false;
        }
    }


    /**
     * 相对于父控件的位置（通过设置Gravity.CENTER，下方Gravity.BOTTOM等 ），可以设置具体位置坐标
     *
     * @param parent  父控件
     * @param gravity
     * @param x       the popup's x location offset
     * @param y       the popup's y location offset
     * @return
     */
    public CustomPopWindow showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
        return this;
    }

    /**
     * 添加一些属性设置
     *
     * @param popupWindow
     */
    private void apply(PopupWindow popupWindow) {
        popupWindow.setClippingEnabled(mClippEnable);
        if (mIgnoreCheekPress) {
            popupWindow.setIgnoreCheekPress();
        }
        if (mInputMode != -1) {
            popupWindow.setInputMethodMode(mInputMode);
        }
        if (mSoftInputMode != -1) {
            popupWindow.setSoftInputMode(mSoftInputMode);
        }
        if (mOnDismissListener != null) {
            popupWindow.setOnDismissListener(mOnDismissListener);
        }
        if (mOnTouchListener != null) {
            popupWindow.setTouchInterceptor(mOnTouchListener);
        }
        popupWindow.setTouchable(mTouchable);


    }

    private PopupWindow create() {

        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(mResLayoutId, null);
        }

        // 获取当前Activity的window
        Activity activity = (Activity) mContentView.getContext();
        if (activity != null && mIsBackgroundDark) {
            //如果设置的值在0 - 1的范围内，则用设置的值，否则用默认值
            final float alpha = (mBackgroundDrakValue > 0 && mBackgroundDrakValue < 1) ? mBackgroundDrakValue : DEFAULT_ALPHA;
            mWindow = activity.getWindow();
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = alpha;
            mWindow.setAttributes(params);
        }

        if (activity != null && mIBlurEnable) {
            if (mRadius == 0) {
                mRadius = DEFAULT_RADIUS;
            }
            Bitmap mBitmap = null;
            if (mStartHeight == 0) {
                mBitmap = BlurViewUtil.takeScreenShot(activity);
            } else {
                mBitmap = BlurViewUtil.takeScreenShot(activity, mStartHeight);
            }

            Bitmap mFastBlur = BlurViewUtil.doBlur(BlurViewUtil.getScaleBitmap(mBitmap), mRadius, false);
            mContentView.setBackground(new BitmapDrawable(mFastBlur));
        }


        if (mWidth != 0 && mHeight != 0) {
            mPopupWindow = new PopupWindow(mContentView, mWidth, mHeight);
        } else {
            mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (mAnimationStyle != -1) {
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }

        apply(mPopupWindow);//设置一些属性

        mPopupWindow.setFocusable(mIsFocusable);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(mIsOutside);

        if (mWidth == 0 || mHeight == 0) {
            mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            mWidth = mPopupWindow.getContentView().getMeasuredWidth();
            mHeight = mPopupWindow.getContentView().getMeasuredHeight();
        }

        // 添加dissmiss 监听
        mPopupWindow.setOnDismissListener(this);


        mPopupWindow.update();

        return mPopupWindow;
    }

    @Override
    public void onDismiss() {
        dissmiss();
    }

    /**
     * 关闭popWindow
     */
    public void dissmiss() {

        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss();
        }

        //如果设置了背景变暗，那么在dissmiss的时候需要还原
        if (mWindow != null) {
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = 1.0f;
            mWindow.setAttributes(params);
        }
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }


    public static final class PopupWindowBuilder {

        private static final float DEFAULT_ALPHA = 0.7f;
        private Context mContext;
        private int mWidth;
        private int mHeight;
        private boolean mIsFocusable = true;
        private boolean mIsOutside = true;
        private int mResLayoutId = -1;
        private View mContentView;
        private int mAnimationStyle = -1;
        private boolean mClippEnable = true;//default is true
        private boolean mIgnoreCheekPress = false;
        private int mInputMode = -1;
        private PopupWindow.OnDismissListener mOnDismissListener;
        private int mSoftInputMode = -1;
        private boolean mTouchable = true;//default is ture
        private View.OnTouchListener mOnTouchListener;
        /**
         * 弹出PopWindow 背景是否变暗，默认不会变暗。
         */
        private boolean mIsBackgroundDark = false;
        private float mBackgroundDrakValue = 0;// 背景变暗的值，0 - 1
        private boolean mIBlurEnable = false;//是否启用高斯模糊
        private int mRadius;//设置模糊度
        private int mStartHeight;

        public PopupWindowBuilder with(Context context) {
            this.mContext = context;
            return this;
        }

        public PopupWindowBuilder setStartHeightBlur(int mStartHeight) {
            this.mStartHeight = mStartHeight;
            return this;
        }

        public PopupWindowBuilder size(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
            return this;
        }


        public PopupWindowBuilder setFocusable(boolean focusable) {
            this.mIsFocusable = focusable;
            return this;
        }


        public PopupWindowBuilder setView(int resLayoutId) {
            this.mResLayoutId = resLayoutId;
            this.mContentView = null;
            return this;
        }

        public PopupWindowBuilder setView(View view) {
            this.mContentView = view;
            this.mResLayoutId = -1;
            return this;
        }

        public PopupWindowBuilder setOutsideTouchable(boolean outsideTouchable) {
            this.mIsOutside = outsideTouchable;
            return this;
        }

        /**
         * 设置弹窗动画
         *
         * @param animationStyle
         * @return
         */
        public PopupWindowBuilder setAnimationStyle(int animationStyle) {
            this.mAnimationStyle = animationStyle;
            return this;
        }


        public PopupWindowBuilder setClippingEnable(boolean enable) {
            this.mClippEnable = enable;
            return this;
        }


        public PopupWindowBuilder setIgnoreCheekPress(boolean ignoreCheekPress) {
            this.mIgnoreCheekPress = ignoreCheekPress;
            return this;
        }

        public PopupWindowBuilder setInputMethodMode(int mode) {
            this.mInputMode = mode;
            return this;
        }

        public PopupWindowBuilder setOnDissmissListener(PopupWindow.OnDismissListener onDissmissListener) {
            this.mOnDismissListener = onDissmissListener;
            return this;
        }


        public PopupWindowBuilder setSoftInputMode(int softInputMode) {
            this.mSoftInputMode = softInputMode;
            return this;
        }


        public PopupWindowBuilder setTouchable(boolean touchable) {
            this.mTouchable = touchable;
            return this;
        }

        public PopupWindowBuilder setTouchIntercepter(View.OnTouchListener touchIntercepter) {
            this.mOnTouchListener = touchIntercepter;
            return this;
        }

        /**
         * 设置背景变暗是否可用
         *
         * @param isDark
         * @return
         */
        public PopupWindowBuilder enableBackgroundDark(boolean isDark) {
            this.mIsBackgroundDark = isDark;
            return this;
        }

        /**
         * 设置北京变暗的值
         *
         * @param darkValue
         * @return
         */
        public PopupWindowBuilder setBgDarkAlpha(float darkValue) {
            this.mBackgroundDrakValue = darkValue;
            return this;
        }

        public PopupWindowBuilder enableBlur(boolean isBlur) {
            this.mIBlurEnable = isBlur;
            return this;
        }

        public PopupWindowBuilder setBlurRadius(int mBlurRadius) {
            this.mRadius = mBlurRadius;
            return this;
        }

        public CustomPopWindow build() {
            //构建PopWindow

            return new CustomPopWindow(this);
        }

    }

}
