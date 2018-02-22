package com.example.daniel.appone;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.daniel.appone.NAME";
    public static final String EXTRA_YEAR = "com.example.daniel.appone.YEAR";
    private RadioButton r1;
    private RadioButton r2;
    private RadioButton r3;
    private RadioButton r4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }




    public void send(View view) {

        Intent intent = new Intent(this, playGameActivity.class);

        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_NAME  ,message);




        r1 =(RadioButton)findViewById(R.id.year60);
        r2=(RadioButton)findViewById(R.id.year70);
        r3=(RadioButton)findViewById(R.id.year80);
        r4=(RadioButton)findViewById(R.id.year90);


        if (r1.isChecked()==true) {
            intent.putExtra(EXTRA_YEAR  ,"year60");
        }else if (r2.isChecked()==true) {
            intent.putExtra(EXTRA_YEAR  ,"year70");
        }else if (r3.isChecked()==true) {
            intent.putExtra(EXTRA_YEAR  ,"year80");
        }else if (r4.isChecked()==true) {
            intent.putExtra(EXTRA_YEAR  ,"year90");
        }



        startActivity(intent);

    }




}
