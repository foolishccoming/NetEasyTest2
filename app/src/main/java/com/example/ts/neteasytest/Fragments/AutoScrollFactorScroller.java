package com.example.ts.neteasytest.Fragments;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by ts on 20-1-16.
 */

public class AutoScrollFactorScroller extends Scroller {
    private double mFactory = 1;

    public AutoScrollFactorScroller(Context context) {
        super(context);
    }

    public AutoScrollFactorScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public double getFactor() {
        return mFactory;
    }

    public void setFactor(double mFactory) {
        this.mFactory = mFactory;
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration){
        super.startScroll(startX,startY,dx,dy, (int) (mFactory*mFactory));
    }
}
