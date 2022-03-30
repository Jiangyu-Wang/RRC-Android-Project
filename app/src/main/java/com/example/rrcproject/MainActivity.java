package com.example.rrcproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Question> questionsList;
    private TextView questionIndex;
    private TextView questionText;
    private Button option1;
    private Button option2;
    private TextView questionExplanation;
    private Button previous;
    private Button next;
    private int currentQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionIndex = findViewById(R.id.questionIndex);
        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        questionExplanation = findViewById(R.id.questionExplanation);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);

        Gson gson = new Gson();
        String json = loadJSONFromAsset(this, "questions.json");
        questionsList = gson.fromJson(json, new TypeToken<List<Question>>(){}.getType());

        currentQuestionIndex = 0;
        renderQuestionCard(questionsList.get(currentQuestionIndex));

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    renderQuestionCard(questionsList.get(currentQuestionIndex));
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No previous questions", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestionIndex < (questionsList.size()-1)) {
                    currentQuestionIndex++;
                    renderQuestionCard(questionsList.get(currentQuestionIndex));
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No next questions", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    public String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void renderQuestionCard(Question question) {
        questionIndex.setText(Integer.toString(question.index+1));
        questionText.setText(question.questionText);
        option1.setText(question.option1);
        option2.setText(question.option2);
        questionExplanation.setText(question.questionExplanation);
    }
}