package com.parse.starter;

import android.content.Context;
import android.content.Intent;
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
    Context context;
    String bookName,bookCat,bookPub,bookPrice,bookBuyer,status;

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
        context = holder.itemView.getContext();
        final NotifBookSet notifBookSet = buyRecord.get(position);

        bookName = notifBookSet.getBuy_book_name();
        bookCat = notifBookSet.getBuy_book_cat();
        bookPub = notifBookSet.getBuy_book_pub();
        bookPrice = notifBookSet.getBuy_book_price();
        bookBuyer = notifBookSet.getMessage();
        Log.i("book name in binder ",bookName);
        Log.i("buyer in binder ",bookBuyer);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Buy");
        query.whereEqualTo("Book_Name",notifBookSet.getBuy_book_name())
                .whereEqualTo("Book_Category",notifBookSet.getBuy_book_cat())
                .whereEqualTo("Book_Publisher",notifBookSet.getBuy_book_pub())
                .whereEqualTo("Buyer",notifBookSet.getBuyer())
                .whereEqualTo("Seller",notifBookSet.getSeller());

            holder.buyer.setText(bookBuyer + " "+bookName);

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null){
                        for(ParseObject parseObject : objects){

                            if(parseObject.get("Status").toString().equals("Request Accepted")){
                                holder.message.setText(parseObject.get("Status").toString());
                                status = parseObject.get("Status").toString();
                                //holder.accept.setVisibility(View.INVISIBLE);
                                //holder.decline.setVisibility(View.INVISIBLE);
                            }if(parseObject.get("Status").toString().equals("Request Declined")){
                                holder.message.setText(parseObject.get("Status").toString());
                                status = parseObject.get("Status").toString();
                                //holder.accept.setVisibility(View.INVISIBLE);
                                //holder.decline.setVisibility(View.INVISIBLE);
                            }if(parseObject.get("Status").toString().equals("No")){
                                holder.message.setText("Request pending");
                                status = parseObject.get("Status").toString();
                            }

                        }
                    }
                }
            });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ExpandNotification.class);
                intent.putExtra("bookName",notifBookSet.getBuy_book_name());
                intent.putExtra("bookCat",notifBookSet.getBuy_book_cat());
                intent.putExtra("bookPub",notifBookSet.getBuy_book_pub());
                intent.putExtra("bookPrice",notifBookSet.getBuy_book_price());
                intent.putExtra("bookBuyer",notifBookSet.getBuyer());
                intent.putExtra("status",notifBookSet.getStatus());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        Log.i("length",Integer.toString(buyRecord.size()));
        return buyRecord.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //TextView buy_name;
        //TextView buy_cat;
        //TextView buy_pub;
        //TextView buy_price;
        TextView buyer,message;
        //ImageView accept,decline;




        public ViewHolder(View itemView) {

            super(itemView);
            buyer = (TextView)itemView.findViewById(R.id.prod_buyer);
            message =(TextView)itemView.findViewById(R.id.message);







        }
    }
}
