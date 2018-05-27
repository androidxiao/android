package com.duomizhibo.phonelive.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by cxf on 2017/7/13.
 */

public class UserInfo implements Parcelable {

    /**
     * id : 8573
     * user_nicename : 手机用户9060
     * avatar : http://livedemo.yunbaozhibo.com/default.jpg
     * coin : 697
     * avatar_thumb : http://livedemo.yunbaozhibo.com/default_thumb.jpg
     * sex : 2
     * signature : 这家伙很懒，什么都没留下
     * consumption : 1013
     * votestotal : 0
     * province :
     * city : 泰安市
     * birthday :
     * issuper : 0
     * level : 6
     * level_anchor : 1
     * vip : {"type":"1"}
     * liang : {"name":"0"}
     */

    private String id;
    private String user_nicename;
    private String avatar;
    private String coin;
    private String avatar_thumb;
    private String sex;
    private String signature;
    private String consumption;
    private String votestotal;
    private String province;
    private String city;
    private String birthday;
    private String issuper;
    private String level;
    private String level_anchor;
    private VipBean vip;
    private LiangBean liang;

    protected UserInfo(Parcel in) {
        id = in.readString();
        user_nicename = in.readString();
        avatar = in.readString();
        coin = in.readString();
        avatar_thumb = in.readString();
        sex = in.readString();
        signature = in.readString();
        consumption = in.readString();
        votestotal = in.readString();
        province = in.readString();
        city = in.readString();
        birthday = in.readString();
        issuper = in.readString();
        level = in.readString();
        level_anchor = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_nicename() {
        return user_nicename;
    }

    public void setUser_nicename(String user_nicename) {
        this.user_nicename = user_nicename;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getAvatar_thumb() {
        return avatar_thumb;
    }

    public void setAvatar_thumb(String avatar_thumb) {
        this.avatar_thumb = avatar_thumb;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getVotestotal() {
        return votestotal;
    }

    public void setVotestotal(String votestotal) {
        this.votestotal = votestotal;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIssuper() {
        return issuper;
    }

    public void setIssuper(String issuper) {
        this.issuper = issuper;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel_anchor() {
        return level_anchor;
    }

    public void setLevel_anchor(String level_anchor) {
        this.level_anchor = level_anchor;
    }

    public VipBean getVip() {
        return vip;
    }

    public void setVip(VipBean vip) {
        this.vip = vip;
    }

    public LiangBean getLiang() {
        return liang;
    }

    public void setLiang(LiangBean liang) {
        this.liang = liang;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_nicename);
        dest.writeString(avatar);
        dest.writeString(coin);
        dest.writeString(avatar_thumb);
        dest.writeString(sex);
        dest.writeString(signature);
        dest.writeString(consumption);
        dest.writeString(votestotal);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(birthday);
        dest.writeString(issuper);
        dest.writeString(level);
        dest.writeString(level_anchor);
    }

    public static class VipBean implements Serializable{
        /**
         * type : 1
         */

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class LiangBean implements Serializable{
        /**
         * name : 0
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
