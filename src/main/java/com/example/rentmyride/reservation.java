package com.example.rentmyride;

import java.math.BigDecimal;
import java.util.Date;

public class reservation {
    private int id;
    private int userid;

    private int cardid;

    public int carid;
    private BigDecimal rentprice;
    private Date datetaken;
    private int duration;
    public reservation(int id, int userid, int cardid, int carid, BigDecimal rentprice, Date date_taken, int duration) {
        this.id = id;
        this.userid = userid;
        this.cardid = cardid;
        this.carid = carid;
        this.rentprice = rentprice;
        this.datetaken = date_taken;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public long getCardid() {
        return cardid;
    }

    public void setCardId(int cardid) {
        this.cardid = cardid;
    }

    public int getCarid() {
        return carid;
    }

    public void setCarid(int carid) {
        this.carid = carid;
    }

    public BigDecimal getRent_price() {
        return rentprice;
    }

    public void setRent_price(BigDecimal rent_price) {
        this.rentprice = rent_price;
    }


    public Date getDate_taken() {
        return datetaken;
    }

    public void setDate_taken(Date date_taken) {
        this.datetaken = date_taken;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "reservation{" +
                "id=" + id +
                ", userid=" + userid +
                ", cardid=" + cardid +
                ", carid=" + carid +
                ", rent_price=" + rentprice +
                ", date_taken=" + datetaken +
                ", duration=" + duration +
                '}';
    }
}
