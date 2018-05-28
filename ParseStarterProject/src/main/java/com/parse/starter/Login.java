/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;


public class Login extends AppCompatActivity {

RelativeLayout relativeLayout ;
ImageView imageView ;
TextView signUp;
EditText login_username;
EditText login_password;
Button login_button;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
    imageView = (ImageView)findViewById(R.id.imageView);
    login_username = (EditText)findViewById(R.id.usernameText);
    login_password = (EditText)findViewById(R.id.passwordText);
    login_button = (Button)findViewById(R.id.login_button);

    relativeLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
      }
    });

    imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
      }
    });




    login_password.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
          startLogIn();
        }
        return false;
      }
    });

    if(ParseUser.getCurrentUser() != null){
      goToFeed();
    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void logIn(View view){
      startLogIn();
  }

  public void startLogIn(){

    String login_usr = login_username.getText().toString();
    String login_pwd = login_password.getText().toString();

    ParseUser.logInInBackground(login_usr, login_pwd, new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(user != null){
          Log.i("login","successful");
          goToFeed();

        }else {
          Log.i("login","failed");
          Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  public void goToFeed(){
    Intent intent = new Intent(getApplicationContext(),UserFeed.class);
    startActivity(intent);
  }
}