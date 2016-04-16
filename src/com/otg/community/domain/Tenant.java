package com.otg.community.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 16-2-25.
 */
public class Tenant implements Serializable {
    private long id;
    private String name ;
    private String sex ;
    private String idCard;
    private String mz ;
    private String birth;
    private String sign;
    private String address;
    private String DN;
    private String validity;
    private String phone;
    private String description;
    private byte[] bytes;
    private byte[] cardPicBefore;
    private byte[] cardPicAfter;
    private int status;
    private int attention;
    private int ocr;
    private Date initDate;
    private Date lastDate;
    private String longitude;
    private String latitude;
    private int room;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    private CommunityRoom communityRoom;

    public Tenant() {
    }

    public Tenant(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDN() {
        return DN;
    }

    public void setDN(String DN) {
        this.DN = DN;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getCardPicBefore() {
        return cardPicBefore;
    }

    public void setCardPicBefore(byte[] cardPicBefore) {
        this.cardPicBefore = cardPicBefore;
    }

    public byte[] getCardPicAfter() {
        return cardPicAfter;
    }

    public void setCardPicAfter(byte[] cardPicAfter) {
        this.cardPicAfter = cardPicAfter;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public CommunityRoom getCommunityRoom() {
        return communityRoom;
    }

    public void setCommunityRoom(CommunityRoom communityRoom) {
        this.communityRoom = communityRoom;
    }

    public int getOcr() {
        return ocr;
    }

    public void setOcr(int ocr) {
        this.ocr = ocr;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
