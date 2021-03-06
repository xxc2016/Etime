package com.student.xxc.etime.view;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.student.xxc.etime.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences=getSharedPreferences("default_night", MODE_PRIVATE);
        int currentNightMode = preferences.getInt("default_night",getResources().getConfiguration().uiMode);
        getDelegate().setLocalNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //设置修改状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        TextView textView1=findViewById(R.id.textView2);
        TextView textView2=findViewById(R.id.textView3);
        String group="团队介绍：咸鱼突刺团队,四名成员组成，在生活中寻找灵感，让软件散发光彩。";
        String contact="联系我们：\n                18212462429(电话)\n                821222974zkk@gmail.com";
        String join="加入我们";
        String about=group+"\n\n"+contact+"\n\n"+join;
        textView1.setText("v1.0.1");
        //textView2.setText(about);

    }
}
