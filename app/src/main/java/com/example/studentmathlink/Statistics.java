package com.example.studentmathlink;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Statistics extends AppCompatActivity {
    private TextView statisticsContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        statisticsContent = findViewById(R.id.statisticsContent);

        String content = getStatisticsAndProbabilityContent();
        statisticsContent.setText(content);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private String getStatisticsAndProbabilityContent() {
        return "What is Probability?\n" +
                "Probability denotes the possibility of the outcome of any random event. For example, when we flip a coin, the probability of getting heads is 1/2.\n" +
                "\nWhat is Statistics?\n" +
                "Statistics is the study of the collection, analysis, interpretation, presentation, and organization of data. It is used in various fields such as sociology, economics, etc.\n" +
                "\nTerms Used in Probability and Statistics:\n" +
                "1. Random Experiment\n" +
                "2. Sample Space\n" +
                "3. Random Variables\n" +
                "4. Independent Event\n" +
                "5. Mean\n" +
                "6. Expected Value\n" +
                "7. Variance\n" +
                "\nRandom Experiment: An experiment whose result cannot be predicted is called a random experiment.\n" +
                "Sample Space: The set of all possible outcomes of a random experiment.\n" +
                "Random Variables: Variables that denote possible outcomes of a random experiment.\n" +
                "\nProbability Formulas:\n" +
                "P(E) = Number of Favourable Outcomes / Number of Total Outcomes\n" +
                "P(E) = n(E) / n(S)\n" +
                "\nList of Probability Topics:\n" +
                "1. Addition Rule of Probability\n" +
                "2. Bayes Theorem\n" +
                "3. Compound Probability\n" +
                "4. Independent Events\n" +
                "5. Conditional Probability\n" +
                "\nList of Statistical Topics:\n" +
                "1. Central Tendency\n" +
                "2. Histogram\n" +
                "3. Mean, Median, Mode\n" +
                "4. Scatter Plots\n" +
                "5. Variance, Standard Deviation\n";
    }
}