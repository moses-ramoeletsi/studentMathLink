package com.example.studentmathlink;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TheoryActivity extends AppCompatActivity {

    private CardView algebraCard, geometryCard, statsAndProbCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);

        algebraCard = findViewById(R.id.algebraCard);
        geometryCard = findViewById(R.id.geometryCard);
        statsAndProbCard = findViewById(R.id.statsAndProbCard);

        setupAlgebraCard();
        setupGeometryCard();
        setupStatsAndProbCard();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupAlgebraCard() {
        algebraCard.setOnClickListener(v -> {
            Intent intent = new Intent(TheoryActivity.this, Algebra.class);
            startActivity(intent);
        });
    }

    private void setupGeometryCard() {
        geometryCard.setOnClickListener(v -> {
            Intent intent = new Intent(TheoryActivity.this, Geometry.class);
            startActivity(intent);
        });
    }

    private void setupStatsAndProbCard() {
        statsAndProbCard.setOnClickListener(v -> {
            Intent intent = new Intent(TheoryActivity.this, Statistics.class);
            startActivity(intent);
        });
    }
}