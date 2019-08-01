package com.fenbi.android.transitiondemo;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * 同样的动画使用ObjectAnimator和ValueAnimator分别实现
 */
public class AnimatorDemoActivity extends AppCompatActivity {
    private View targetView;
    private View objectAnimatorBtn;
    private View valueAnimatorBtn;
    private PointF dst = new PointF(800, 800);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_demo);
        getSupportActionBar().setTitle("Animator Demo");
        initView();
    }

    private void initView() {
        targetView = findViewById(R.id.target);
        objectAnimatorBtn = findViewById(R.id.object_animator);
        valueAnimatorBtn = findViewById(R.id.value_animator);
        objectAnimatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startObjectAnimator(targetView);
            }
        });
        valueAnimatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startValueAnimator(targetView);
            }
        });
    }

    /**
     * 使用ObjectAnimator实现移动动画
     *
     * @param view 要做动画的view
     */
    private void startObjectAnimator(View view) {
        ViewPositionWrapper wrapper = new ViewPositionWrapper(view);
        PointF currentPosition = wrapper.getPosition();
        PointF startPosition = currentPosition;
        PointF endPosition = getEndPosition(currentPosition);
        ObjectAnimator objectAnimator =
                ObjectAnimator.ofObject(wrapper,
                        "position",
                        new LinearMoveEvaluator(),
                        startPosition, endPosition).setDuration(1000);
        // BounceInterpolator 能够让动画有回弹效果
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.start();
    }

    /**
     * 使用ValueAnimator实现移动动画
     *
     * @param view 要做动画的view
     */
    private void startValueAnimator(View view) {
        PointF currentPosition = new PointF(view.getTranslationX(), view.getTranslationY());
        PointF startPosition = currentPosition;
        PointF endPosition = getEndPosition(currentPosition);
        ValueAnimator animator = ValueAnimator.ofObject(new LinearMoveEvaluator(), startPosition, endPosition);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF position = (PointF) animation.getAnimatedValue();
                targetView.setTranslationX(position.x);
                targetView.setTranslationY(position.y);
            }
        });
        animator.setDuration(1000).setInterpolator(new BounceInterpolator());
        animator.start();
    }

    private PointF getEndPosition(PointF currentPosition) {
        PointF result;
        if (currentPosition.x > dst.x / 2 || currentPosition.y > dst.y / 2) {
            result = new PointF(0, 0);
        } else {
            result = dst;
        }
        return result;
    }

    /**
     * 实现了简单的直线运动，如果需要曲线运动，可以参考这个实现
     */
    class LinearMoveEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            // 这里只是实现了直线移动，可以根据实际场景实现曲线移动
            float resultx = fraction * (endValue.x - startValue.x) + startValue.x;
            float resulty = fraction * (endValue.y - startValue.y) + startValue.y;

            return new PointF(resultx, resulty);
        }
    }
}
