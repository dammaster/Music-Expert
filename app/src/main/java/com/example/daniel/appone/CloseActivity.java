package com.example.daniel.appone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CloseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);


        String name = getIntent().getStringExtra("name");
        TextView nameTextView = findViewById(R.id.name);
        nameTextView.setText("Come back soon " + name + "!");
    }




    public void close(View view) {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        Toast.makeText(CloseActivity.this, getString(R.string.bye), Toast.LENGTH_SHORT).show();
        finish();
    }


    public void share(View view){

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.recommend) );
        intent.putExtra(Intent.EXTRA_TEXT, "Download app: https://play.google.com/store/apps/details?id=com.appolom.viralselfie");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }



    public void suggestion(View view){

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:me@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion" );
        intent.putExtra(Intent.EXTRA_TEXT, "");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}
