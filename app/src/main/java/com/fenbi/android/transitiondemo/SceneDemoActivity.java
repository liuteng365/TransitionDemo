package com.fenbi.android.transitiondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 为两种状态各自定义了一个布局文件，通过transition实现两种状态的切换动画
 */
public class SceneDemoActivity extends AppCompatActivity {

    private Scene scene1;
    private Scene scene2;
    private boolean isScene2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_demo);
        initView();
        initScene();
    }

    private void initView() {
        getSupportActionBar().setTitle("Scene Demo");
        findViewById(R.id.change_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
    }

    private void initScene() {
        ViewGroup sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.scene_1, this);
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.scene_2, this);
        TransitionManager.go(scene1);
    }

    /**
     * scene1和scene2相互切换，播放动画
     */
    public void change() {
        TransitionManager.go(isScene2 ? scene1 : scene2, new ChangeBounds());
        isScene2 = !isScene2;
    }
}
