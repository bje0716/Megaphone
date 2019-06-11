package com.kit.megaphone.ui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kit.megaphone.R;
import com.kit.megaphone.databinding.ActivityWriteBinding;
import com.kit.megaphone.datas.ArticleData;
import com.kit.megaphone.utils.Util;
import com.kit.megaphone.utils.VerifyUtil;

public class WriteActivity extends AppCompatActivity {

    private ActivityWriteBinding binding;

    private DatabaseReference database;
    private FirebaseUser user;

    private String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(Util.setBackArrowColor(this));

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.topic));
        binding.topic.setAdapter(adapter);

        // 분류 스피너 선택
        binding.topic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                topic = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 청원 내용 글자 수
        binding.content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.length.setText(binding.content.getText().toString().length()+ "자");
            }
        });

        binding.submit.setOnClickListener(v -> {
            final String title = binding.title.getText().toString();
            final String content = binding.content.getText().toString();

            if (!VerifyUtil.verifyStrings(title, content)) {
                Toast.makeText(this, "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show();
                return;
            } else {
                // firebase database submit.
                if (user != null) {
                    ArticleData articleData = new ArticleData(user.getDisplayName(), Util.getDate(System.currentTimeMillis()), topic, title, content);
                    database.child("article").push().setValue(articleData);
                    finish();
                }
            }
        });
    }

    // 툴바 뒤로가기 이벤트
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
