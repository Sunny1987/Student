package com.parse.starter;

import android.graphics.Bitmap;

import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.BitSet;

public class BookSet {

    private String book_name,book_cat,book_pub,book_price;
    private ParseFile book_image;

    public BookSet(String book_name, String book_cat, String book_pub, String book_price, ParseFile book_image) {
        this.book_name = book_name;
        this.book_cat = book_cat;
        this.book_pub = book_pub;
        this.book_price = book_price;
        this.book_image = book_image;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getBook_cat() {
        return book_cat;
    }

    public String getBook_pub() {
        return book_pub;
    }

    public String getBook_price() {
        return book_price;
    }

    public ParseFile getBook_image() {
        return book_image;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setBook_cat(String book_cat) {
        this.book_cat = book_cat;
    }

    public void setBook_pub(String book_pub) {
        this.book_pub = book_pub;
    }

    public void setBook_price(String book_price) {
        this.book_price = book_price;
    }

    public void setBook_image(ParseFile book_image) {
        this.book_image = book_image;
    }
}
