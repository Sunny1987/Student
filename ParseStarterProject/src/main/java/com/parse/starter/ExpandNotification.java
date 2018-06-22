package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ExpandNotification extends AppCompatActivity {

    ImageView decline;
    ImageView accept;
    TextView message;
    String buyer,name,cat,pub,price,msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_notification);
        TextView bookName = (TextView)findViewById(R.id.name);
        TextView bookCat = (TextView)findViewById(R.id.cat);
        TextView bookPub = (TextView)findViewById(R.id.pub);
        TextView bookPrice = (TextView)findViewById(R.id.price);
        TextView bookBuyer = (TextView)findViewById(R.id.buyer);
        decline = (ImageView)findViewById(R.id.decline);
        accept =(ImageView) findViewById(R.id.accept);
        message = (TextView) findViewById(R.id.message);


        Intent intent = getIntent();

        buyer = intent.getStringExtra("bookBuyer");
        name = intent.getStringExtra("bookName");
        cat = intent.getStringExtra("bookCat");
        pub = intent.getStringExtra("bookPub");
        price = intent.getStringExtra("bookPrice");
        msg = intent.getStringExtra("status");



        Log.i("status",msg);
        //Log.i("buyer",buyer);

        bookBuyer.setText(buyer);
        bookName.setText(name);
        bookCat.setText(cat);
        bookPub.setText(pub);
        bookPrice.setText(price);


        if(msg.equals("Request Accepted")){
            accept.setVisibility(View.INVISIBLE);
            decline.setVisibility(View.INVISIBLE);
            message.setText("Request Accepted");
        }if(msg.equals("Request Declined")){
            accept.setVisibility(View.INVISIBLE);
            decline.setVisibility(View.INVISIBLE);
            message.setText("Request Accepted");
        }if(msg.equals("No")){
            message.setText("Request Pending`");
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept.setVisibility(View.INVISIBLE);
                decline.setVisibility(View.INVISIBLE);
                message.setText("Request Accepted");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Buy");
                query.whereEqualTo("Book_Name",name)
                        .whereEqualTo("Book_Category",cat)
                        .whereEqualTo("Book_Publisher",pub)
                        .whereEqualTo("Buyer",buyer);

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null){
                            for(ParseObject parseObject : objects){
                                parseObject.put("Status","Request Accepted");
                                parseObject.saveInBackground();
                            }
                        }
                    }
                });




            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept.setVisibility(View.INVISIBLE);
                decline.setVisibility(View.INVISIBLE);
                message.setText("Request Declined");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Buy");
                query.whereEqualTo("Book_Name",name)
                        .whereEqualTo("Book_Category",cat)
                        .whereEqualTo("Book_Publisher",pub)
                        .whereEqualTo("Buyer",buyer);

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null){
                            for(ParseObject parseObject : objects){
                                parseObject.put("Status","Request Declined");
                                parseObject.saveInBackground();
                            }
                        }
                    }
                });
            }
        });




            }


        }






