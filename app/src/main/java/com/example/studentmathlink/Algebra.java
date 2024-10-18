package com.example.studentmathlink;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Algebra extends AppCompatActivity {

    private TextView algebraContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algebra);
        algebraContent = findViewById(R.id.algebraContent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String content = getAlgebraContent();
        algebraContent.setText(content);

    }
    private String getAlgebraContent() {
        return "Basic Algebra Rules\n" +
                "\n1. The Symmetry rule\n" +
                "2. The Commutative rules\n" +
                "3. The Inverse of adding\n" +
                "4. Two rules for equation\n" +
                "\nBasic Algebra Operations\n" +
                "Addition: x + y\n" +
                "Subtraction: x – y\n" +
                "Multiplication: xy\n" +
                "Division: x/y\n" +
                "\nBasic Algebra Formulas\n" +
                "a² – b² = (a – b)(a + b)\n" +
                "(a+b)² = a² + 2ab + b²\n" +
                "a² + b² = (a – b)² + 2ab\n" +
                "(a – b)² = a² – 2ab + b²\n" +
                "(a + b + c)² = a² + b² + c² + 2ab + 2ac + 2bc\n" +
                "\nBasic Algebra Examples\n" +
                "Q1: Find y, when y + 15 = 30\n" +
                "Solution: y = 30 – 15\n" +
                "Answer: y = 15\n" +
                "\nQ2: Find x, when 9x = 63\n" +
                "Answer: x = 63/9 = 7\n" +
                "\nPractice Problems\n" +
                "1. Solve x + 12 = 6\n" +
                "2. Find the value of z, if 23z + 3 = 10\n" +
                "3. Solve 2y – 8 = 5y\n";
    }
}