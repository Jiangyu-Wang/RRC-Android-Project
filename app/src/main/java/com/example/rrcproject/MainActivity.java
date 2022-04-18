package com.example.rrcproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Question> questionsList;
    private TextView questionIndex;
    private TextView questionText;
    private Button option1;
    private Button option2;
    private TextView questionExplanation;
    private int currentQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button previous = findViewById(R.id.previous);
        Button next = findViewById(R.id.next);
        questionIndex = findViewById(R.id.questionIndex);
        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        questionExplanation = findViewById(R.id.questionExplanation);
        currentQuestionIndex = 0;
        questionsList = new ArrayList<>();

        DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference("Questions");
        questionRef.get().addOnCompleteListener(task -> {
            DataSnapshot qSnapshot = task.getResult();
            for (DataSnapshot dataSnapshot: qSnapshot.getChildren()) {
                Question question = dataSnapshot.getValue(Question.class);
                questionsList.add(question);
            }
            renderQuestionCard(questionsList.get(currentQuestionIndex));
        });

        previous.setOnClickListener(view -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                renderQuestionCard(questionsList.get(currentQuestionIndex));
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "No previous questions", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        next.setOnClickListener(view -> {
            if (currentQuestionIndex < (questionsList.size()-1)) {
                currentQuestionIndex++;
                renderQuestionCard(questionsList.get(currentQuestionIndex));
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "No next questions", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        option1.setOnClickListener(view -> questionExplanation.setVisibility(View.VISIBLE));
        option2.setOnClickListener(view -> questionExplanation.setVisibility(View.VISIBLE));

    }

    public void renderQuestionCard(Question question) {
        questionIndex.setText(Integer.toString(question.index+1));
        questionText.setText(question.questionText);
        option1.setText(question.option1);
        option2.setText(question.option2);
        questionExplanation.setText(question.questionExplanation);
        questionExplanation.setVisibility(View.INVISIBLE);
    }
}