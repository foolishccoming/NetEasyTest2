package com.example.ts.neteasytest.Fragments;

/**
 * Created by ts on 20-1-15.
 */

public class SelectItems  {
    public String mItemTitle;
    public int mImageBeauty, mImageBorder,mImageUnbraller;
    public String mTextBeauty,mTextBorder,mTextUnbraller;

    public SelectItems(String mItemTitle, int mImageBeauty, int mImageBorder, int mImageUnbraller, String mTextBeauty, String mTextBorder, String mTextUnbraller){
        this.mItemTitle = mItemTitle;
        this.mImageBeauty = mImageBeauty;
        this.mImageBorder = mImageBorder;
        this.mImageUnbraller = mImageUnbraller;

        this.mTextBeauty = mTextBeauty;
        this.mTextBorder = mTextBorder;
        this.mTextUnbraller = mTextUnbraller;
    }

    public String getmItemTitle() {
        return mItemTitle;
    }

    public void setmItemTitle(String mItemTitle) {
        this.mItemTitle = mItemTitle;
    }

    public int getmImageBeauty() {
        return mImageBeauty;
    }

    public void setmImageBeauty(int mImageBeauty) {
        this.mImageBeauty = mImageBeauty;
    }

    public int getmImageBorder() {
        return mImageBorder;
    }

    public void setmImageBorder(int mImageBorder) {
        this.mImageBorder = mImageBorder;
    }

    public int getmImageUnbraller() {
        return mImageUnbraller;
    }

    public void setmImageUnbraller(int mImageUnbraller) {
        this.mImageUnbraller = mImageUnbraller;
    }

    public String getmTextBeauty() {
        return mTextBeauty;
    }

    public void setmTextBeauty(String mTextBeauty) {
        this.mTextBeauty = mTextBeauty;
    }

    public String getmTextBorder() {
        return mTextBorder;
    }

    public void setmTextBorder(String mTextBorder) {
        this.mTextBorder = mTextBorder;
    }

    public String getmTextUnbraller() {
        return mTextUnbraller;
    }

    public void setmTextUnbraller(String mTextUnbraller) {
        this.mTextUnbraller = mTextUnbraller;
    }
}
