package com.kit.megaphone.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kit.megaphone.R;
import com.kit.megaphone.databinding.ActivitySignUpBinding;
import com.kit.megaphone.datas.UserData;
import com.kit.megaphone.utils.PreferencesUtil;
import com.kit.megaphone.utils.Util;
import com.kit.megaphone.utils.VerifyUtil;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference database;
    private PreferencesUtil util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        binding.setActivity(this);

        util = new PreferencesUtil(this);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(Util.setBackArrowColor(this));

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void submit(View view) {
        final String email = binding.email.getText().toString();
        final String password = binding.password.getText().toString();
        final String age = binding.age.getText().toString();
        final String address = binding.address.getText().toString();
        final String name = binding.name.getText().toString();

        if (!VerifyUtil.verifyStrings(email, password, age, address, name)) {
            Toast.makeText(this, "입력칸 중 공백이 있습니다", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "비밀번호는 6자리 이상으로 설정해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final FirebaseUser user = task.getResult().getUser();
                    final UserData registerUserData = new UserData(email, name, age, address);
                    database.child("users").child(user.getUid()).setValue(registerUserData);

                    util.putString("email", email);
                    util.putString("name", name);
                    util.putString("age", age);
                    util.putString("address", address);

                    startActivity(new Intent(this, MainActivity.class));
                    Toast.makeText(this, "회원가입 완료됨", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
