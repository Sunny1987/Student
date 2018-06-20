package com.parse.starter;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class UserFeedAdapter extends RecyclerView.Adapter<UserFeedAdapter.ViewHolder> {

    private ArrayList<BookSet> bookList;

    public UserFeedAdapter(ArrayList<BookSet> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_display,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final BookSet bookSet = bookList.get(position);
        holder.product_name.setText(bookSet.getBook_name());
        holder.product_category.setText(bookSet.getBook_cat());
        holder.product_publisher.setText(bookSet.getBook_pub());
        holder.product_price.setText("₹"+bookSet.getBook_price());

        ParseFile parseFile = bookSet.getBook_image();

        parseFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if(e == null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    holder.product_image.setImageBitmap(bitmap);
                }
            }
        });

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("buy",Integer.toString(position));
                //Log.i("book name",bookSet.getBook_name());

                
                String pos = Integer.toString(position);
                String buy_bookName = bookSet.getBook_name();
                String buy_bookCat = bookSet.getBook_cat();
                final String buy_bookPub = bookSet.getBook_pub();
                String buy_bookPrice = bookSet.getBook_price();

                final ParseQuery<ParseObject> buy_query = ParseQuery.getQuery("Sell");
                buy_query.whereEqualTo("name",buy_bookName)
                        .whereEqualTo("category",buy_bookCat)
                        .whereEqualTo("publisher",buy_bookPub)
                        .whereEqualTo("price",buy_bookPrice);

                buy_query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null){
                            for(ParseObject parseObject:objects){
                                String user = parseObject.get("username").toString();

                                if(!(user.equals(ParseUser.getCurrentUser().getUsername()))){
                                    ParseObject buy_prod = new ParseObject("Buy");
                                    buy_prod.put("Book_Name",parseObject.get("name").toString());
                                    buy_prod.put("Book_Category",parseObject.get("category").toString());
                                    buy_prod.put("Book_Publisher",parseObject.get("publisher").toString());
                                    buy_prod.put("Book_Price",parseObject.get("price").toString());
                                    buy_prod.put("Seller",user);
                                    buy_prod.put("Buyer", ParseUser.getCurrentUser().getUsername());
                                    buy_prod.put("Status","No");
                                    buy_prod.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if(e == null){
                                                Log.i("Save ","Successful");


                                            }else {
                                                Log.i("Save","Unsuccessful");
                                            }
                                        }
                                    });


                                }



                            }
                        }
                    }
                });



            }
        });


    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView product_name;
        TextView product_publisher;
        TextView product_category;
        TextView product_price;
        ImageView product_image;
        ImageView buy;


        public ViewHolder(View itemView) {
            super(itemView);

          product_name = (TextView)itemView.findViewById(R.id.product_name);
          product_publisher = (TextView)itemView.findViewById(R.id.product_publisher);
          product_category = (TextView)itemView.findViewById(R.id.product_category);
          product_price = (TextView)itemView.findViewById(R.id.product_price);
          product_image = (ImageView)itemView.findViewById(R.id.product_image);
          buy = (ImageView)itemView.findViewById(R.id.buy_book);

       }
    }
}
