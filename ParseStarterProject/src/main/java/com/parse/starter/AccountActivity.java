package com.parse.starter;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountActivity extends AppCompatActivity {

    ImageView myFeed;
    ImageView notif;
    ImageView sell;
    ImageView order;
    ImageView setting;
    ImageView logout;
    ImageView profileImage;
    TextView name;
    ImageView chat;
    //ImageView sell_book;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        }


    }


    public void putDataInServer(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();

        ParseFile file = new ParseFile("image.png",byteArray);

        ParseObject object = new ParseObject("Image");
        object.put("image",file);
        object.put("username",ParseUser.getCurrentUser().getUsername());


        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(AccountActivity.this, "Image shared", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AccountActivity.this, "Image could not be shared", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);

                Log.i("Photo","Received");
                profileImage = (ImageView)findViewById(R.id.profile_Image);
                profileImage.setImageBitmap(bitmap);

                putDataInServer(bitmap);





            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        //setTitle("Account Info");


        profileImage = (ImageView) findViewById(R.id.profile_Image);
        sell = (ImageView) findViewById(R.id.sell);
        order = (ImageView) findViewById(R.id.order);
        setting = (ImageView) findViewById(R.id.setting);
        logout = (ImageView) findViewById(R.id.logout);
        name  = (TextView)findViewById(R.id.name) ;
        chat = (ImageView)findViewById(R.id.chat);
        //sell_book = (ImageView)findViewById(R.id.sell_book);

        name.setText(ParseUser.getCurrentUser().getUsername());

        /*sell_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SellBook.class);
                startActivity(intent);
            }
        });*/

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChatRoom.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                if(ParseUser.getCurrentUser() != null){
                    Log.i("User","Logged In");
                }else {
                    Log.i("User","Logged off");
                }
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SellBook.class);
                startActivity(intent);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrderHistory.class);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MySettings.class);
                startActivity(intent);
            }
        });


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
       // Log.i("fl",Integer.toString(fl));

            //query.orderByDescending("createdAt");
            query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
            query.orderByDescending("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            for (ParseObject object : objects) {
                                ParseFile file = (ParseFile) object.get("image");

                                file.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        if (e == null && data != null) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            profileImage.setImageBitmap(bitmap);
                                        }/*else {
                                            Drawable res = getResources().getDrawable(R.drawable.user_image,null);
                                            profileImage.setImageDrawable(res);
                                        }*/
                                    }
                                });
                            }
                        }
                    }
                }
            });







        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                String[] userCh = {"Delete Pic ","Upload"};

                builder.setTitle("Actions");
                builder.setItems(userCh, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) {
                            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
                            query.orderByAscending("createdAt");

                            //profileImage.setImageResource(R.drawable.user_image);
                            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_image);
                            //putDataInServer(bitmap);
                            //fl = 0;


                        }

                        if(which == 1) {

                            //fl = 1;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                                }else {
                                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(intent,1);
                                }
                            }else {
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent,1);
                            }



                        }

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });











        myFeed = (ImageView)findViewById(R.id.news);
        notif = (ImageView)findViewById(R.id.notif);

        myFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserFeed.class);

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
    }
}
