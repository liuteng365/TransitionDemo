package com.fenbi.android.transitiondemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 改变view的属性，使用transition生成动画
 */
public class TransitionDemoActivity extends AppCompatActivity {

    private ViewGroup sceneRoot;
    private ImageView yuantiku;
    private ImageView yuansouti;
    boolean isTransformChanged = false;
    boolean isChangedClipBound = false;
    boolean isImageTransformChanged = false;
    Transition[] transitions = new Transition[]{
            new Fade(),
            new Explode(),
            new Slide(),
    };
    int index = 0;
    boolean isVisibleTransitionDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_demo);
        initView();
        initContentTransition();
        initSharedElementTransition();
    }

    private void initView() {
        getSupportActionBar().setTitle("Transition Demo");
        sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        yuantiku = (ImageView) findViewById(R.id.yuantiku);
        yuansouti = (ImageView) findViewById(R.id.yuansouti);
    }

    private void initContentTransition() {
        // 设置transition enter 动画
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new Explode()
                .excludeTarget(android.R.id.statusBarBackground, true)
                .excludeTarget(R.id.action_bar_container, true));
        transitionSet.addTransition(new Fade()
                .addTarget(android.R.id.statusBarBackground)
                .addTarget(R.id.action_bar_container));
        getWindow().setEnterTransition(transitionSet);
    }

    private void initSharedElementTransition() {
        yuansouti.setTransitionName("yuansouti_icon");
//        getWindow().setSharedElementEnterTransition(new FixedTransition());
    }

    private void swapViews(View one, View two) {
        ViewGroup.LayoutParams layoutParams = one.getLayoutParams();
        one.setLayoutParams(two.getLayoutParams());
        two.setLayoutParams(layoutParams);
    }

    public void changeBounds() {
        TransitionManager.beginDelayedTransition(sceneRoot, new ChangeBounds());
        swapViews(yuansouti, yuantiku);
    }

    public void changeTransform() {
        TransitionManager.beginDelayedTransition(sceneRoot, new ChangeTransform());
        if (isTransformChanged) {
            yuansouti.setRotation(0);
            yuantiku.setRotation(0);
            isTransformChanged = false;
        } else {
            yuansouti.setRotation(90);
            yuantiku.setRotation(-90);
            isTransformChanged = true;
        }
    }

    public void changeClipBound() {
        TransitionManager.beginDelayedTransition(sceneRoot, new ChangeClipBounds());
        if (isChangedClipBound) {
            yuansouti.setClipBounds(null);
            yuantiku.setClipBounds(null);
            isChangedClipBound = false;
        } else {
            yuansouti.setClipBounds(new Rect(0, 0, 100, 100));
            yuantiku.setClipBounds(new Rect(100, 100, 200, 200));
            isChangedClipBound = true;
        }
    }

    public void changeImageTransform() {
        TransitionManager.beginDelayedTransition(sceneRoot, new ChangeImageTransform());
        if (isImageTransformChanged) {
            yuansouti.setScaleType(ImageView.ScaleType.CENTER_CROP);
            yuantiku.setScaleType(ImageView.ScaleType.CENTER_CROP);
            isImageTransformChanged = false;
        } else {
            yuansouti.setScaleType(ImageView.ScaleType.CENTER);
            yuantiku.setScaleType(ImageView.ScaleType.CENTER);
            isImageTransformChanged = true;
        }
    }

    public void doInvisibleAnimation() {
        TransitionManager.beginDelayedTransition(sceneRoot, transitions[index % transitions.length]);
        if (isVisibleTransitionDone) {
            isVisibleTransitionDone = false;
            yuansouti.setVisibility(View.VISIBLE);
            yuantiku.setVisibility(View.VISIBLE);
            index++;
        } else {
            isVisibleTransitionDone = true;
            yuansouti.setVisibility(View.INVISIBLE);
            yuantiku.setVisibility(View.INVISIBLE);
        }
    }

    public void onButtonClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.change_bound_btn:
                changeBounds();
                break;
            case R.id.change_transform_btn:
                changeTransform();
                break;
            case R.id.change_clip_bound_btn:
                changeClipBound();
                break;
            case R.id.change_image_transform_btn:
                changeImageTransform();
                break;
            case R.id.fade_explode_slide:
                doInvisibleAnimation();
                break;

        }
    }

    class FixedTransition extends Transition {
        private ChangeBounds changeBounds = new ChangeBounds();
        private ChangeTransform changeTransform = new ChangeTransform();

        @Override
        public void captureStartValues(TransitionValues transitionValues) {
            changeBounds.captureStartValues(transitionValues);
            changeTransform.captureStartValues(transitionValues);
        }

        @Override
        public void captureEndValues(TransitionValues transitionValues) {
            changeBounds.captureEndValues(transitionValues);
            changeTransform.captureEndValues(transitionValues);
        }

        @Override
        public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
            AnimatorSet animator = new AnimatorSet();
            animator.playTogether(changeBounds.createAnimator(sceneRoot, startValues, endValues),
                    changeTransform.createAnimator(sceneRoot, startValues, endValues));
            return animator;
        }

        @Override
        public String[] getTransitionProperties() {
            List<String> list = new ArrayList<>();
            list.addAll(changeBounds.getTargetNames());
            list.addAll(changeTransform.getTargetNames());
            return (String[]) list.toArray();
        }
    }
}
