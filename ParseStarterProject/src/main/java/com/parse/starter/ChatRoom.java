package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom extends AppCompatActivity {

    ListView chatRoom_listView;
    ArrayAdapter adapter;
    ArrayList<String> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        chatRoom_listView = (ListView)findViewById(R.id.member_list);

        chatRoom_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                intent.putExtra("activeUser",memberList.get(position));
                startActivity(intent);
            }
        });

        ParseQuery<ParseObject> member = new ParseQuery<ParseObject>("Message");
        member.whereEqualTo("receiver", ParseUser.getCurrentUser().getUsername());

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,memberList);
        chatRoom_listView.setAdapter(adapter);

        member.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size()>0){
                        for (ParseObject parseObject : objects){
                            String newValue = parseObject.getString("sender");
                            if(!memberList.contains(newValue)){
                                memberList.add(newValue);
                            }
                        }

                        adapter.notifyDataSetChanged();

                    }
                }
            }
        });

    }
}
