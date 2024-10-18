package com.example.studentmathlink;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Profile extends AppCompatActivity {

    private TextView usernameTextView, emailTextView, scoreTextView, testTimeTakenView, testDateTakenView, gradeTextView, addressTextView, phoneNumberTextView;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        addressTextView = findViewById(R.id.addressTextView);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        testDateTakenView = findViewById(R.id.testDateTakenView);
        testTimeTakenView = findViewById(R.id.testTimeTakenView);
        gradeTextView = findViewById(R.id.gradeTextView);
        logoutButton = findViewById(R.id.logoutButton);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        loadUserProfile();
        setupLogoutButton();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupLogoutButton() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performLogout();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void performLogout() {
        mAuth.signOut();
        Toast.makeText(Profile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Profile.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Please log in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Profile.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private void loadUserProfile() {
        if (currentUser != null) {
            String userId = currentUser.getUid();
            firestore.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String username = documentSnapshot.getString("name");
                            String email = documentSnapshot.getString("email");
                            Long score = documentSnapshot.getLong("score");
                            Long testTimeTaken = documentSnapshot.getLong("lastTimeTaken");
                            com.google.firebase.Timestamp timestamp = documentSnapshot.getTimestamp("lastUpdated");
                            String grade = documentSnapshot.getString("lastGrade");
                            String address = documentSnapshot.getString("address");
                            String phoneNumber = documentSnapshot.getString("phoneNumber");

                            if (timestamp != null) {
                                Date date = timestamp.toDate();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());

                                String formattedDate = dateFormat.format(date);

                                testDateTakenView.setText("Test Date: " + formattedDate);
                            } else {
                                testDateTakenView.setText("Test Date: Not available");
                            }

                            usernameTextView.setText("Username: " + username);
                            emailTextView.setText("Email: " + email);
                            scoreTextView.setText("Score: " + (score != null ? score : 0));
                            gradeTextView.setText("Grade: " + (grade != null ? grade : "Not provided"));
                            testTimeTakenView.setText("Test Time Taken: " + (testTimeTaken != null ? testTimeTaken : 0));
                            addressTextView.setText("Address: " + (address != null ? address : "Not provided"));
                            phoneNumberTextView.setText("Phone: " + (phoneNumber != null ? phoneNumber : "Not provided"));
                        }
                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                    });
        }
    }
}