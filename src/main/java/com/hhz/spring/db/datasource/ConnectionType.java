package com.hhz.spring.db.datasource;

public class ConnectionType {
    public static final String READ = "R";
    public static final String READ_WRITE = "RW";

    private  String type; // should be one of R/W/RW
    private  String key;

    public ConnectionType() {
    }

    public ConnectionType(String type, String key) {
        this.type = type;
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
