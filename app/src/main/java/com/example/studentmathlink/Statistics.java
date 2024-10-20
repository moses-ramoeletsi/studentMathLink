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
                "Random Variables\n" +
                "The variables which denote the possible outcomes of a random experiment are called random variables. They are of two types:\n" +
                "1.Discrete Random Variables\n" +
                "2.Continuous Random Variables\n" +
                "Discrete random variables take only those distinct values which are countable. Whereas continuous random variables could take an infinite number of possible values.\n" +
                "Independent Event\n" +
                "When the probability of occurrence of one event has no impact on the probability of another event, then both the events are termed as independent of each other. For example, if you flip a coin and at the same time you throw a dice, the probability of getting a 'head' is independent of the probability of getting a 6 in dice.\n" +
                "Mean\n" +
                "Mean of a random variable is the average of the random values of the possible outcomes of a random experiment. In simple terms, it is the expectation of the possible outcomes of the random experiment, repeated again and again or n number of times. It is also called the expectation of a random variable.\n" +
                "Expected Value\n" +
                "Expected value is the mean of a random variable. It is the assumed value which is considered for a random experiment. It is also called expectation, mathematical expectation or first moment. For example, if we roll a dice having six faces, then the expected value will be the average value of all the possible outcomes, i.e. 3.5.\n" +
                "Variance\n" +
                "Basically, the variance tells us how the values of the random variable are spread around the mean value. It specifies the distribution of the sample space across the mean.\n" +
                "Probability Formulas: For two events A and B:\n" +
                "Probability Range\n" +
                "Probability of an event ranges from 0 to 1 i.e. 0 ≤ P(A) ≤ 1\n" +
                "Rule of Complementary Events\n" +
                "P(A') + P(A) = 1\n" +
                "Rule of Addition\n" +
                "P(A∪B) = P(A) + P(B) – P(A∩B)\n" +
                "Mutually Exclusive Events\n" +
                "P(A∪B) = P(A) + P(B)\n" +
                "Independent Events\n" +
                "P(A∩B) = P(A)P(B)\n" +
                "Disjoint Events\n" +
                "P(A∩B) = 0\n" +
                "Conditional Probability\n" +
                "P(A|B) = P(A∩B)/P(B)\n" +
                "Bayes Formula\n" +
                "P(A|B) = P(B|A) P(A)/P(B)\n" +
                "Solved Examples\n" +
                "Here are some examples based on the concepts of statistics and probability to understand better. Students can practice more questions based on these solved examples to excel in the topic. Also, make use of the formulas given in this article in the above section to solve problems based on them.\n" +
                "Example 1: Find the mean and mode of the following data: 2, 3, 5, 6, 10, 6, 12, 6, 3, 4.\n" +
                "Solution:\n" +
                "Total Count: 10\n" +
                "Sum of all the numbers: 2+3+5+6+10+6+12+6+3+7=60\n" +
                "Mean = (sum of all the numbers)/(Total number of items)\n" +
                "Mean = 60/10 = 6\n" +
                "Again, Number 6 is occurring for 3 times, therefore Mode = 6. Answer\n" +
                "Example 2: A bucket contains 5 blue, 4 green and 5 red balls. Sudheer is asked to pick 2 balls randomly from the bucket without replacement and then one more ball is to be picked. What is the probability he picked 2 green balls and 1 blue ball?\n" +
                "Solution: Total number of balls = 14\n" +
                "Probability of drawing\n" +
                "1 green ball = 4/14\n" +
                "another green ball = 3/13\n" +
                "1 blue ball = 5/12\n" +
                "Probability of picking 2 green balls and 1 blue ball = 4/14 * 3/13 * 5/12 = 5/182.\n" +
                "Example 3: What is the probability that Ram will choose a marble at random and that it is not black if the bowl contains 3 red, 2 black and 5 green marbles.\n" +
                "Solution: Total number of marble = 10\n" +
                "Red and Green marbles = 8\n" +
                "Find the number of marbles that are not black and divide by the total number of marbles.\n" +
                "So P(not black) = (number of red or green marbles)/(total number of marbles)\n" +
                "= 8 /10\n" +
                "= 4/5\n" +
                "Example 4: Find the mean of the following data:\n" +
                "55, 36, 95, 73, 60, 42, 25, 78, 75, 62\n" +
                "Solution: Given,\n" +
                "55 36 95 73 60 42 25 78 75 62\n" +
                "Sum of observations = 55 + 36 + 95 + 73 + 60 + 42 + 25 + 78 + 75 + 62 = 601\n" +
                "Number of observations = 10\n" +
                "Mean = 601/10 = 60.1\n" +
                "Example 5: Find the median and mode of the following marks (out of 10) obtained by 20 students:\n" +
                "4, 6, 5, 9, 3, 2, 7, 7, 6, 5, 4, 9, 10, 10, 3, 4, 7, 6, 9, 9\n" +
                "Solution: Given,\n" +
                "4, 6, 5, 9, 3, 2, 7, 7, 6, 5, 4, 9, 10, 10, 3, 4, 7, 6, 9, 9\n" +
                "Ascending order: 2, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 7, 7, 7, 9, 9, 9, 9, 10, 10\n" +
                "Number of observations = n = 20\n" +
                "Median = (10th + 11th observation)/2\n" +
                "= (6 + 6)/2\n" +
                "= 6\n" +
                "Most frequent observations = 9\n" +
                "Hence, the mode is 9.";
    }
}