package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OrderHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        setTitle("History of Orders");
    }
}
