package com.example.studentmathlink;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class GeometryPracticeQuestions extends AppCompatActivity {
    private TextView questionTextView ,questionNumberTextView,explanationTextView;
    private RadioGroup optionsGroup;
    private RadioButton option1, option2, option3, option4;
    private Button nextButton;
    private List<QuestionBank.Question> questionList;
    private int currentQuestionIndex = 0;
    private MediaPlayer correctSound, incorrectSound;
    private int score = 0;
    private ConstraintLayout mainLayout;

    private ScrollView scrollView;
    private LinearLayout resultLayout;
    private List<QuestionBank.Question> incorrectQuestions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometry_practice_questions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextButton = findViewById(R.id.nextButton);
        mainLayout = findViewById(R.id.main);
        scrollView = findViewById(R.id.scrollView);
        resultLayout = findViewById(R.id.resultLayout);
        explanationTextView = findViewById(R.id.explanationTextView);

        QuestionBank questionBank = new QuestionBank();
        questionList = questionBank.getGeometryQuestions();

        correctSound = MediaPlayer.create(this, R.raw.correct);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrect);

        loadQuestion(currentQuestionIndex);

        updateQuestionNumber();

        nextButton.setOnClickListener(v -> {
            checkAnswer();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadQuestion(int index) {
        QuestionBank.Question question = questionList.get(index);
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
        QuestionBank.Question currentQuestion = questionList.get(currentQuestionIndex);

        if (selectedAnswerIndex == currentQuestion.getCorrectAnswerIndex()) {
            score++;
            playCorrectSound();
        } else {
            playIncorrectSound();
            blinkScreen();
            incorrectQuestions.add(currentQuestion);
        }

        if (currentQuestionIndex < questionList.size() - 1) {
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
        } else {
            showResults();
        }
    }
    private void updateQuestionNumber() {
        String questionNumber = "Question " + (currentQuestionIndex + 1) + "/" + questionList.size();
        questionNumberTextView.setText(questionNumber);
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
    private void showResults() {
         scrollView.setVisibility(ScrollView.GONE);
         findViewById(R.id.resultScrollView).setVisibility(View.VISIBLE);
         nextButton.setText("Show Results");
         nextButton.setOnClickListener(v -> showIncorrectQuestions());

         TextView scoreTextView = new TextView(this);
         scoreTextView.setText("Quiz Complete! Your Score: " + score + "/" + questionList.size());
         scoreTextView.setTextSize(20);
         scoreTextView.setPadding(0, 0, 0, 16);
         resultLayout.addView(scoreTextView);

    }

    private void showIncorrectQuestions() {
        resultLayout.removeAllViews();
        if (incorrectQuestions.isEmpty()) {
            TextView perfectScoreTextView = new TextView(this);
            perfectScoreTextView.setText("Congratulations! You got a perfect score!");
            perfectScoreTextView.setTextSize(20);
            resultLayout.addView(perfectScoreTextView);
            nextButton.setText("Finish");
            nextButton.setOnClickListener(v -> finish());
        }else {
            TextView headerTextView = new TextView(this);
            headerTextView.setText("Incorrect Questions:");
            headerTextView.setTextSize(22);
            headerTextView.setPadding(0, 0, 0, 16);
            resultLayout.addView(headerTextView);

            for (int i = 0; i < incorrectQuestions.size(); i++){
                QuestionBank.Question question = incorrectQuestions.get(i);

                TextView questionNumberTextView = new TextView(this);
                questionNumberTextView.setText("Question " + (i + 1) + ":");
                questionNumberTextView.setTextSize(20);
                questionNumberTextView.setPadding(0, 16, 0, 8);
                questionNumberTextView.setTextColor(Color.parseColor("#4CAF50"));
                resultLayout.addView(questionNumberTextView);

                TextView questionTextView = new TextView(this);
                questionTextView.setText(question.getQuestionText());
                questionTextView.setTextSize(18);
                questionTextView.setPadding(0, 0, 0, 8);
                resultLayout.addView(questionTextView);

                TextView correctAnswerTextView = new TextView(this);
                correctAnswerTextView.setText("Correct Answer: " + question.getOptions()[question.getCorrectAnswerIndex()]);
                correctAnswerTextView.setTextSize(16);
                correctAnswerTextView.setPadding(0, 0, 0, 8);
                resultLayout.addView(correctAnswerTextView);

                TextView explanationTextView = new TextView(this);
                explanationTextView.setText("Explanation:\n" + question.getExplanation());
                explanationTextView.setTextSize(16);
                explanationTextView.setPadding(0, 0, 0, 24);
                resultLayout.addView(explanationTextView);

                if (i < incorrectQuestions.size() - 1) {
                    View divider = new View(this);
                    divider.setBackgroundColor(Color.LTGRAY);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            2
                    );
                    params.setMargins(0, 0, 0, 16);
                    divider.setLayoutParams(params);
                    resultLayout.addView(divider);
                }
            }
            nextButton.setText("Finish");
            nextButton.setOnClickListener(v -> finish());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (correctSound != null) {
            correctSound.release();
        }
        if (incorrectSound != null) {
            incorrectSound.release();
        }
    }
}