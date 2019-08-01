package com.fenbi.android.transitiondemo;

import android.graphics.PointF;
import android.view.View;

/**
 * Created by liuteng on 2019/7/31.
 */

public class ViewPositionWrapper {
    private View target;

    public ViewPositionWrapper(View view) {
        target = view;
    }

    public void setPosition(PointF position) {
        target.setTranslationX(position.x);
        target.setTranslationY(position.y);
    }

    public PointF getPosition() {
        return new PointF(target.getTranslationX(), target.getTranslationY());
    }
}
