package com.kit.megaphone.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kit.megaphone.R;
import com.kit.megaphone.adapters.MainAdapter;
import com.kit.megaphone.databinding.ActivityMainBinding;

/**
 * 메인화면
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth auth;

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        auth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Log.d(MainActivity.class.getSimpleName(), user.getEmail());
            } else {
                startActivity(new Intent(this, SignInActivity.class));
                finish();
            }
        };

        binding.fab.setOnClickListener(v -> startActivity(new Intent(this, WriteActivity.class)));

        adapter = new MainAdapter();
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setItemAnimator(new DefaultItemAnimator());
        binding.recycler.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }
}
