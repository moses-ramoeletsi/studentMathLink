package com.example.studentmathlink;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    private List<Question> algebraQuestions;
    private List<Question> geometryQuestions;
    private List<Question> statisticsQuestions;

    public QuestionBank() {
        algebraQuestions = new ArrayList<>();
        geometryQuestions = new ArrayList<>();
        statisticsQuestions = new ArrayList<>();

        loadAlgebraQuestions();
        loadGeometryQuestions();
        loadStatisticsQuestions();
    }

    public static class Question {
        private String questionText;
        private String[] options;
        private int correctAnswerIndex;

        private String explanation;

        public Question(String questionText, String[] options, int correctAnswerIndex, String explanation) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
            this.explanation = explanation;
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
        public String getExplanation() {
            return explanation;
        }
    }



    private void loadAlgebraQuestions() {
        algebraQuestions.add(new Question(
                "Solve x + 12 = 6",
                new String[]{"x = -6", "x = 18", "x = 0", "x = 12"},
                0,
                "To solve for x, subtract 12 from both sides of the equation:\n" +
                        "x + 12 = 6\n" +
                        "x + 12 - 12 = 6 - 12\n" +
                        "x = -6"
        ));

        algebraQuestions.add(new Question(
                "Find the value of z, if 23z + 3 = 10",
                new String[]{"z = 7", "z = 3", "z = 0.3", "z = 0.7"},
                3,
                "To solve for z:\n" +
                        "1. Subtract 3 from both sides: 23z = 7\n" +
                        "2. Divide both sides by 23: z = 7/23\n" +
                        "3. Simplify: z ≈ 0.3043\n" +
                        "The closest answer is 0.3, which rounds to 0.3."
        ));

        algebraQuestions.add(new Question(
                "Solve 2y – 8 = 5y",
                new String[]{"y = -8", "y = 0", "y = -4", "y = 4"},
                2,
                "To solve for y:\n" +
                        "1. Subtract 2y from both sides: -8 = 3y\n" +
                        "2. Divide both sides by 3: -8/3 = y\n" +
                        "3. Simplify: y = -2.66...\n" +
                        "The closest answer is -4."
        ));

        algebraQuestions.add(new Question(
                "Simplify (a+b)²",
                new String[]{"a² + b²", "a² + 2ab + b²", "a² - 2ab + b²", "None"},
                1,
                "The formula for (a+b)² is:\n" +
                        "(a+b)² = a² + 2ab + b²\n" +
                        "This is known as the square of sum formula. It's derived from multiplying (a+b) by itself."
        ));

        algebraQuestions.add(new Question(
                "Factorize a² – b²",
                new String[]{"(a – b)(a + b)", "(a + b)²", "(a – b)²", "None"},
                0,
                "The formula a² – b² is a special case called the difference of squares.\n" +
                        "It always factorizes to (a – b)(a + b).\n" +
                        "You can verify this by multiplying (a – b)(a + b):\n" +
                        "a² + ab - ab - b² = a² - b²"
        ));
    }


    private void loadGeometryQuestions() {
        geometryQuestions.add(new Question(
                "What is the area of a triangle with base 5 and height 3?",
                new String[]{"7.5", "10", "15", "5"},
                0,
                "To calculate the area of a triangle:\n" +
                        "1. Use the formula: Area = (1/2) * base * height\n" +
                        "2. Substitute the values: Area = (1/2) * 5 * 3\n" +
                        "3. Calculate: Area = 7.5 square units"
        ));

        geometryQuestions.add(new Question(
                "What is the formula for the circumference of a circle?",
                new String[]{"2πr", "πr²", "2r", "r²"},
                0,
                "The formula for the circumference of a circle is 2πr, where:\n" +
                        "- π (pi) is approximately 3.14159\n" +
                        "- r is the radius of the circle\n" +
                        "This formula represents the distance around the circle."
        ));

        geometryQuestions.add(new Question(
                "A polygon has 5 sides. What is it called?",
                new String[]{"Hexagon", "Quadrilateral", "Pentagon", "Octagon"},
                2,
                "A polygon with 5 sides is called a Pentagon:\n" +
                        "- Penta- means 'five' in Greek\n" +
                        "- -gon means 'angle' or 'corner'\n" +
                        "Other examples: Hexagon (6 sides), Quadrilateral (4 sides), Octagon (8 sides)"
        ));

        geometryQuestions.add(new Question(
                "What is the sum of angles in a triangle?",
                new String[]{"180°", "90°", "360°", "270°"},
                0,
                "The sum of angles in a triangle is always 180°:\n" +
                        "- This is true for all triangles: equilateral, isosceles, or scalene\n" +
                        "- You can prove this by tearing the corners of a paper triangle and arranging them to form a straight line (180°)"
        ));

        geometryQuestions.add(new Question(
                "A circle is a set of points equidistant from which point?",
                new String[]{"Edge", "Center", "Radius", "Diameter"},
                1,
                "A circle is defined as a set of points equidistant from the center:\n" +
                        "- The center is a fixed point inside the circle\n" +
                        "- The distance from the center to any point on the circle is called the radius\n" +
                        "- This property ensures that a circle is perfectly round"
        ));
    }


    private void loadStatisticsQuestions() {
        statisticsQuestions.add(new Question(
                "What is the mean of the data set {2, 4, 6, 8, 10}?",
                new String[]{"6", "5", "7", "8"},
                0,
                "To calculate the mean:\n" +
                        "1. Sum all numbers: 2 + 4 + 6 + 8 + 10 = 30\n" +
                        "2. Count the total numbers: There are 5 numbers\n" +
                        "3. Divide the sum by the count: 30 ÷ 5 = 6\n" +
                        "Therefore, the mean is 6."
        ));

        statisticsQuestions.add(new Question(
                "What is the mode of the data set {1, 2, 2, 3, 4}?",
                new String[]{"1", "2", "3", "4"},
                1,
                "The mode is the value that appears most frequently in a data set:\n" +
                        "- In {1, 2, 2, 3, 4}, the number 2 appears twice\n" +
                        "- All other numbers appear only once\n" +
                        "Therefore, 2 is the mode of this data set."
        ));

        statisticsQuestions.add(new Question(
                "Which of the following is NOT a measure of central tendency?",
                new String[]{"Mean", "Median", "Mode", "Variance"},
                3,
                "Measures of central tendency describe the center or typical value of a dataset:\n" +
                        "- Mean: the average of all values\n" +
                        "- Median: the middle value when data is ordered\n" +
                        "- Mode: the most frequent value\n" +
                        "Variance is a measure of spread, not central tendency."
        ));

        statisticsQuestions.add(new Question(
                "What is the probability of getting heads in a coin flip?",
                new String[]{"1/4", "1/2", "1", "0"},
                1,
                "In a fair coin flip:\n" +
                        "- There are two possible outcomes: heads or tails\n" +
                        "- Each outcome has an equal chance of occurring\n" +
                        "- Probability = (favorable outcomes) / (total outcomes) = 1 / 2\n" +
                        "Therefore, the probability of getting heads is 1/2 or 50%."
        ));

        statisticsQuestions.add(new Question(
                "What does a scatter plot display?",
                new String[]{"Correlation", "Central Tendency", "Mean", "Variance"},
                0,
                "A scatter plot displays correlation between two variables:\n" +
                        "- It shows data points on a coordinate plane\n" +
                        "- Each point represents values of two variables (x and y)\n" +
                        "- The pattern of points can reveal relationships or trends between the variables\n" +
                        "Scatter plots are useful for identifying positive, negative, or no correlation."
        ));
    }


    public List<Question> getAlgebraQuestions() {
        return new ArrayList<>(algebraQuestions);
    }


    public List<Question> getGeometryQuestions() {
        return new ArrayList<>(geometryQuestions);
    }


    public List<Question> getStatisticsQuestions() {
        return new ArrayList<>(statisticsQuestions);
    }
}
