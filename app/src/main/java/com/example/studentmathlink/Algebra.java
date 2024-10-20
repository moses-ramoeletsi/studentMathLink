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
                "What is Algebra?Algebra is a branch of mathematics that deals with symbols and the rules for manipulating those symbols. These symbols (often letters) represent numbers in equations and formulas. Algebra helps us solve problems where we don't know the exact values of numbers, so we use letters (like x or y) to represent those unknowns.\n" +
                "Commutative Rules:The commutative property states that the order in which two numbers are added or multiplied does not change the result.\n" +
                "Addition: a+b=b+a\n" +
                "Multiplication: a×b=b×a\n" +
                "For example:\n" +
                "2+3=3+2=5\n" +
                "4×5=5×4=20\n" +
                "Inverse of Adding and Subtracting:The inverse operation of addition is subtraction, and the inverse of subtraction is addition. These operations \"undo\" each other.\n" +
                "For addition, the inverse is subtraction:If x+5=10, then subtracting 5 from both sides gives x=5.\n" +
                "For subtraction, the inverse is addition:If x−3=7, then adding 3 to both sides gives x=10.\n" +
                "Two Rules for Solving Equations:\n" +
                "1.Balance the Equation:Whatever you do to one side of the equation, you must do to the other side. This keeps the equation balanced. For example:\n" +
                "oIf x+4=10, subtract 4 from both sides to get x=6.\n" +
                "2.Isolate the Variable:The goal of solving an equation is to get the unknown variable (like x) alone on one side of the equation. This often involves performing inverse operations. For example:\n" +
                "oTo solve 3x=12, divide both sides by 3 to get x=4.\n" +
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
                "Example 1: Solve the equation 5x – 6 = 3x – 8.\n" +
                "Solution:\n" +
                "Given,\n" +
                "5x – 6 = 3x – 8\n" +
                "Adding 6 on both sides,\n" +
                "5x – 6 + 6 = 3x – 8 + 6\n" +
                "5x = 3x – 2\n" +
                "Subtract 3x from both sides,\n" +
                "5x – 3x = 3x – 2 – 3x\n" +
                "2x = -2\n" +
                "Dividing both sides of the equation by 2,\n" +
                "2x/2 = -2/2\n" +
                "x = -1\n" +
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