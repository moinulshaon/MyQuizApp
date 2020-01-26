package com.example.myquizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String SCORE_KEY="score";
    private static String INDEX_KEY="index";

    private Button trueButton;
    private Button falseButton;
    private TextView questionTextView;
    private ProgressBar progressBar;

    private int currentQuestionInd;
    private int score;

    private QuestionModel[] questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        initializeState(savedInstanceState);

        loadQuestion(currentQuestionInd);

        trueButton.setOnClickListener(this::questionAnswered);
        falseButton.setOnClickListener(this::questionAnswered);
    }

    public void questionAnswered(View v) {
        if (v.getId() == trueButton.getId()) {
            score += questions[currentQuestionInd].isAnswer()?1:0;
        } else {
            score += questions[currentQuestionInd].isAnswer()?0:1;
        }
        progressBar.incrementProgressBy(100 / questions.length);
        if (currentQuestionInd + 1 >= questions.length) {
            finishQuiz();
        } else {
            loadQuestion(++currentQuestionInd);
        }
    }

    private void finishQuiz() {
        new AlertDialog.Builder(this)
                .setTitle("You have answered all the questions")
                .setMessage("Your score is: " + score)
                .setCancelable(false)
                .setPositiveButton("Finish the quiz", (dialog, which) -> finish())
                .show();
    }

    private void loadQuestion(int ind) {
        questionTextView.setText(questions[ind].getQuestion());
    }

    private void initializeComponents() {
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        questionTextView = findViewById(R.id.question_text_view);
        progressBar = findViewById(R.id.progressBar);
    }

    private void initializeState(Bundle savedInstanceState) {
        currentQuestionInd = 0;
        score = 0;
        questions = new QuestionModel[]{
                new QuestionModel(R.string.q1, Boolean.valueOf( getString(R.string.q1_ans))),
                new QuestionModel(R.string.q2, Boolean.valueOf( getString(R.string.q2_ans))),
                new QuestionModel(R.string.q3, Boolean.valueOf( getString(R.string.q3_ans)))
        };
        if (savedInstanceState != null) {
            currentQuestionInd = savedInstanceState.getInt(INDEX_KEY);
            score = savedInstanceState.getInt(SCORE_KEY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SCORE_KEY, score);
        outState.putInt(INDEX_KEY, currentQuestionInd);
    }
}
