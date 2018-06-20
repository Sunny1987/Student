package com.parse.starter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private ArrayList<NotifBookSet> buyRecord;

    public NotificationAdapter(ArrayList<NotifBookSet> buyRecord) {
        this.buyRecord = buyRecord;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_prod_layout,parent,false);
        NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ViewHolder holder, int position) {

        final NotifBookSet notifBookSet = buyRecord.get(position);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Buy");
        query.whereEqualTo("Book_Name",notifBookSet.getBuy_book_name())
                .whereEqualTo("Book_Category",notifBookSet.getBuy_book_cat())
                .whereEqualTo("Book_Publisher",notifBookSet.getBuy_book_pub())
                .whereEqualTo("Buyer",notifBookSet.getBuyer())
                .whereEqualTo("Seller",notifBookSet.getSeller());



            holder.buy_name.setText(notifBookSet.getBuy_book_name());
            holder.buy_cat.setText(notifBookSet.getBuy_book_cat());
            holder.buy_pub.setText(notifBookSet.getBuy_book_pub());
            holder.buy_price.setText("₹"+notifBookSet.getBuy_book_price());
            holder.buyer.setText(notifBookSet.getMessage());



            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null){
                        for(ParseObject parseObject : objects){

                            if(parseObject.get("Status").toString().equals("Request Accepted")){
                                holder.message.setText(parseObject.get("Status").toString());
                                holder.accept.setVisibility(View.INVISIBLE);
                                holder.decline.setVisibility(View.INVISIBLE);
                            }if(parseObject.get("Status").toString().equals("Request Declined")){
                                holder.message.setText(parseObject.get("Status").toString());
                                holder.accept.setVisibility(View.INVISIBLE);
                                holder.decline.setVisibility(View.INVISIBLE);
                            }

                        }
                    }
                }
            });

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.accept.setVisibility(View.INVISIBLE);
                    holder.decline.setVisibility(View.INVISIBLE);
                    holder.message.setText("Request Accepted");
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Buy");
                    query.whereEqualTo("Book_Name",notifBookSet.getBuy_book_name())
                            .whereEqualTo("Book_Category",notifBookSet.getBuy_book_cat())
                            .whereEqualTo("Book_Publisher",notifBookSet.getBuy_book_pub())
                            .whereEqualTo("Buyer",notifBookSet.getBuyer());

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

            holder.decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.accept.setVisibility(View.INVISIBLE);
                    holder.decline.setVisibility(View.INVISIBLE);
                    holder.message.setText("Request Declined");
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Buy");
                    query.whereEqualTo("Book_Name",notifBookSet.getBuy_book_name())
                            .whereEqualTo("Book_Category",notifBookSet.getBuy_book_cat())
                            .whereEqualTo("Book_Publisher",notifBookSet.getBuy_book_pub())
                            .whereEqualTo("Buyer",notifBookSet.getBuyer());

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

    @Override
    public int getItemCount() {
        Log.i("length",Integer.toString(buyRecord.size()));
        return buyRecord.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView buy_name;
        TextView buy_cat;
        TextView buy_pub;
        TextView buy_price;
        TextView buyer,message;
        ImageView accept,decline;




        public ViewHolder(View itemView) {

            super(itemView);
            buy_name = (TextView) itemView.findViewById(R.id.prod_name);
            buy_cat = (TextView)itemView.findViewById(R.id.prod_cat);
            buy_pub = (TextView)itemView.findViewById(R.id.prod_pub);
            buy_price = (TextView)itemView.findViewById(R.id.prod_price);
            buyer = (TextView)itemView.findViewById(R.id.prod_buyer);
            accept = (ImageView)itemView.findViewById(R.id.accept);
            decline = (ImageView)itemView.findViewById(R.id.decline);
            message =(TextView)itemView.findViewById(R.id.message);



        }
    }
}
