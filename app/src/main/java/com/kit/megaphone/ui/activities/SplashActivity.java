package com.kit.megaphone.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.kit.megaphone.R;
import com.kit.megaphone.databinding.ActivitySplashBinding;

/**
 * 스플래시 화면
 * 2초 후 메인화면으로 넘어감.
 */
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        // 로고 애니메이션
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        binding.bgLogo.setAnimation(anim);
        binding.logoText.setAnimation(anim);

        // 2초 후 메인화면으로 이동
        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }, 2000);
    }
}
