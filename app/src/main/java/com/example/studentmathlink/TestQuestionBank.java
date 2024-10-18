package com.example.studentmathlink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestQuestionBank {
    private List<Question> questions;

    public TestQuestionBank() {
        questions = new ArrayList<>();
        loadQuestions();
    }

    public static class Question {
        private String questionText;
        private String[] options;
        private int correctAnswerIndex;

        public Question(String questionText, String[] options, int correctAnswerIndex) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        public String getQuestionText() {
            return questionText;
        }

        public String[] getOptions() {
            return options;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }
    }

    private void loadQuestions() {

        questions.add(new Question("Solve 2x + 5 = 15", new String[]{"x = 5", "x = 2", "x = 4", "x = 10"}, 2));
        questions.add(new Question("Simplify 3(x + 4) - 2x", new String[]{"x + 12", "x + 4", "3x + 4", "2x + 4"}, 1));
        questions.add(new Question("Find the value of x, if 4x + 12 = 0", new String[]{"x = -3", "x = 3", "x = -4", "x = 4"}, 0));
        questions.add(new Question("Expand (x + 3)(x - 2)", new String[]{"x² + x - 6", "x² - x - 6", "x² + x + 6", "x² - x + 6"}, 1));
        questions.add(new Question("What is the solution to the equation 5x = 25?", new String[]{"x = 1", "x = 5", "x = 25", "x = 0"}, 1));

        questions.add(new Question("What is the area of a rectangle with length 8 and width 4?", new String[]{"12", "16", "32", "24"}, 2));
        questions.add(new Question("How many sides does a hexagon have?", new String[]{"4", "6", "5", "8"}, 1));
        questions.add(new Question("The angles of a triangle are 45°, 45°, and 90°. What type of triangle is it?", new String[]{"Right Triangle", "Equilateral Triangle", "Isosceles Triangle", "Scalene Triangle"}, 0));
        questions.add(new Question("What is the perimeter of a square with a side length of 5?", new String[]{"20", "25", "10", "15"}, 0));
        questions.add(new Question("What is the formula for the area of a circle?", new String[]{"πr²", "2πr", "r²", "πr"}, 0));


        questions.add(new Question("What is the median of the data set {3, 5, 7, 9, 11}?", new String[]{"7", "5", "9", "6"}, 0));
        questions.add(new Question("What is the range of the data set {2, 4, 6, 8, 10}?", new String[]{"4", "8", "6", "5"}, 1));
        questions.add(new Question("What is the probability of rolling a 3 on a standard six-sided die?", new String[]{"1/3", "1/2", "1/6", "1/4"}, 2));
        questions.add(new Question("Which of the following is a measure of variability?", new String[]{"Mean", "Mode", "Range", "Median"}, 2));
        questions.add(new Question("What does the standard deviation measure?", new String[]{"Central Tendency", "Dispersion", "Probability", "Frequency"}, 1));
    }


    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions);
    }

    public List<List<Question>> splitQuestions(int splitSize) {
        List<Question> shuffledQuestions = new ArrayList<>(questions);
        Collections.shuffle(shuffledQuestions);

        List<Question> firstSet = new ArrayList<>();
        List<Question> secondSet = new ArrayList<>();

        for (int i = 0; i < splitSize && i < shuffledQuestions.size(); i++) {
            firstSet.add(shuffledQuestions.get(i));
        }

        for (int i = splitSize; i < shuffledQuestions.size(); i++) {
            secondSet.add(shuffledQuestions.get(i));
        }

        List<List<Question>> result = new ArrayList<>();
        result.add(firstSet);
        result.add(secondSet);
        return result;
    }
}
