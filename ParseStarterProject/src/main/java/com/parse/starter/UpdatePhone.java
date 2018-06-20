package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class UpdatePhone extends AppCompatActivity {

    EditText phone_number;
    Button submit_phone_number;
    ImageView cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);
        setTitle("Update Phone Number");
        phone_number = (EditText)findViewById(R.id.phone_number);
        submit_phone_number = (Button)findViewById(R.id.submit_phone_number);
        cancel = (ImageView)findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MySettings.class);
                startActivity(intent);
            }
        });


        final ParseObject phone_Number = new ParseObject("Phone");
        submit_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone_number.getText().toString();
                phone_Number.put("username", ParseUser.getCurrentUser().getUsername());
                phone_Number.put("phone",phoneNumber);

                phone_Number.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if( e == null ){
                            Toast.makeText(UpdatePhone.this, "Phone number updated successfully!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(UpdatePhone.this, "Phone number not updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }
}
