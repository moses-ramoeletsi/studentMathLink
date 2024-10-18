package com.example.studentmathlink;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class BrainTeasers extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup optionsGroup;
    private RadioButton option1, option2, option3, option4;
    private List<BrainTeaser> brainTeasers;
    private int currentQuestionIndex = 0;
    private MediaPlayer correctSound, incorrectSound;
    private ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain_teasers);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questionTextView = findViewById(R.id.questionTextView);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        mainLayout = findViewById(R.id.main);

        brainTeasers = getBrainTeasers();

        correctSound = MediaPlayer.create(this, R.raw.correct);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrect);

        displayQuestion();

        option1.setOnClickListener(v -> checkAnswer(option1.getText().toString()));
        option2.setOnClickListener(v -> checkAnswer(option2.getText().toString()));
        option3.setOnClickListener(v -> checkAnswer(option3.getText().toString()));
        option4.setOnClickListener(v -> checkAnswer(option4.getText().toString()));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private List<BrainTeaser> getBrainTeasers() {
        List<BrainTeaser> teasers = new ArrayList<>();
        teasers.add(new BrainTeaser("What has keys but can't open locks?", "A map", "A piano", "A book", "A door", "A piano"));
        teasers.add(new BrainTeaser("What comes once in a minute, twice in a moment, but never in a thousand years?", "The letter M", "Time", "A second", "A day", "The letter M"));
        teasers.add(new BrainTeaser("What can travel around the world while staying in one corner?", "A bird", "A stamp", "A phone", "A satellite", "A stamp"));
        teasers.add(new BrainTeaser("I’m tall when I’m young, and I’m short when I’m old. What am I?", "A candle", "A tree", "A clock", "A human", "A candle"));
        teasers.add(new BrainTeaser("If two’s company, and three’s a crowd, what are four and five?", "A team", "Nine", "A party", "A group", "Nine"));
        teasers.add(new BrainTeaser("What gets wetter as it dries?", "A towel", "A sponge", "A plant", "Rain", "A towel"));
        teasers.add(new BrainTeaser("What has a head, a tail, but no body?", "A snake", "A coin", "A ghost", "A fish", "A coin"));
        teasers.add(new BrainTeaser("What belongs to you, but other people use it more than you?", "Your name", "Your phone", "Your time", "Your money", "Your name"));
        teasers.add(new BrainTeaser("What has many teeth, but can’t bite?", "A zipper", "A comb", "A saw", "A fence", "A comb"));
        teasers.add(new BrainTeaser("The more you take, the more you leave behind. What am I?", "Time", "Footsteps", "Memories", "Money", "Footsteps"));
        return teasers;
    }

    private void displayQuestion() {
        if (currentQuestionIndex < brainTeasers.size()) {
            BrainTeaser currentTeaser = brainTeasers.get(currentQuestionIndex);
            questionTextView.setText(currentTeaser.getQuestion());
            option1.setText(currentTeaser.getOption1());
            option2.setText(currentTeaser.getOption2());
            option3.setText(currentTeaser.getOption3());
            option4.setText(currentTeaser.getOption4());
            optionsGroup.clearCheck();

        } else {
            Toast.makeText(this, "You've completed all brain teasers!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void checkAnswer(String selectedAnswer) {
        BrainTeaser currentTeaser = brainTeasers.get(currentQuestionIndex);
        if (selectedAnswer.equals(currentTeaser.getCorrectAnswer())) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            playCorrectSound();
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
            playIncorrectSound();
            blinkScreen();
        }
        currentQuestionIndex++;
        displayQuestion();
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