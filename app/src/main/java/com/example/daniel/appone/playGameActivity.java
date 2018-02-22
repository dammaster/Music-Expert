package com.example.daniel.appone;

/**
 * Created by daniel on 2018-01-16.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;
import java.io.IOException;
import java.text.BreakIterator;


public class playGameActivity extends AppCompatActivity {


    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3
    };
    // Gloabal variable
    private int correct_answer;
    private int current_question;
    private int correctas;
    private String[] all_questions;
    private boolean[] answer_is_correct;
    private int[] answer;
    private int[] video;
    private TextView text_question;
    private RadioGroup group;
    private Button btn_next;
    private String name;
    private TextView nQuestion;
    private String year;
    private CharSequence yearItem;
    private MyCount mc;
    private TextView tv;
    int mf;
    int second;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);





        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        // TextView myTextView = findViewById(R.id.displayText);
        // myTextView.setText(name);

        year = intent.getStringExtra(MainActivity.EXTRA_YEAR);
        //TextView myTextView2 = findViewById(R.id.years);
        //myTextView2.setText(year);


        text_question = (TextView) findViewById(R.id.text_question);
        group = (RadioGroup) findViewById(R.id.answer_group);
        btn_next = (Button) findViewById(R.id.btn_next);
        nQuestion = (TextView) findViewById(R.id.nQuestion);

        //Metodo empieza de nuevo
        start_over();



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = group.getCheckedRadioButtonId();
                int answer = -1;
                for (int i = 0; i < ids_answers.length; i++) {
                    if (ids_answers[i] == id) {
                        answer = i;
                    }
                }

                /*
                if (answer == correct_answer) {
                    Toast.makeText(playGameActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(playGameActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
                }

                */

                answer_is_correct[current_question] = (answer == correct_answer);

                if (current_question < all_questions.length - 1) {
                    current_question++;
                    showQuestion();
                } else {
                    checkResults();

                }


            }
        });


       //Initial notice how much time you have and numbers of questions
        Toast.makeText(playGameActivity.this, getString(R.string.you_have)+ " " + second + " " +getString(R.string.seconds_for)+ " " + all_questions.length + " " + getString(R.string.questions), Toast.LENGTH_LONG).show();


    }


     class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {

            //termina y muestra resultado
            checkResults();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            tv.setText("" + millisUntilFinished / 1000 + "");

        }
    }



    public void MyCounter() {
        //countDown
        mf = all_questions.length * 20000;
        second = mf / 1000;
        tv = (TextView) findViewById(R.id.chronometer);
        mc = new MyCount(mf, 1000);
        mc.start();

    }




// take all_questions from string.xml
    public void start_over() {


        switch (year) {
            case "year60":
                all_questions = getResources().getStringArray(R.array.all_questions60);
                break;
            case "year70":
                all_questions = getResources().getStringArray(R.array.all_questions70);
                break;
            case "year80":
                all_questions = getResources().getStringArray(R.array.all_questions80);
                break;
            case "year90":
                all_questions = getResources().getStringArray(R.array.all_questions90);
                break;


        }


        //cambiarlo por el switch
        //  all_questions = getResources().getStringArray(R.array.all_questions60);
        answer_is_correct = new boolean[all_questions.length];
        answer = new int[all_questions.length];
        for (int i = 0; i < answer.length; i++) {

            answer[i] = -1;
        }
        current_question = 0;
        showQuestion();
        MyCounter();



    }


// show dialod with alternative and call star_over() app star again from playGameActiviti
    public void selectYear() {

        //close MyCounter()
       mc.cancel();


        final CharSequence[] items = {"60's", "70's", "80's", "90's"};

        AlertDialog.Builder builder = new AlertDialog.Builder(playGameActivity.this);
        builder.setTitle(R.string.decade);
        AlertDialog show = builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                // will toast your selection
                //showToast("Name: " + items[item]);
                // dialog.dismiss();
                yearItem = items[item];


                if (yearItem == "60's") {
                    year = "year60";
                }
                if (yearItem == "70's") {
                    year = "year70";
                }
                if (yearItem == "80's") {
                    year = "year80";
                }
                if (yearItem == "90's") {
                    year = "year90";
                }

// star_over() starts with the selected year
                start_over();


            }


        })

// necessary to show the AlertDialog
                .show();


    }


    private void checkResults() {
        correctas = 0;
        for (boolean b : answer_is_correct) {
            if (b) correctas++;

        }

        String message =
                String.format(correctas + " out of " + all_questions.length + " " + getString(R.string.correct) + "\n\n" + getString(R.string.play_again));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.result);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                selectYear();




            }
        });
        builder.setNegativeButton(R.string.not, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //selectYear();
                //dialog.cancel();
                confirmClose();

            }
        });
        // create a AlertDialog and show it
        builder.create().show();

    }


/*
confirmClose() swow dialog, call goToClose() if click yes, or show selectYear() to start again
*/
    private void confirmClose() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.leave));
        // builder.setMessage(message);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                goToClose();


            }
        });


        builder.setNegativeButton(R.string.not, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                selectYear();

            }
        });
        // create a AlertDialog and show it
        builder.create().show();

    }


    /*
goToClose() do intent to another activity and send param to CloseActivity.class
@param name
*/

    public void goToClose() {

        Intent intent = new Intent(this, CloseActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();
    }




 /*
showQuestion() read array all_questions from string.xml, and separate with (split ";"). Show N question
show video. The correct answer is identified with the symbol (*)


@param
*/

public void showQuestion() {
        
        String q = all_questions[current_question];
        String[] parts = q.split(";");

        group.clearCheck();

        text_question.setText(parts[0]);

        // numero de preguntas
        int nq = current_question +1;
        nQuestion.setText(nq+" out of "+all_questions.length);


        // show video
        String video = parts[4];
        //we get the VideoView control
        VideoView videoView = findViewById(R.id.videoView1);
        //We establish the Uri of the Video
        video = video+".mp4";
        Uri uri = Uri.parse("http://viralselfie.mobi/video_app/"+video);
        //Uri uri = Uri.parse("android.resource://"+getPackageName()+"/raw/"+video);
        MediaController mc = new MediaController(this);
       // videoView.setMediaController(mc);
        //Assign the Uri to the VideoView Control
        videoView.setVideoURI(uri);
        //Start Video
        videoView.start();




        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            String answer = parts[i+1];
            if(answer.charAt(0) == '*'){
                correct_answer = i;
                answer = answer.substring(1);
            }

            rb.setText(answer);
        }

        if(current_question == all_questions.length-1){

            btn_next.setText(R.string.finish);
        }else{
            btn_next.setText(R.string.next);
        }


    }


    @Override
    public void onBackPressed() {

        Toast.makeText(playGameActivity.this, getString(R.string.not_go_back), Toast.LENGTH_LONG).show();

    }



}
