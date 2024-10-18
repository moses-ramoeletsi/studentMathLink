package com.example.studentmathlink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextInputEditText useremail, userpassword;

    private MaterialButton loginButton;
    private TextView register;
    private ProgressDialog loadingBar;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        useremail = findViewById (R.id.emailEditText);
        userpassword = findViewById (R.id.passwordEditText);
        loginButton = findViewById (R.id.loginButton);
        register = findViewById (R.id.registerTextView);
        mAuth = FirebaseAuth.getInstance ();
        loadingBar = new ProgressDialog (this);
        currentUser = mAuth.getCurrentUser ();
        loginButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                AllowUserToLogin ();
            }
        });
        register.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                sendUserToRegister ();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void sendUserToRegister () {
        Intent registerIntent = new Intent (Login.this, Register.class);
        startActivity (registerIntent);
    }

    private void AllowUserToLogin () {
        String email = useremail.getText ().toString ().trim ();
        String password = userpassword.getText ().toString ();
        if ( TextUtils.isEmpty (email) ) {
            Toast.makeText (Login.this, "Please enter email id", Toast.LENGTH_SHORT).show ();
        } else if ( TextUtils.isEmpty (password) ) {
            Toast.makeText (Login.this, "Please enter password", Toast.LENGTH_SHORT).show ();
        } else {
            loadingBar.setTitle ("Sign In");
            loadingBar.setMessage ("Please wait, because good things always take time");
            loadingBar.show ();
            mAuth.signInWithEmailAndPassword (email, password)
                    .addOnCompleteListener (new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete (@NonNull Task<AuthResult> task) {
                            if ( task.isSuccessful () ) {
                                sendToMainActivity ();
                            } else {
                                String msg = task.getException ().toString ();
                                Toast.makeText (Login.this, "Error: " + msg, Toast.LENGTH_SHORT).show ();
                                loadingBar.dismiss ();
                            }
                        }
                    });
        }
    }

    private void sendToMainActivity () {
        Intent mainIntent = new Intent (Login.this, MainActivity.class);
        startActivity (mainIntent);
        finish ();
    }

}