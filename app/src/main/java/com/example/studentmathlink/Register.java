package com.example.studentmathlink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    private TextInputEditText username, useremail, useraddress, userschoolName,userphoneNumber, userpassword;
    private MaterialButton registerButton;
    private TextView login;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance ();
    private CollectionReference usersRef = db.collection ("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance ();
        username = findViewById (R.id.nameEditText);
        useremail = findViewById (R.id.emailEditText);
        useraddress = findViewById (R.id.addressEditText);
        userschoolName = findViewById(R.id.schoolNameEditText);
        userphoneNumber = findViewById (R.id.phoneNumberEditText);
        userpassword = findViewById (R.id.passwordEditText);
        registerButton = findViewById (R.id.registerButton);
        login = findViewById (R.id.loginTextView);

        loadingBar = new ProgressDialog (this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        login.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                sendUserToLoginActivity ();
            }
        });

        registerButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                createNewAccount ();
            }
        });
    }
    private void createNewAccount () {
        String name = username.getText ().toString ().trim ();
        String email = useremail.getText ().toString ().trim ();
        String address = useraddress.getText ().toString ().trim ();
        String schoolName = userschoolName.getText().toString().trim();
        String phoneNumber = userphoneNumber.getText ().toString ().trim ();
        String password = userpassword.getText ().toString ().trim ();


        if ( TextUtils.isEmpty (email) ) {
            Toast.makeText (Register.this, "Please enter email id", Toast.LENGTH_SHORT).show ();
            return;
        }
        if ( TextUtils.isEmpty (password) ) {
            Toast.makeText (Register.this, "Please enter password", Toast.LENGTH_SHORT).show ();
            return;
        }

        loadingBar.setTitle ("Creating New Account");
        loadingBar.setMessage ("Please wait, we are creating new Account");
        loadingBar.setCanceledOnTouchOutside (true);
        loadingBar.show ();

        UserDetails userDetails = new UserDetails (name, email, address,schoolName, phoneNumber);

        mAuth.createUserWithEmailAndPassword (email, password).addOnCompleteListener (new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete (@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful () ) {
                    FirebaseUser currentUser = mAuth.getCurrentUser ();
                    String userId = currentUser.getUid ();

                    usersRef.document (userId).set (userDetails).addOnCompleteListener (new OnCompleteListener <Void> () {
                        @Override
                        public void onComplete (@NonNull Task <Void> task) {
                            if ( task.isSuccessful () ) {
                                sendUserToLoginActivity ();
                                Toast.makeText (Register.this, "Account created successfully", Toast.LENGTH_SHORT).show ();
                            } else {
                                String msg = task.getException ().getMessage ();
                                Toast.makeText (Register.this, "Error: " + msg, Toast.LENGTH_SHORT).show ();
                            }
                            loadingBar.dismiss ();
                        }
                    });
                } else {
                    String msg = task.getException ().getMessage ();
                    Toast.makeText (Register.this, "Error: " + msg, Toast.LENGTH_SHORT).show ();
                    loadingBar.dismiss ();
                }
            }
        });
    }


    private void sendUserToLoginActivity () {
        Intent loginIntent = new Intent (Register.this, Login.class);
        startActivity (loginIntent);
    }
}