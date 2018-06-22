package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    String activeUser;
    ArrayList<String> messages = new ArrayList<>();
    Button go;
    EditText enterMsg;
    ArrayAdapter arrayAdapter;
    ListView listView;
    TextView member_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        activeUser = intent.getStringExtra("activeUser");
        Log.i("activeUser",activeUser);


        go = (Button)findViewById(R.id.msg_submit);
        enterMsg = (EditText)findViewById(R.id.enterMsg);
        listView = (ListView)findViewById(R.id.msg_listview);
        member_name = (TextView)findViewById(R.id.membar_name);
        member_name.setText(activeUser+"'s chat");

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseObject message = new ParseObject("Message");
                final String  messageContent = enterMsg.getText().toString();
                message.put("sender", ParseUser.getCurrentUser().getUsername());
                message.put("receiver",activeUser);
                message.put("message",messageContent);

                enterMsg.setText("");

                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Log.i("message","sent");
                            messages.add(messageContent);
                            Log.i("size of message",String.valueOf(messages.size()));
                            arrayAdapter.notifyDataSetChanged();

                        }else {
                            Log.i("message","not sent");
                        }
                    }
                });

            }
        });



        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,messages);
        listView.setAdapter(arrayAdapter);


        ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("Message");
        query1.whereEqualTo("sender",ParseUser.getCurrentUser().getUsername())
                .whereEqualTo("receiver",activeUser);

        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Message");
        query2.whereEqualTo("sender",activeUser)
                .whereEqualTo("receiver",ParseUser.getCurrentUser().getUsername());

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        messages.clear();
                        for (ParseObject message : objects){
                            String messageContent = message.getString("message");


                            if(!message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())){
                                messageContent = "> " + messageContent;
                            }else {

                                messageContent = messageContent +" <";
                            }
                            messages.add(messageContent);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });






    }
}
