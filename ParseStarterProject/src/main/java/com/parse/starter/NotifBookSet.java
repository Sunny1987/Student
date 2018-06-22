package com.parse.starter;

public class NotifBookSet {
   private String buy_book_name,buy_book_cat,buy_book_pub,buy_book_price,buyer,seller,message,status;

    public NotifBookSet(String buy_book_name, String buy_book_cat, String buy_book_pub, String buy_book_price, String buyer,String seller,String message,String status) {
        this.buy_book_name = buy_book_name;
        this.buy_book_cat = buy_book_cat;
        this.buy_book_pub = buy_book_pub;
        this.buy_book_price = buy_book_price;
        this.buyer = buyer;
        this.seller = seller;
        this.message = message;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getSeller() {
        return seller;
    }

    public String getBuy_book_name() {
        return buy_book_name;
    }

    public String getBuy_book_cat() {
        return buy_book_cat;
    }

    public String getBuy_book_pub() {
        return buy_book_pub;
    }

    public String getBuy_book_price() {
        return buy_book_price;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBuy_book_name(String buy_book_name) {
        this.buy_book_name = buy_book_name;
    }

    public void setBuy_book_cat(String buy_book_cat) {
        this.buy_book_cat = buy_book_cat;
    }

    public void setBuy_book_pub(String buy_book_pub) {
        this.buy_book_pub = buy_book_pub;
    }

    public void setBuy_book_price(String buy_book_price) {
        this.buy_book_price = buy_book_price;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
