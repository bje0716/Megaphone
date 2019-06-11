package com.kit.megaphone.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kit.megaphone.R;
import com.kit.megaphone.databinding.ActivitySignInBinding;
import com.kit.megaphone.utils.VerifyUtil;

public class SignInActivity extends AppCompatActivity {

    private static final int REQUEST_GOOGLE = 100;

    private ActivitySignInBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference database;
    private GoogleSignInClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        binding.setActivity(this);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this, gso);

        binding.gso.setOnClickListener(v -> startActivityForResult(client.getSignInIntent(), REQUEST_GOOGLE));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_GOOGLE:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    auth.signInWithCredential(credential)
                            .addOnCompleteListener(command -> {
                                if (command.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    Toast.makeText(this, user.getDisplayName() + "님 반갑습니다", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(this, "구글 로그인에 실패했습니다", Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (ApiException e) {
                    Log.w(SignInActivity.class.getSimpleName(), "google sign in failed.");
                }
                break;
        }
    }

    public void signIn(View view) {
        final String email = binding.email.getText().toString();
        final String password = binding.password.getText().toString();

        if (!VerifyUtil.verifyStrings(email, password)) {
            Toast.makeText(this, "입력칸 중 공백이 있습니다", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    startActivity(new Intent(this, MainActivity.class));
                    Toast.makeText(this, "로그인 완료됨", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    public void signUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void findAccount(View view) {

    }
}
