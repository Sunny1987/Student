package com.parse.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class SellBook extends AppCompatActivity {

    EditText book_name;
    EditText book_publisher;
    EditText book_category;
    EditText book_price;
    ImageView book;
    Button sell_button;
    Bitmap selectedImage;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){

            selectedImage = (Bitmap) data.getExtras().get("data");


            book.setImageBitmap(selectedImage);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 0 ){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent,1);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_book);
        //setTitle("Sell Book");



        book_name = (EditText)findViewById(R.id.book_name);
        book_publisher = (EditText)findViewById(R.id.book_publisher);
        book_category = (EditText)findViewById(R.id.book_category);
        book_price = (EditText)findViewById(R.id.book_price);


       book = (ImageView)findViewById(R.id.book_image);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.CAMERA},1);

                    }else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent,1);
                    }
                }
            }
        });





        final ParseObject sellBook = new ParseObject("Sell");
        sell_button = (Button)findViewById(R.id.sell_button);
        sell_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = book_name.getText().toString();
                String bookPub = book_publisher.getText().toString();
                String bookCat = book_category.getText().toString();
                String bookPrice = book_price.getText().toString();
                sellBook.put("username", ParseUser.getCurrentUser().getUsername());
                sellBook.put("name",bookName);
                sellBook.put("publisher",bookPub);
                sellBook.put("category",bookCat);
                sellBook.put("price",bookPrice);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray = stream.toByteArray();
                ParseFile file = new ParseFile("book_image.png",byteArray);

                sellBook.put("image_book",file);



                sellBook.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Log.i("saveInBackground","successful");
                            Toast.makeText(SellBook.this, "Post successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),OrderSummary.class);
                            startActivity(intent);

                        }else{
                            Log.i("saveInBackground","failed. Error" +e.toString());
                        }
                    }
                });




            }
        });

    }
}
