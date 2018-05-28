package com.parse.starter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class OrderSummary extends AppCompatActivity {

    ImageView book_pic;
    TextView book_name;
    TextView book_pub;
    TextView book_cat;
    TextView book_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        book_pic = (ImageView)findViewById(R.id.myBookImage);
        book_name = (TextView) findViewById(R.id.myBookName);
        book_pub = (TextView) findViewById(R.id.myBookPub);
        book_cat = (TextView) findViewById(R.id.myBookCat);
        book_price = (TextView) findViewById(R.id.myBookPrice);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Sell");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size()>0){
                        for(ParseObject parseObject : objects){
                            book_name.setText(parseObject.getString("name"));
                            book_pub.setText(parseObject.getString("publisher"));
                            book_cat.setText(parseObject.getString("category"));
                            book_price.setText(parseObject.getString("price"));

                            ParseFile file = (ParseFile) parseObject.get("image_book");
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(e== null && data != null){
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        book_pic.setImageBitmap(bitmap);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });


    }
}
