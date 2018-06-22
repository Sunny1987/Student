package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class UserFeed extends AppCompatActivity {

    ImageView account;
    ImageView notif;
    ImageView buy;
    ImageView chat;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<BookSet> bookList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);


        account = (ImageView)findViewById(R.id.account);
        notif = (ImageView)findViewById(R.id.notif);
        buy = (ImageView)findViewById(R.id.buy_book);
        chat = (ImageView)findViewById(R.id.chat);


        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChatRoom.class);
                startActivity(intent);
            }
        });

       /*buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Button","Clicked");
            }
        });*/

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
            }
        });


        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new UserFeedAdapter(bookList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);



        ParseQuery<ParseObject> dashboardQuery = new ParseQuery<ParseObject>("Sell");

        dashboardQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    for(ParseObject parseObject : objects){

                        String name = (String) parseObject.get("name");
                        String cat = (String) parseObject.get("category");
                        String pub = (String)parseObject.get("publisher");
                        String price = (String) parseObject.get("price");
                        ParseFile pf = (ParseFile) parseObject.get("image_book") ;
                        String seller = (String)parseObject.get("username");

                        BookSet bookSet = new BookSet(name,cat,pub,price,pf,seller,getApplicationContext());
                        bookList.add(bookSet);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
}










