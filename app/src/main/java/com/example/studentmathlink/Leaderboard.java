package com.example.studentmathlink;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard extends AppCompatActivity {

    private RecyclerView leaderboardRecyclerView;
    private List<UserScore> userScores;
    private LeaderboardAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView);
        leaderboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userScores = new ArrayList<>();
        adapter = new LeaderboardAdapter(this, userScores);
        leaderboardRecyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        fetchLeaderboardData();

        adapter.setOnItemClickListener(this::showUserDetails);
    }

    private void fetchLeaderboardData() {
        db.collection("users")
                .orderBy("score", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    userScores.clear();
                    int position = 1;
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String userId = document.getString("userId");
                        String userName = document.getString("name");
                        long score = document.getLong("score");
                        long timeTaken = document.getLong("lastTimeTaken");

                        UserScore userScore = new UserScore(userId, userName, score, timeTaken, position);
                        userScores.add(userScore);

                        if (currentUser != null && currentUser.getUid().equals(userId)) {
                            userScore.setCurrentUser(true);
                        }

                        position++;
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Leaderboard.this, "Error fetching leaderboard data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void showUserDetails(UserScore userScore) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("User Details")
                .setMessage("Name: " + userScore.getUserName() +
                        "\nScore: " + userScore.getScore() +
                        "\nTime Taken: " + userScore.getTimeTaken() + " seconds" +
                        "\nPosition: " + userScore.getPosition())
                .setPositiveButton("OK", null)
                .show();
    }
}