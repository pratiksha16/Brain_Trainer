package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView sumTextView;
    TextView timerTextView;
    Button play;
    RelativeLayout relativeLayout;

    TextView resultTextView;
    TextView pointsTextView;
    TextView highscoreView;


    int locationOfCorrectAnswer;
    int score=0;
    int numOfQuestion=0;
    int highScore=0;
    ArrayList<Integer> answers= new ArrayList<Integer>();
    SharedPreferences preferences;
    public void playAgain(View view){
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        score=0;
        numOfQuestion=0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        play.setVisibility(View.INVISIBLE);
        generateQuestion();

        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                play.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your score:"+Integer.toString(score)+ "/"+ Integer.toString(numOfQuestion));
                if(score > highScore){
                    highscoreView.setText("High Score:"+Integer.toString(score));
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putInt("HIGH_SCORE",score);
                    editor.commit();
                }
                else{
                    highscoreView.setText("High Score:"+Integer.toString(highScore));
                }

                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);






            }
        }.start();



    }

    public void generateQuestion(){

        Random rand= new Random();
        int a= rand.nextInt(21);
        int b= rand.nextInt(21);
        sumTextView.setText(Integer.toString(a)+"+"+ Integer.toString(b));
        locationOfCorrectAnswer= rand.nextInt(3);
        answers.clear();
        int incorrectAnswer;

        for(int i=0; i<4; i++){
            if(i==locationOfCorrectAnswer){
                answers.add(a+b);
            }
            else{
                incorrectAnswer=rand.nextInt(41);
                while(incorrectAnswer==a+b) {
                    incorrectAnswer=rand.nextInt(41);
                }
                answers.add(incorrectAnswer);

            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void chooseAnswer(View view){
        //Log.i("Tag", (String)view.getTag());
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            //Log.i("Correct", "correct");
            score++;
            resultTextView.setText("Correct!");

        }
        else{
            resultTextView.setText("Incorrect!");
        }
        numOfQuestion++;
        pointsTextView.setText(Integer.toString(score)+ "/"+ Integer.toString(numOfQuestion));
        generateQuestion();


    }
    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.play));

    }

    private void setHighScore(){

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences= getSharedPreferences("highscore", Context.MODE_PRIVATE);
        highScore=preferences.getInt("HIGH_SCORE",0);



        startButton= findViewById(R.id.startButton);
         sumTextView= findViewById(R.id.sumTextView);
        resultTextView= findViewById(R.id.resultTextView);
        pointsTextView= findViewById(R.id.pointsTextView);
        timerTextView= findViewById(R.id.timerTextView);
        highscoreView= findViewById(R.id.highScore);
        play= findViewById(R.id.play);

        button0= findViewById(R.id.button0);
         button1= findViewById(R.id.button1);
         button2= findViewById(R.id.button2);
        button3= findViewById(R.id.button3);
        relativeLayout= findViewById(R.id.gameRelativeLayout);














    }
}
