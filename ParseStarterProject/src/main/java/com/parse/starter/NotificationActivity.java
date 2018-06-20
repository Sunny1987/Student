package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {


    ImageView myFeed;
    ImageView account;
    RecyclerView recycler_notification;
    RecyclerView.Adapter notif_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<NotifBookSet> notifBookSets = new ArrayList<>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //setTitle("My Notifications");

        recycler_notification = (RecyclerView)findViewById(R.id.recycler_notification);
        notif_adapter = new NotificationAdapter(notifBookSets);
        layoutManager = new LinearLayoutManager(this);
        recycler_notification.setLayoutManager(layoutManager);
        recycler_notification.setHasFixedSize(true);
        recycler_notification.setAdapter(notif_adapter);



        ParseQuery<ParseObject> notif_query = new ParseQuery<ParseObject>("Buy");



        //notif_query.whereEqualTo("Seller", ParseUser.getCurrentUser().getUsername());

        notif_query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    for (ParseObject object : objects){
                        String name = (String) object.get("Book_Name");
                        String cat = (String) object.get("Book_Category");
                        String pub = (String) object.get("Book_Publisher");
                        String price = (String)object.get("Book_Price");
                        String buyer = (String)object.get("Buyer");
                        String seller = (String)object.get("Seller");
                        String status = (String)object.get("Status");
                        if(seller.equals(ParseUser.getCurrentUser().getUsername())){
                            String message =  buyer + " wants to buy this book:";
                            NotifBookSet sellSet = new NotifBookSet(name,cat,pub,price,buyer,seller,message);
                            notifBookSets.add(sellSet);
                            notif_adapter.notifyDataSetChanged();
                        }

                        if((buyer.equals(ParseUser.getCurrentUser().getUsername())) && (!(status.equals("No")))){
                            String message = seller+" has responded";
                            NotifBookSet buySet = new NotifBookSet(name,cat,pub,price,buyer,seller,message);
                            notifBookSets.add(buySet);
                            notif_adapter.notifyDataSetChanged();
                        }



                    }
                }
            }
        });




        account = (ImageView)findViewById(R.id.account);
        myFeed = (ImageView)findViewById(R.id.news);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
            }
        });

        myFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserFeed.class);
                startActivity(intent);
            }
        });




    }
}
