package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    Button new_signup;
    ImageView new_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        new_signup = (Button)findViewById(R.id.new_signup);
        new_login = (ImageView)findViewById(R.id.new_login);


        new_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });

        new_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);

                startActivity(intent);
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            Intent intent = new Intent(getApplicationContext(),UserFeed.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            // show the signup or login screen
        }
    }
}
