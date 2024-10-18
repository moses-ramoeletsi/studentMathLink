package com.example.studentmathlink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private TextView usernameTextView;
    private CardView theoryCard, leaderboardCard, practiceCard, testCard, studyPlanCard, brainTeaserCard;

    private ImageView personIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        usernameTextView = findViewById(R.id.usernameTextView);
        theoryCard = findViewById(R.id.theoryCard);
        leaderboardCard = findViewById(R.id.leaderboardCard);
        personIcon = findViewById(R.id.personIcon);
        practiceCard= findViewById(R.id.practiceCard);
        testCard = findViewById(R.id.testCard);
        studyPlanCard = findViewById(R.id.studyPlanCard);
        brainTeaserCard = findViewById(R.id.brainTeaserCard);

        displayUsername();

        setuptheoryCard();
        setupPracticeButton();
        setupTestButton();
        setupStudyPlanButton();
        setupLeaderboardButton();
        setbrainTeaserCard();
        setUserProfile();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void setUserProfile() {
        personIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
        });
    }

    private void setbrainTeaserCard() {
        brainTeaserCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BrainTeasers.class);
            startActivity(intent);
        });
    }

    private void setupLeaderboardButton() {
        leaderboardCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Leaderboard.class);
            startActivity(intent);
        });
    }

    private void setupStudyPlanButton() {
        studyPlanCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudyPlan.class);
            startActivity(intent);
        });
    }

    private void setupTestButton() {
        testCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TakeTest.class);
            startActivity(intent);
        });
    }

    private void setupPracticeButton() {
        practiceCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PracticePage.class);
            startActivity(intent);
        });
    }

    private void setuptheoryCard() {
        theoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TheoryActivity.class);
            startActivity(intent);
        });
    }

    private void displayUsername() {
        if (currentUser != null) {
            String email = currentUser.getEmail();
            String username = email != null ? email.split("@")[0] : "User";
            usernameTextView.setText("Welcome, " + username + "!");
        } else {
            usernameTextView.setText("Welcome, Guest!");
        }
    }
}