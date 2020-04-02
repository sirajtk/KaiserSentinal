package com.example.kaisersentinel.DataModal;

public class Values {

    int id_no;
    String unixtime ,timestamp ,hub_mac ,user_mac ,channel ,alert_type ,marked ,description;
    byte[] image;

    public Values() {
    }

    public Values(int id_no, String unixtime, String timestamp, String hub_mac, String user_mac, String channel, String alert_type, String marked, String description,byte[] image) {
        this.id_no = id_no;
        this.unixtime = unixtime;
        this.timestamp = timestamp;
        this.hub_mac = hub_mac;
        this.user_mac = user_mac;
        this.channel = channel;
        this.alert_type = alert_type;
        this.marked = marked;
        this.description = description;
        this.image=image;
    }

    public int getId_no() {
        return id_no;
    }

    public void setId_no(int id_no) {
        this.id_no = id_no;
    }

    public String getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(String unixtime) {
        this.unixtime = unixtime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHub_mac() {
        return hub_mac;
    }

    public void setHub_mac(String hub_mac) {
        this.hub_mac = hub_mac;
    }

    public String getUser_mac() {
        return user_mac;
    }

    public void setUser_mac(String user_mac) {
        this.user_mac = user_mac;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAlert_type() {
        return alert_type;
    }

    public void setAlert_type(String alert_type) {
        this.alert_type = alert_type;
    }

    public String getMarked() {
        return marked;
    }

    public void setMarked(String marked) {
        this.marked = marked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getimage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
