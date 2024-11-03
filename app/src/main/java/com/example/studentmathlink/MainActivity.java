package com.example.studentmathlink;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
        practiceCard = findViewById(R.id.practiceCard);
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

        FloatingActionButton fabRating = findViewById(R.id.fabRating);
        fabRating.setOnClickListener(v -> openRatingDialog());


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

    private void openRatingDialog() {
        // Inflate the dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_feedback, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Initialize dialog components
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        EditText feedbackText = dialogView.findViewById(R.id.feedbackText);
        Button submitFeedbackBtn = dialogView.findViewById(R.id.submitFeedbackBtn);

        // Set onClick for submit button
        submitFeedbackBtn.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String feedback = feedbackText.getText().toString();

            if (currentUser != null) {
                String userId = currentUser.getUid();

                // Query to check if the user's feedback already exists
                firestore.collection("feedback")
                        .whereEqualTo("userId", userId)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // If feedback exists, update the existing document
                                String documentId = queryDocumentSnapshots.getDocuments().get(0).getId();
                                firestore.collection("feedback").document(documentId)
                                        .update("rating", rating,
                                                "feedback", feedback,
                                                "timestamp", FieldValue.serverTimestamp())
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(MainActivity.this, "Feedback updated successfully!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(MainActivity.this, "Failed to update feedback. Try again.", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                // If no feedback exists, add a new document
                                Map<String, Object> feedbackData = new HashMap<>();
                                feedbackData.put("userId", userId);
                                feedbackData.put("rating", rating);
                                feedbackData.put("feedback", feedback);
                                feedbackData.put("timestamp", FieldValue.serverTimestamp());

                                firestore.collection("feedback").add(feedbackData)
                                        .addOnSuccessListener(documentReference -> {
                                            Toast.makeText(MainActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(MainActivity.this, "Failed to submit feedback. Try again.", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(MainActivity.this, "Error checking feedback. Try again.", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(MainActivity.this, "Please log in to submit feedback.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


//    private void openRatingDialog() {
//        // Inflate the dialog layout
//        View dialogView = getLayoutInflater().inflate(R.layout.dialog_feedback, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(dialogView);
//        AlertDialog dialog = builder.create();
//
//        // Initialize dialog components
//        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
//        EditText feedbackText = dialogView.findViewById(R.id.feedbackText);
//        Button submitFeedbackBtn = dialogView.findViewById(R.id.submitFeedbackBtn);
//
//        // Set onClick for submit button
//        submitFeedbackBtn.setOnClickListener(v -> {
//            float rating = ratingBar.getRating();
//            String feedback = feedbackText.getText().toString();
//
//            if (currentUser != null) {
//                String userId = currentUser.getUid();
//                Map<String, Object> feedbackData = new HashMap<>();
//                feedbackData.put("userId", userId);
//                feedbackData.put("rating", rating);
//                feedbackData.put("feedback", feedback);
//                feedbackData.put("timestamp", FieldValue.serverTimestamp());
//
//                // Save feedback to Firestore
//                firestore.collection("feedback").add(feedbackData)
//                        .addOnSuccessListener(documentReference -> {
//                            Toast.makeText(MainActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//                        })
//                        .addOnFailureListener(e -> {
//                            Toast.makeText(MainActivity.this, "Failed to submit feedback. Try again.", Toast.LENGTH_SHORT).show();
//                        });
//            } else {
//                Toast.makeText(MainActivity.this, "Please log in to submit feedback.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        dialog.show();
//    }

}