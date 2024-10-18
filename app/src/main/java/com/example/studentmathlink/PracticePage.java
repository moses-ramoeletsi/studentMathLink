package com.example.studentmathlink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PracticePage extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private TextView usernameTextView;
    private CardView algebraPracticeCard, geometryPracticeCard, statisticsPracticeCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_page);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameTextView = findViewById(R.id.usernameTextView);

        algebraPracticeCard = findViewById(R.id.algebraCard);
        geometryPracticeCard = findViewById(R.id. geometryCard);
        statisticsPracticeCard = findViewById(R.id.statsAndProbCard);

        displayUsername();
        setupAlgebraPracticeCard();
        setupGeometryPracticeCard();
        setupStatisticPracticeCard();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void setupAlgebraPracticeCard() {
        algebraPracticeCard.setOnClickListener(v -> {
            Intent intent = new Intent(PracticePage.this, AlgebraPracticeQuestions.class);
            startActivity(intent);
        });
    }

    private void setupGeometryPracticeCard() {
        geometryPracticeCard.setOnClickListener(v -> {
            Intent intent = new Intent(PracticePage.this, GeometryPracticeQuestions.class);
            startActivity(intent);
        });
    }

    private void setupStatisticPracticeCard() {
        statisticsPracticeCard.setOnClickListener(v -> {
            Intent intent = new Intent(PracticePage.this, StatisticsPracticeQuestions.class);
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