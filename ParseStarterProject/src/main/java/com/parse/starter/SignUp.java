package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity {

    TextView login;
    RelativeLayout sup_relativeLayout;
    ImageView sup_image;
    EditText sup_username;
    EditText sup_password;
    EditText sup_mail;
    Button sup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        sup_relativeLayout = (RelativeLayout) findViewById(R.id.sup_relativeLayout);
        sup_image = (ImageView) findViewById(R.id.sup_imageView);
        sup_username = (EditText) findViewById(R.id.sup_usernameText);
        sup_password = (EditText) findViewById(R.id.sup_passwordText);
        sup_mail = (EditText) findViewById(R.id.sup_email);



        sup_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        sup_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        sup_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    startSignUp();
                }


                return false;
            }
        });
    }

    public void signUp(View view) {

        startSignUp();
    }

    public void startSignUp(){
        String sup_usr = sup_username.getText().toString();
        String sup_pwd = sup_password.getText().toString();
        String sup_email = sup_mail.getText().toString();

        ParseUser user = new ParseUser();
        user.put("username",sup_usr);
        user.put("email",sup_email);
        user.put("password",sup_pwd);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.i("signup","successful");
                   goToFeed();
                }else {
                    Log.i("signup","failed");
                }
            }
        });

    }

    public void goToFeed(){
        Intent intent = new Intent(getApplicationContext(),UserFeed.class);
        startActivity(intent);
    }
}
