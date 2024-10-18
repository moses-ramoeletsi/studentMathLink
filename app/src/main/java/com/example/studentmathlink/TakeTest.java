package com.example.studentmathlink;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TakeTest extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private TextView questionTextView, questionNumberTextView, timerTextView;
    private RadioGroup optionsGroup;
    private RadioButton option1, option2, option3, option4;
    private Button nextButton;
    private List<TestQuestionBank.Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String userId = "12345";
    private String userName = "John Doe";
    private static final int TOTAL_TIME = 60000;
    private CountDownTimer timer;
    private MediaPlayer correctSound, incorrectSound;
    private ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            userId = currentUser.getUid();
            userName = currentUser.getDisplayName() != null ? currentUser.getDisplayName() : currentUser.getEmail();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
        }

        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        timerTextView = findViewById(R.id.timerTextView);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextButton = findViewById(R.id.nextButton);
        mainLayout = findViewById(R.id.main);

        TestQuestionBank testQuestions = new TestQuestionBank();
        questionList = testQuestions.getAllQuestions();

        correctSound = MediaPlayer.create(this, R.raw.correct);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrect);

        updateQuestionNumber();
        loadQuestion(currentQuestionIndex);
        startTimer(TOTAL_TIME);

        nextButton.setOnClickListener(v -> {
            checkAnswer();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Quit Quiz")
                .setMessage("Are you sure you want to quit the quiz? Your progress will be lost.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (timer != null) {
                        timer.cancel();
                    }
                    super.onBackPressed();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void loadQuestion(int index) {
        TestQuestionBank.Question question = questionList.get(index);
        questionTextView.setText(question.getQuestionText());
        String[] options = question.getOptions();
        option1.setText(options[0]);
        option2.setText(options[1]);
        option3.setText(options[2]);
        option4.setText(options[3]);
        optionsGroup.clearCheck();

        updateQuestionNumber();
    }

    private void checkAnswer() {
        int selectedOptionId = optionsGroup.getCheckedRadioButtonId();

        if (selectedOptionId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedOption = findViewById(selectedOptionId);
        int selectedAnswerIndex = optionsGroup.indexOfChild(selectedOption);

        if (selectedAnswerIndex == questionList.get(currentQuestionIndex).getCorrectAnswerIndex()) {
            score++;
            playCorrectSound();
        } else {
            playIncorrectSound();
            blinkScreen();
        }

        if (currentQuestionIndex < questionList.size() - 1) {
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
        } else {
            String timeText = timerTextView.getText().toString();
            String remainingSecondsStr = timeText.replaceAll("\\D+", "");
            int remainingSeconds = Integer.parseInt(remainingSecondsStr);

            long timeTaken = (TOTAL_TIME / 1000) - remainingSeconds;
            showFinalScore(timeTaken);
        }
    }

    private void playCorrectSound() {
        if (correctSound != null) {
            correctSound.start();
        }
    }

    private void playIncorrectSound() {
        if (incorrectSound != null) {
            incorrectSound.start();
        }
    }

    private void blinkScreen() {
        int colorFrom = Color.TRANSPARENT;
        int colorTo = Color.RED;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250);
        colorAnimation.setRepeatCount(3);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.addUpdateListener(animator -> mainLayout.setBackgroundColor((int) animator.getAnimatedValue()));
        colorAnimation.start();
    }

    private void updateQuestionNumber() {
        String questionNumber = "Question " + (currentQuestionIndex + 1) + "/" + questionList.size();
        questionNumberTextView.setText(questionNumber);
    }

    private void showFinalScore(long timeTaken) {
        String grade = calculateGrade();
        String message = "Your Score: " + score + "/" + questionList.size() + "\nGrade: " + grade;

        new AlertDialog.Builder(this)
                .setTitle("Quiz Complete")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    finish();
                })
                .show();

        if (timer != null) {
            timer.cancel();
        }
        sendScoreToServer(userId, userName, score, timeTaken, currentQuestionIndex + 1, grade);
    }

    private void startTimer(long duration) {
        timer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timerTextView.setText("Time: " + secondsRemaining + "s");
            }

            @Override
            public void onFinish() {
                Toast.makeText(TakeTest.this, "Time is up!", Toast.LENGTH_SHORT).show();
                long timeTaken = TOTAL_TIME / 1000;
                showFinalScore(timeTaken);
            }
        }.start();
    }

    private String calculateGrade() {
        double percentage = (double) score / questionList.size() * 100;
        if (percentage >= 90) {
            return "A";
        } else if (percentage >= 80) {
            return "B";
        } else if (percentage >= 70) {
            return "C";
        } else if (percentage >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    private void sendScoreToServer(String userId, String userName, int score, long timeTaken, int questionsTaken, String grade) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Long currentScore = document.getLong("score");
                    if (currentScore == null || score > currentScore) {
                        updateUserScore(userRef, userId, userName, score, timeTaken, questionsTaken, grade);
                    } else {
                        Toast.makeText(TakeTest.this, "Score not updated. New score is not higher than your current score.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    updateUserScore(userRef, userId, userName, score, timeTaken, questionsTaken, grade);
                }
            } else {
                Toast.makeText(TakeTest.this, "Failed to check current score. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserScore(DocumentReference userRef, String userId, String userName, int score, long timeTaken, int questionsTaken, String grade) {
        Map<String, Object> userScoreData = new HashMap<>();
        userScoreData.put("score", score);
        userScoreData.put("timeTaken", timeTaken);
        userScoreData.put("questionsTaken", questionsTaken);
        userScoreData.put("grade", grade);
        userScoreData.put("lastUpdated", FieldValue.serverTimestamp());

        userRef.set(userScoreData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(TakeTest.this, "Score updated successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(TakeTest.this, "Failed to update score. Try again later.", Toast.LENGTH_SHORT).show();
                });
    }
}
