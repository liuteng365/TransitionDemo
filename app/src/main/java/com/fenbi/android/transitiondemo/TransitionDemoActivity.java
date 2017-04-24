package com.fenbi.android.transitiondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

public class TransitionDemoActivity extends AppCompatActivity {

    private ViewGroup sceneRoot;
    private View yuantiku;
    private View yuansouti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_demo);
        initView();
    }

    private void initView() {
        findViewById(R.id.change_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
        sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        yuantiku = findViewById(R.id.yuantiku);
        yuansouti = findViewById(R.id.yuansouti);
    }

    private void swapViews(View one, View two) {
        ViewGroup.LayoutParams layoutParams = one.getLayoutParams();
        one.setLayoutParams(two.getLayoutParams());
        two.setLayoutParams(layoutParams);
    }

    public void change() {
        TransitionManager.beginDelayedTransition(sceneRoot);
        swapViews(yuansouti, yuantiku);
    }
}
