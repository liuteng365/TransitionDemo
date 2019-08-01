package com.fenbi.android.transitiondemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;

/**
 * Created by liuteng on 2017/4/24.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        getSupportActionBar().setTitle("Demo List");
        findViewById(R.id.scene_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SceneDemoActivity.class));
            }
        });
        findViewById(R.id.transition_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TransitionDemoActivity.class));
            }
        });
        findViewById(R.id.content_transition_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setExitTransition(new Fade());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
                startActivity(new Intent(MainActivity.this, TransitionDemoActivity.class), optionsCompat.toBundle());
            }
        });
        findViewById(R.id.shared_element_transition_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSharedElementExitTransition(new Fade());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(getActivity(), findViewById(R.id.yuansouti), "yuansouti_icon");
                startActivity(new Intent(MainActivity.this, TransitionDemoActivity.class), optionsCompat.toBundle());
            }
        });
        findViewById(R.id.animator_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimatorDemoActivity.class));
            }
        });
    }

    private Activity getActivity() {
        return MainActivity.this;
    }
}
